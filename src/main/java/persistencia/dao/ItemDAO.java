package persistencia.dao;

import persistencia.entidades.EItem;

/**
 * The Interface ItemDAO.
 */
public interface ItemDAO extends GenericDAO<EItem, Integer> {

    /**
     * Cantidad de items existente.
     *
     * @return the int
     */
    public int cantidadDeItemsExistente();
}
