package persistencia.dao;

import persistencia.entidades.EUsuario;

/**
 * The Interface UsuarioDAO.
 */
public interface UsuarioDAO extends GenericDAO<EUsuario, String> {

    /**
     * Existe.
     *
     * @param nombreUsuario
     *            the nombre usuario
     * @return true, if successful
     */
    boolean existe(String nombreUsuario);

    /**
     * Validar usuario.
     *
     * @param username
     *            the username
     * @param password
     *            the password
     * @return true, if successful
     */
    boolean validarUsuario(String username, String password);
}
