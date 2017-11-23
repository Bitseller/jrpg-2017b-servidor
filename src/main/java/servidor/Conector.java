package servidor;

import java.io.IOException;
import java.util.Random;

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
 */
public class Conector {
    /**
     * Registrar usuario.
     *
     * @param user  the user
     * @return true, if successful
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
    public boolean registrarPersonaje(final PaquetePersonaje paquetePersonaje, final PaqueteUsuario paqueteUsuario) {
        boolean resultadoOperacion;
        HibernateUtil.abrirSessionEnHilo();
        UsuarioCrl ctrlUsuario = new UsuarioCrl();
        try {
            Usuario u = ctrlUsuario.buscarPorId(paqueteUsuario.getUsername());
            u.setPersonaje(new Personaje(paquetePersonaje));
            ctrlUsuario.guardar(u);
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
        if (valido) {
            Servidor.appendLog(MensajesLog.ingresoExitoso(user.getUsername()));
        } else {
            Servidor.appendLog(MensajesLog.ingresoFallido(user.getUsername()));
        }
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
        int i = 0;
        HibernateUtil.abrirSessionEnHilo();
        PersonajeCtrl ctrl = new PersonajeCtrl();
        ItemCtrl ctrlItem = new ItemCtrl();
        try {
            Personaje personaje = ctrl.buscarPorId(paquetePersonaje.getId());
            personaje.getMochila().clear();
            while (i < paquetePersonaje.getCantItems()) {
                personaje.getMochila().add(ctrlItem.buscarPorId(paquetePersonaje.getItemID(i)));
                i++;
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
     * Actualizar inventario.
     *
     * @param idPersonaje
     *            the id personaje
     */
    public void actualizarMochila(final int idPersonaje) {

        int i = 0;
        HibernateUtil.abrirSessionEnHilo();
        PersonajeCtrl ctrl = new PersonajeCtrl();
        ItemCtrl ctrlItem = new ItemCtrl();
        PaquetePersonaje paquetePersonaje = Servidor.getPersonajesConectados().get(idPersonaje);
        try {
            Personaje personaje = ctrl.buscarPorId(idPersonaje);
            personaje.getMochila().clear();
            while (i < paquetePersonaje.getCantItems()) {
                personaje.getMochila().add(ctrlItem.buscarPorId(paquetePersonaje.getItemID(i)));//ctrlItem.buscarPorId(paquetePersonaje.getItemID(i)));
                i++;
            }

            if (paquetePersonaje.getCantItems() < 9) {
                int itemGanado = new Random().nextInt(ctrlItem.cantidadDeItemsExistente());
                itemGanado += 1;
                Item e =ctrlItem.buscarPorId(itemGanado);
                if(!personaje.getMochila().contains(e)){
                    personaje.getMochila().add(ctrlItem.buscarPorId(itemGanado));
                    paquetePersonaje.anadirItem(e.getId());
                }
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
