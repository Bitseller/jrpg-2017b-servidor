package servidor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import dominio.Item;
import mensajeria.PaquetePersonaje;
import mensajeria.PaqueteUsuario;
import persistencia.controladores.ControladorMochila;
import persistencia.controladores.ControladorPersonaje;
import persistencia.controladores.ControladorUsuario;
import persistencia.entidades.EInventario;
import persistencia.entidades.EItem;
import persistencia.entidades.EMochila;
import persistencia.entidades.EPersonaje;
import persistencia.entidades.EUsuario;
import persistencia.hibernate.HibernateUtil;
import properties.Idioma;

/**
 * The Class Conector.
 */
public class Conector {
    /**
     * Registrar usuario.
     *
     * @param user the user
     * @return true, if successful
     */
    public boolean registrarUsuario(final PaqueteUsuario user) {
        boolean resultadoOperacion;
        HibernateUtil.abrirSessionEnHilo();
        ControladorUsuario ctrlUsr = new ControladorUsuario();
        try 
        {
        	if(ctrlUsr.existe(user.getUsername())) 
        	{
        		Servidor.appendLog(MensajesLog.usuarioDuplicado(user.getUsername()));
        		resultadoOperacion = false;
        	}
        	else
        	{
        		EUsuario e = new EUsuario();
        		e.setUserName(user.getUsername());
        		e.setPassword(user.getPassword());
        		
        		ctrlUsr.guardar(e);
        		Servidor.appendLog(MensajesLog.usuarioRegistradoExitosamente(user.getUsername()));
        		resultadoOperacion = true;
        	}
		} 
        catch (Exception ex) 
        {
    		Servidor.appendLog(MensajesLog.usuarioErrorGeneralAlRegistrar(user.getUsername()));
        	System.err.println(ex.getMessage());
			ex.printStackTrace();
	     	resultadoOperacion = false;
		}
        finally
        {    
        	HibernateUtil.cerrarSessionEnHilo();
        }
     	return resultadoOperacion;
    }

    /**
     * Registrar personaje.
     *
     * @param paquetePersonaje the paquete personaje
     * @param paqueteUsuario the paquete usuario
     * @return true, if successful
     */
    public boolean registrarPersonaje(final PaquetePersonaje paquetePersonaje, final PaqueteUsuario paqueteUsuario) {    	
    	boolean resultadoOperacion;
        HibernateUtil.abrirSessionEnHilo();
        ControladorUsuario ctrlUsuario = new ControladorUsuario();
        try 
        {
        	EUsuario u = ctrlUsuario.buscarPorId(paqueteUsuario.getUsername());
        	
        	EPersonaje ePersonaje = new EPersonaje();
        	ePersonaje.setCasta(paquetePersonaje.getCasta());
        	ePersonaje.setRaza(paquetePersonaje.getRaza());
        	ePersonaje.setFuerza(paquetePersonaje.getFuerza());
        	ePersonaje.setDestreza(paquetePersonaje.getDestreza());
    		ePersonaje.setInteligencia(paquetePersonaje.getInteligencia());
    		ePersonaje.setSaludTope(paquetePersonaje.getSaludTope());
    		ePersonaje.setEnergiaTope(paquetePersonaje.getEnergiaTope());
    		ePersonaje.setNombre(paquetePersonaje.getNombre());
    		ePersonaje.setPuntosSkill(paquetePersonaje.getPuntosSkill());

    		ePersonaje.setInventario(new EInventario());
    		
        	u.setPersonaje(ePersonaje);

        	ctrlUsuario.guardar(u);
        	
    		Servidor.appendLog(MensajesLog.personajeRegistradoExitosamente(paquetePersonaje.getNombre()));
        	resultadoOperacion = true;
		} 
        catch (Exception ex) 
        {
    		Servidor.appendLog(MensajesLog.personajeErrorGeneralAlRegistrar()+paquetePersonaje.getNombre());
        	System.err.println(ex.getMessage());
			ex.printStackTrace();
	     	resultadoOperacion = false;
		}
        finally
        {    
        	HibernateUtil.cerrarSessionEnHilo();
        }
     	return resultadoOperacion;
    	
    }

