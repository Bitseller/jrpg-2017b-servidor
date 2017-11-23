package persistencia.controladores;

import persistencia.dao.MochilaDAO;
import persistencia.dao.impl.MochilaHibernate;
import persistencia.entidades.Mochila;

/**
 * The Class ControladorMochila.
 */
public class MochilaCtrl {
    private MochilaDAO objetoDAO;

    /**
     * Instantiates a new controlador mochila.
     */
    public MochilaCtrl() {
        //CAMBIANDO ESTA LINEA IMPLEMENTO OTRO TIPO DE DAO
        objetoDAO = new MochilaHibernate();
    }

    /**
     * Guardar.
     *
     * @param objeto
     *            the objeto
     * @throws Exception
     *             the exception
     */
    public void guardar(final Mochila objeto) throws Exception {
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
    public void actualizar(final Mochila objeto) throws Exception {
        objetoDAO.actualizar(objeto);

    }
}
