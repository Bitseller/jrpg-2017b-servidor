package persistencia.dao.impl;


import javax.persistence.Convert;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import persistencia.dao.ItemDAO;
import persistencia.entidades.EItem;
import persistencia.hibernate.HibernateUtil;

public class ItemDAOImplHibernate extends GenericDAOImplHibernate<EItem,Integer> implements ItemDAO {

	public int cantidadDeItemsExistente() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(EItem.class);
		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.uniqueResult()).intValue();
	}
	
}
