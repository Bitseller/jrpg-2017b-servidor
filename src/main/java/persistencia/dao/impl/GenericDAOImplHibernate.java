package persistencia.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import persistencia.dao.GenericDAO;
import persistencia.hibernate.HibernateUtil;

/**
 * The Class GenericDAOImplHibernate.
 *
 * @param <T>
 *            the generic type
 * @param <ID>
 *            the generic type
 */
public class GenericDAOImplHibernate<T, ID extends Serializable> implements GenericDAO<T, ID> {
    private SessionFactory sessionFactory;
    private static final Logger LOGGER = Logger.getLogger(GenericDAOImplHibernate.class.getName());

    /**
     * Instantiates a new generic DAO impl hibernate.
     */
    public GenericDAOImplHibernate() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public void guardarOActualizar(final T entity) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(entity);
            session.getTransaction().commit();
        } catch (Exception ex) {
            rollback(session);
            throw ex;
        }
    }

    @Override
    public void actualizar(final T entity) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        //.getCurrentSession();	
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        } catch (Exception ex) {
            rollback(session);
            throw ex;
        }
    }

    @Override
    public T buscarPorId(final ID id) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            T entity = session.get(getEntityClass(), id);
            session.getTransaction().commit();
            return entity;
        } catch (Exception ex) {
            rollback(session);
            throw ex;
        }
    }

    @Override
    public void borrar(final ID id) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            T entity = session.get(getEntityClass(), id);
            if (entity != null) {
                session.delete(entity);
                session.getTransaction().commit();
            }
        } catch (Exception ex) {
            rollback(session);
            throw ex;
        }
    }

    @Override
    public List<T> traerTodos() throws Exception {
        Session session = sessionFactory.getCurrentSession();
        try {
            Class<T> cl = getEntityClass();
            CriteriaQuery<T> query = session.getCriteriaBuilder().createQuery(cl);
            Root<T> root = query.from(cl);
            return session.createQuery(query.select(root)).getResultList();
        } catch (Exception ex) {
            rollback(session);
            throw ex;
        }
    }

    /**
     * Gets the entity class.
     *
     * @return the entity class
     */
    private Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Rollback.
     *
     * @param session
     *            the session
     */
    private void rollback(final Session session) {
        try {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } catch (Exception exc) {
            LOGGER.log(Level.WARNING, "Error en la transaccion y no pudo rebertirse", exc);
        }
    }

}
