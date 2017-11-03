package persistencia.controladores;

import persistencia.dao.PersonajeDAO;
import persistencia.dao.impl.PersonajeDAOImplHibernate;
import persistencia.entidades.EPersonaje;

public class ControladorPersonaje {
    private PersonajeDAO objetoDAO;

    public ControladorPersonaje() {
    	objetoDAO=new PersonajeDAOImplHibernate();//CAMBIANDO ESTA LINEA IMPLEMENTO OTRO TIPO DE DAO
    }

    public void guardar(EPersonaje objeto) throws Exception {
    	objetoDAO.guardarOActualizar(objeto);
    }

	public void actualizar(EPersonaje objeto) throws Exception {
    	objetoDAO.actualizar(objeto);
		
	}

	public EPersonaje buscarPorId(int id) throws Exception {
    	return objetoDAO.buscarPorId(id);
	}
    
}