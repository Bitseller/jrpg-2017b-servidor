package persistencia.controladores;
import persistencia.dao.UsuarioDAO;
import persistencia.dao.impl.UsuarioDAOImplHibernate;
import persistencia.entidades.EUsuario;

public class ControladorUsuario {
    private UsuarioDAO objetoDAO;

    public ControladorUsuario() {
    	objetoDAO=new UsuarioDAOImplHibernate();//CAMBIANDO ESTA LINEA IMPLEMENTO OTRO TIPO DE DAO
    }

    public void guardar(EUsuario objeto) throws Exception {
    	objetoDAO.guardarOActualizar(objeto);
    }
    
	public boolean existe(String nombreUsuario)
	{
    	return objetoDAO.existe(nombreUsuario);
	}

	public EUsuario buscarPorId(String usuario) throws Exception {
		// TODO Auto-generated method stub
    	return objetoDAO.buscarPorId(usuario);
	}

	public boolean validarUsuario(String username, String password) {
		return objetoDAO.validarUsuario(username,password);
	}
}