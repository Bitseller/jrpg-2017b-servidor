package persistencia.controladores;

import persistencia.dao.ItemDAO;
import persistencia.dao.impl.ItemHibernate;
import persistencia.entidades.Item;

/**
 * The Class ControladorItem.
 */
public class ItemCtrl {
    private ItemDAO objetoDAO;

    /**
     * Instantiates a new controlador item.
     */
    public ItemCtrl() {
        //CAMBIANDO ESTA LINEA IMPLEMENTO OTRO TIPO DE DAO
        objetoDAO = new ItemHibernate();
    }

    /**
     * Guardar.
     *
     * @param objeto
     *            the objeto
     * @throws Exception
     *             the exception
     */
    public void guardar(final Item objeto) throws Exception {
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
    public void actualizar(final Item objeto) throws Exception {
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
    public Item buscarPorId(final int id) throws Exception {
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
