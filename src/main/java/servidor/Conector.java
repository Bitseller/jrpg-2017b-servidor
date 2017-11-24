package servidor;

import java.io.IOException;
import mensajeria.PaquetePersonaje;
import mensajeria.PaqueteUsuario;
import persistencia.controladores.ItemCtrl;
import persistencia.controladores.PersonajeCtrl;
import persistencia.controladores.UsuarioCrl;
import persistencia.entidades.Item;
import persistencia.entidades.Personaje;
import persistencia.entidades.Usuario;
import persistencia.hibernate.HibernateUtil;

/**
 * The Class Conector.
 * Objetivo: realiza el enlace entre las operaciones con la base de datos y la aplicacion.
 */
public class Conector {
	/**
	 * Registra un usuario.
	 * @param user. Contenedor de informacion de usuario.
	 * @return true si el usuario es registrado correctamente, false en caso de error (ejemplo: el usr ya existe).
	 */
    public boolean registrarUsuario(final PaqueteUsuario user) {
        boolean resultadoOperacion;
        HibernateUtil.abrirSessionEnHilo();
        UsuarioCrl ctrlUsr = new UsuarioCrl();
        try {
            if (ctrlUsr.existe(user.getUsername())) {
                Servidor.appendLog(MensajesLog.usuarioDuplicado(user.getUsername()));
                resultadoOperacion = false;
            } else {
                ctrlUsr.guardar(new Usuario(user));
                Servidor.appendLog(MensajesLog.usuarioRegistradoExitosamente(user.getUsername()));
                resultadoOperacion = true;
            }
        } catch (Exception ex) {
            Servidor.appendLog(MensajesLog.usuarioErrorGeneralAlRegistrar(user.getUsername()));
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            resultadoOperacion = false;
        } finally {
            HibernateUtil.cerrarSessionEnHilo();
        }
        return resultadoOperacion;
    }

    /**
     * Registrar personaje.
     *
     * @param paquetePersonaje
     *            the paquete personaje
     * @param paqueteUsuario
     *            the paquete usuario
     * @return true, if successful
     */
    public boolean registrarPersonaje(PaquetePersonaje paquetePersonaje, final PaqueteUsuario paqueteUsuario) {
        boolean resultadoOperacion;
        HibernateUtil.abrirSessionEnHilo();
        UsuarioCrl ctrlUsuario = new UsuarioCrl();
        try {
        	Usuario usr = ctrlUsuario.buscarPorId(paqueteUsuario.getUsername());
            Personaje persj = new Personaje(paquetePersonaje);
            //persj.agregarItem(new ItemCtrl().itemRandom());//REGALAMOS UN ITEM POR SER LA PRIMERA VEZ
            usr.setPersonaje(persj);
            ctrlUsuario.guardar(usr);
            Servidor.appendLog(MensajesLog.personajeRegistradoExitosamente(paquetePersonaje.getNombre()));
            resultadoOperacion = true;
        } catch (Exception ex) {
            Servidor.appendLog(MensajesLog.personajeErrorGeneralAlRegistrar() + paquetePersonaje.getNombre());
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            resultadoOperacion = false;
        } finally {
            HibernateUtil.cerrarSessionEnHilo();
        }
        return resultadoOperacion;
    }

    /**
     * Loguear usuario.
     *
     * @param user
     *            the user
     * @return true, if successful
     */
    public boolean loguearUsuario(final PaqueteUsuario user) {
        HibernateUtil.abrirSessionEnHilo();
        UsuarioCrl ctrl = new UsuarioCrl();
        boolean valido = ctrl.validarUsuario(user.getUsername(), user.getPassword());
        if (valido)
            Servidor.appendLog(MensajesLog.ingresoExitoso(user.getUsername()));
        else
            Servidor.appendLog(MensajesLog.ingresoFallido(user.getUsername()));
        HibernateUtil.cerrarSessionEnHilo();
        return valido;
    }

    /**
     * Actualizar personaje.
     *
     * @param paquetePersonaje
     *            the paquete personaje
     */
    public void actualizarPersonaje(final PaquetePersonaje paquetePersonaje) {
        HibernateUtil.abrirSessionEnHilo();
        PersonajeCtrl ctrlPersonaje = new PersonajeCtrl();
        try {
        	ctrlPersonaje.guardar(new Personaje(paquetePersonaje));
            Servidor.appendLog(MensajesLog.personajeActualizadoExitosamente(paquetePersonaje.getNombre()));
        } catch (Exception ex) {
            Servidor.appendLog(MensajesLog.personajeErrorGeneralAlActualizar(paquetePersonaje.getNombre()));
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            HibernateUtil.cerrarSessionEnHilo();
        }
    }