    /**
     * Loguear usuario.
     *
     * @param user the user
     * @return true, if successful
     */
    public boolean loguearUsuario(final PaqueteUsuario user) 
    {
    	HibernateUtil.abrirSessionEnHilo();
    	ControladorUsuario ctrl = new ControladorUsuario();
    	boolean valido = ctrl.validarUsuario(user.getUsername(),user.getPassword());
    	if(valido)
    		Servidor.appendLog(MensajesLog.ingresoExitoso(user.getUsername()));
    	else
    		Servidor.appendLog(MensajesLog.ingresoFallido(user.getUsername()));
    	HibernateUtil.cerrarSessionEnHilo();
    	return valido;
    }

    /**
     * Actualizar personaje.
     *
     * @param paquetePersonaje the paquete personaje
     */
    public void actualizarPersonaje(final PaquetePersonaje paquetePersonaje) {
    	boolean resultadoOperacion;
        HibernateUtil.abrirSessionEnHilo();
        ControladorPersonaje ctrlPersonaje = new ControladorPersonaje();
        try 
        {
        	EPersonaje ePersonaje = ctrlPersonaje.buscarPorId(paquetePersonaje.getId());
        	
        	ePersonaje.setCasta(paquetePersonaje.getCasta());
        	ePersonaje.setRaza(paquetePersonaje.getRaza());
        	ePersonaje.setFuerza(paquetePersonaje.getFuerza());
        	ePersonaje.setDestreza(paquetePersonaje.getDestreza());
    		ePersonaje.setInteligencia(paquetePersonaje.getInteligencia());
    		ePersonaje.setSaludTope(paquetePersonaje.getSaludTope());
    		ePersonaje.setEnergiaTope(paquetePersonaje.getEnergiaTope());
    		ePersonaje.setNombre(paquetePersonaje.getNombre());
    		ePersonaje.setPuntosSkill(paquetePersonaje.getPuntosSkill());
    		ePersonaje.setExperiencia(paquetePersonaje.getExperiencia());
    		ePersonaje.setNivel(paquetePersonaje.getNivel());
    	
        	ctrlPersonaje.guardar(ePersonaje);
        	
    		Servidor.appendLog(MensajesLog.personajeActualizadoExitosamente(paquetePersonaje.getNombre()));
        	resultadoOperacion = true;
		} 
        catch (Exception ex) 
        {
    		Servidor.appendLog(MensajesLog.personajeErrorGeneralAlActualizar(paquetePersonaje.getNombre()));
        	System.err.println(ex.getMessage());
			ex.printStackTrace();
	     	resultadoOperacion = false;
		}
        finally
        {    
        	HibernateUtil.cerrarSessionEnHilo();
        }
    }

    /**
     * Gets the personaje.
     *
     * @param user the user
     * @return the personaje
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public PaquetePersonaje getPersonaje(final PaqueteUsuario user) throws IOException {
    	HibernateUtil.abrirSessionEnHilo();
        ControladorUsuario ctrlUsuario ;
        EUsuario u ;
        try 
        {
            ctrlUsuario = new ControladorUsuario();
            u = ctrlUsuario.buscarPorId(user.getUsername());
            EPersonaje p = u.getPersonaje();
            
            //REFACTORIZAR A UN METODO
            PaquetePersonaje personaje = new PaquetePersonaje();
            personaje.setId(p.getId());
            personaje.setRaza(p.getRaza());
            personaje.setCasta(p.getCasta());
            personaje.setFuerza(p.getFuerza());
            personaje.setInteligencia(p.getInteligencia());
            personaje.setDestreza(p.getDestreza());
            personaje.setEnergiaTope(p.getEnergiaTope());
            personaje.setSaludTope(p.getSaludTope());
            personaje.setNombre(p.getNombre());
            personaje.setExperiencia(p.getExperiencia());
            personaje.setNivel(p.getNivel());
            personaje.setPuntosSkill(p.getPuntosSkill());
            for(EItem item : p.getMochila())
            	personaje.anadirItem(new Item(item.getId(),item.getNombre(), item.getWereable(), item.getBonusSalud(),item.getBonusEnergia(),item.getBonusFuerza(), item.getBonusDestreza(), item.getBonusInteligencia(), item.getFoto(),item.getFotoEquipado()));
            
            return personaje;

        } 
        catch (Exception e) 
        {
    		Servidor.appendLog(MensajesLog.errorAlIntentarRecuperarElPJ(user.getUsername()));
		}
    	finally
    	{    
    		HibernateUtil.cerrarSessionEnHilo();
    	}

        return new PaquetePersonaje();
    }

    /**
     * Gets the usuario.
     *
     * @param usuario the usuario
     * @return the usuario
     */
    public PaqueteUsuario getUsuario(final String usuario) {
    	HibernateUtil.abrirSessionEnHilo();
    	ControladorUsuario ctrl = new ControladorUsuario();
    	PaqueteUsuario paqueteUsuario = new PaqueteUsuario();
    	try 
    	{
    		EUsuario eUsr = ctrl.buscarPorId(usuario);
    		paqueteUsuario.setUsername(usuario);
    		paqueteUsuario.setPassword(eUsr.getPassword());//no se deeberia pasar la contrasenia. cambiar aparte a guardar hash
    		paqueteUsuario.setIdPj(eUsr.getPersonaje().getId());
    	} 
    	catch (Exception ex) 
    	{
    		Servidor.appendLog(MensajesLog.errorAlIntentarRecuperarElUsuario(usuario));
    		Servidor.appendLog(ex.getMessage());
    	}
    	finally
    	{    
    		HibernateUtil.cerrarSessionEnHilo();
    	}
    	return paqueteUsuario;
    }

