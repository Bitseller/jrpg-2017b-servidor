package persistencia.controladores;

import persistencia.dao.MochilaDAO;
import persistencia.dao.impl.MochilaDAOImplHibernate;
import persistencia.entidades.EMochila;

public class ControladorMochila {
	 private MochilaDAO objetoDAO;

	    public ControladorMochila() {
	    	objetoDAO=new MochilaDAOImplHibernate();//CAMBIANDO ESTA LINEA IMPLEMENTO OTRO TIPO DE DAO
	    }

	    public void guardar(EMochila objeto) throws Exception {
	    	objetoDAO.guardarOActualizar(objeto);
	    }

		public void actualizar(EMochila objeto) throws Exception {
	    	objetoDAO.actualizar(objeto);
			
		}
}
