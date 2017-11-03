package persistencia.controladores;

import persistencia.dao.UsuarioDAO;
import persistencia.dao.impl.UsuarioDAOImplHibernate;
import persistencia.entidades.EUsuario;

/**
 * The Class ControladorUsuario.
 */
public class ControladorUsuario {
    private UsuarioDAO objetoDAO;

    /**
     * Instantiates a new controlador usuario.
     */
    public ControladorUsuario() {
        objetoDAO = new UsuarioDAOImplHibernate();//CAMBIANDO ESTA LINEA IMPLEMENTO OTRO TIPO DE DAO
    }

    /**
     * Guardar.
     *
     * @param objeto
     *            the objeto
     * @throws Exception
     *             the exception
     */
    public void guardar(final EUsuario objeto) throws Exception {
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
    public EUsuario buscarPorId(final String usuario) throws Exception {
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
