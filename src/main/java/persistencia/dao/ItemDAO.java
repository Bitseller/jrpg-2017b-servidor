package persistencia.dao;

import persistencia.entidades.Item;

/**
 * The Interface ItemDAO.
 */
public interface ItemDAO extends GenericDAO<Item, Integer> {

    /**
     * Cantidad de items existente.
     *
     * @return the int
     */
    public int cantidadDeItemsExistente();

	public Item itemRandom() throws Exception;
}