    /**
     * Actualizar inventario.
     *
     * @param paquetePersonaje the paquete personaje
     */
    public void actualizarInventario(final PaquetePersonaje paquetePersonaje) {
    	int i = 0;
    	HibernateUtil.abrirSessionEnHilo();
    	ControladorMochila ctrl = new ControladorMochila();
    	try 
    	{
    		EMochila mochila = new EMochila();
    		mochila.setId(paquetePersonaje.getId());
    		while (i < paquetePersonaje.getCantItems()) {
    			mochila.setItem(i+1,paquetePersonaje.getItemID(i));
    			i++;
    		}
    		if (paquetePersonaje.getCantItems() < 9) {
    			int itemGanado = new Random().nextInt(29);
    			itemGanado += 1;
    			mochila.setItem(paquetePersonaje.getCantItems() + 1, itemGanado);
    		} 
    		ctrl.actualizar(mochila);
    	} 
    	catch (Exception ex) 
    	{
    		Servidor.appendLog(MensajesLog.inventarioErrorGeneralAlActualizar(paquetePersonaje.getNombre()));
    		System.err.println(ex.getMessage());
    		ex.printStackTrace();
    	}
    	finally
    	{    
    		HibernateUtil.cerrarSessionEnHilo();
    	}
    }

    /**
     * Actualizar inventario.
     *
     * @param idPersonaje the id personaje
     */
    public void actualizarInventario(final int idPersonaje) {
    	actualizarInventario(Servidor.getPersonajesConectados().get(idPersonaje));
    }

    /**
     * Actualizar personaje subio nivel.
     *
     * @param paquetePersonaje the paquete personaje
     */
    public void actualizarPersonajeSubioNivel(final PaquetePersonaje paquetePersonaje) {
        HibernateUtil.abrirSessionEnHilo();
        ControladorPersonaje ctrl = new ControladorPersonaje();
        try 
        {
        	EPersonaje ePersonaje = new EPersonaje();
        	ePersonaje.setCasta(paquetePersonaje.getCasta());
        	ePersonaje.setRaza(paquetePersonaje.getRaza());
        	ePersonaje.setFuerza(paquetePersonaje.getFuerza());
        	ePersonaje.setDestreza(paquetePersonaje.getDestreza());
    		ePersonaje.setInteligencia(paquetePersonaje.getInteligencia());
    		ePersonaje.setSaludTope(paquetePersonaje.getSaludTope());
    		ePersonaje.setEnergiaTope(paquetePersonaje.getEnergiaTope());
    		ePersonaje.setNombre(paquetePersonaje.getNombre());
    		ePersonaje.setPuntosSkill(paquetePersonaje.getPuntosSkill());
    		ePersonaje.setExperiencia(paquetePersonaje.getExperiencia());
    		ePersonaje.setNivel(paquetePersonaje.getNivel());
        	
        	ctrl.actualizar(ePersonaje);
        	Servidor.appendLog(MensajesLog.personajeActualizadoExitosamente(paquetePersonaje.getNombre()));
		} 
        catch (Exception ex) 
        {
    		Servidor.appendLog(MensajesLog.personajeErrorGeneralAlActualizar(paquetePersonaje.getNombre()));
        	System.err.println(ex.getMessage());
			ex.printStackTrace();
		}
        finally
        {    
        	HibernateUtil.cerrarSessionEnHilo();
        }
    }
}
