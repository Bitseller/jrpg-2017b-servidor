package persistencia.controladores;

import persistencia.dao.ItemDAO;
import persistencia.dao.impl.ItemDAOImplHibernate;
import persistencia.entidades.EItem;

/**
 * The Class ControladorItem.
 */
public class ControladorItem {
    private ItemDAO objetoDAO;

    /**
     * Instantiates a new controlador item.
     */
    public ControladorItem() {
        //CAMBIANDO ESTA LINEA IMPLEMENTO OTRO TIPO DE DAO
        objetoDAO = new ItemDAOImplHibernate();
    }

    /**
     * Guardar.
     *
     * @param objeto
     *            the objeto
     * @throws Exception
     *             the exception
     */
    public void guardar(final EItem objeto) throws Exception {
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
    public void actualizar(final EItem objeto) throws Exception {
        objetoDAO.actualizar(objeto);

    }

    /**
     * Buscar por id.
     *
     * @param id
     *            the id
     * @return the e item
     * @throws Exception
     *             the exception
     */
    public EItem buscarPorId(final int id) throws Exception {
        return objetoDAO.buscarPorId(id);
    }

    /**
     * Cantidad de items existente.
     *
     * @return the int
     */
    public int cantidadDeItemsExistente() {
        return objetoDAO.cantidadDeItemsExistente();

    }

}
