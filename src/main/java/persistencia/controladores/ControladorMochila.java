package persistencia.controladores;

import persistencia.dao.MochilaDAO;
import persistencia.dao.impl.MochilaDAOImplHibernate;
import persistencia.entidades.EMochila;

/**
 * The Class ControladorMochila.
 */
public class ControladorMochila {
    private MochilaDAO objetoDAO;

    /**
     * Instantiates a new controlador mochila.
     */
    public ControladorMochila() {
        //CAMBIANDO ESTA LINEA IMPLEMENTO OTRO TIPO DE DAO
        objetoDAO = new MochilaDAOImplHibernate();
    }

    /**
     * Guardar.
     *
     * @param objeto
     *            the objeto
     * @throws Exception
     *             the exception
     */
    public void guardar(final EMochila objeto) throws Exception {
        objetoDAO.guardarOActualizar(objeto);
    }

    /**
     * Actualizar.
     *
     * @param objeto
     *            the objeto
     * @throws Exception
     *             the exception
     */
    public void actualizar(final EMochila objeto) throws Exception {
        objetoDAO.actualizar(objeto);

    }
}