    /**
     * Gets the personaje.
     *
     * @param user
     *            the user
     * @return the personaje
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public PaquetePersonaje getPersonaje(final PaqueteUsuario user) throws IOException {
        HibernateUtil.abrirSessionEnHilo();
        try {
            return new UsuarioCrl().buscarPorId(user.getUsername()).getPersonaje().getPaquete();
        } catch (Exception e) {
            Servidor.appendLog(MensajesLog.errorAlIntentarRecuperarElPJ(user.getUsername()));
        } finally {
            HibernateUtil.cerrarSessionEnHilo();
        }

        return new PaquetePersonaje();
    }

    /**
     * Gets the usuario.
     *
     * @param usuario
     *            the usuario
     * @return the usuario
     */
    public PaqueteUsuario getUsuario(final String usuario) {
        HibernateUtil.abrirSessionEnHilo();
        UsuarioCrl ctrl = new UsuarioCrl();
        try {
        	return ctrl.buscarPorId(usuario).getUsuario();
        } catch (Exception ex) {
            Servidor.appendLog(MensajesLog.errorAlIntentarRecuperarElUsuario(usuario));
            Servidor.appendLog(ex.getMessage());
        } finally {
            HibernateUtil.cerrarSessionEnHilo();
        }
        return new PaqueteUsuario();
    }

    /**
     * Actualizar inventario.
     *
     * @param paquetePersonaje
     *            the paquete personaje
     */
    public void actualizarMochila(final PaquetePersonaje paquetePersonaje) {
        HibernateUtil.abrirSessionEnHilo();
        PersonajeCtrl ctrl = new PersonajeCtrl();
        ItemCtrl ctrlItem = new ItemCtrl();
        try {
            Personaje personaje = ctrl.buscarPorId(paquetePersonaje.getId());
            personaje.getMochila().clear();
            for(dominio.Item item : paquetePersonaje.getItems())
	           personaje.agregarItem(ctrlItem.buscarPorId(item.getIdItem()));
            ctrl.actualizar(personaje);
        } catch (Exception ex) {
            Servidor.appendLog(MensajesLog.inventarioErrorGeneralAlActualizar(paquetePersonaje.getNombre()));
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            HibernateUtil.cerrarSessionEnHilo();
        }
    }

    /**
     * Actualizar inventario.
     *
     * @param idPersonaje
     *            the id personaje
     */
    public void actualizarMochila(final int idPersonaje) {
        HibernateUtil.abrirSessionEnHilo();
        PersonajeCtrl ctrl = new PersonajeCtrl();
        ItemCtrl ctrlItem = new ItemCtrl();
        PaquetePersonaje paquetePersonaje = Servidor.getPersonajesConectados().get(idPersonaje);
        try {
            Personaje personaje = ctrl.buscarPorId(idPersonaje);
            personaje.getMochila().clear();
            for(dominio.Item item : paquetePersonaje.getItems())
	           personaje.agregarItem(ctrlItem.buscarPorId(item.getIdItem()));
            if (paquetePersonaje.getCantItems() < 9) 
            {
                Item regalo;
            	do
            	{
            		regalo =new ItemCtrl().itemRandom();
            	}
            	while(personaje.getMochila().contains(regalo));
                personaje.agregarItem(regalo);
                paquetePersonaje.anadirItem(regalo.convertToItem());
            }
            ctrl.actualizar(personaje);
        } catch (Exception ex) {
            Servidor.appendLog(MensajesLog.inventarioErrorGeneralAlActualizar(paquetePersonaje.getNombre()));
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            HibernateUtil.cerrarSessionEnHilo();
        }

    }

    /**
     * Actualizar personaje subio nivel.
     *
     * @param paquetePersonaje
     *            the paquete personaje
     */
    public void actualizarPersonajeSubioNivel(final PaquetePersonaje paquetePersonaje) {
        HibernateUtil.abrirSessionEnHilo();
        PersonajeCtrl ctrl = new PersonajeCtrl();
        try {
        	
        	if (paquetePersonaje.getCantItems() < 9) 
            {
                Item regalo;
            	do
            	{
            		regalo =new ItemCtrl().itemRandom();
            	}
            	while(paquetePersonaje.getItems().contains(regalo.convertToItem()));
            	paquetePersonaje.anadirItem(regalo.convertToItem());
            }
        	
        	ctrl.actualizar(new Personaje(paquetePersonaje));
            
            Servidor.appendLog(MensajesLog.personajeActualizadoExitosamente(paquetePersonaje.getNombre()));
        } catch (Exception ex) {
            Servidor.appendLog(MensajesLog.personajeErrorGeneralAlActualizar(paquetePersonaje.getNombre()));
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            HibernateUtil.cerrarSessionEnHilo();
        }
    }
}
