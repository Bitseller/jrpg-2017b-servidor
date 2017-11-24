package persistencia.dao.impl;

import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import persistencia.controladores.ItemCtrl;
import persistencia.dao.ItemDAO;
import persistencia.entidades.Item;
import persistencia.hibernate.HibernateUtil;

/**
 * La clase ItemDAOImplHibernate.
 * Tiene metodos propios DAO item a parte de los DAO genericos heredados.
 */
public class ItemHibernate extends GenericHibernate<Item, Integer> implements ItemDAO {

	/**
	 * Retorna la cantidad de items existente en la db
	 * @return Entero con la cantidad de items en la db
	 */
    @Override
    public int cantidadDeItemsExistente() {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(Item.class);
        criteria.setProjection(Projections.rowCount());
        return ((Long) criteria.uniqueResult()).intValue();
    }

	@Override
	public Item itemRandom() throws Exception {
		int idItem = new Random().nextInt(cantidadDeItemsExistente());
		return new ItemCtrl().buscarPorId(idItem+1);
	}

}
