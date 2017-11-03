package persistencia.dao;

import persistencia.entidades.EUsuario;

public interface  UsuarioDAO  extends GenericDAO<EUsuario,String> {
	boolean existe(String nombreUsuario);

	boolean validarUsuario(String username, String password);
}