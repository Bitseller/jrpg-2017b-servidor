package persistencia.controladores;

import persistencia.dao.UsuarioDAO;
import persistencia.dao.impl.UsuarioHibernate;
import persistencia.entidades.Usuario;

/**
 * The Class ControladorUsuario.
 */
public class UsuarioCrl {
    private UsuarioDAO objetoDAO;

    /**
     * Instantiates a new controlador usuario.
     */
    public UsuarioCrl() {
        //CAMBIANDO ESTA LINEA IMPLEMENTO OTRO TIPO DE DAO
        objetoDAO = new UsuarioHibernate();
    }

    /**
     * Guardar.
     *
     * @param objeto
     *            the objeto
     * @throws Exception
     *             the exception
     */
    public void guardar(final Usuario objeto) throws Exception {
        objetoDAO.guardarOActualizar(objeto);
    }

    /**
     * Existe.
     *
     * @param nombreUsuario
     *            the nombre usuario
     * @return true, if successful
     */
    public boolean existe(final String nombreUsuario) {
        return objetoDAO.existe(nombreUsuario);
    }

    /**
     * Buscar por id.
     *
     * @param usuario
     *            the usuario
     * @return the e usuario
     * @throws Exception
     *             the exception
     */
    public Usuario buscarPorId(final String usuario) throws Exception {
        // TODO Auto-generated method stub
        return objetoDAO.buscarPorId(usuario);
    }

    /**
     * Validar usuario.
     *
     * @param username
     *            the username
     * @param password
     *            the password
     * @return true, if successful
     */
    public boolean validarUsuario(final String username, final String password) {
        return objetoDAO.validarUsuario(username, password);
    }
}
