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
 * La clase GenericDAOImplHibernate.
 * Implementa un DAO generico para todas las Entidades
 * @param <T>
 *            Tipo generico de Entidad
 * @param <ID>
 *            Tipo generico de ID
 */
public class GenericHibernate<T, ID extends Serializable> implements GenericDAO<T, ID> {
    private SessionFactory sessionFactory;
    private static final Logger LOGGER = Logger.getLogger(GenericHibernate.class.getName());

    /**
     * Recupero la SessionFactory creada por HibernateUtil
     */
    public GenericHibernate() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    /**
     * Guardo o Actualizo a una entidad
     * @param entity
     * 			Entidad a guardar o actualizar
     */
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
 
    /**
     * Actualizo una entidad
     * @param entity
     * 			entidad a actualizar
     */
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

    /**
     * Recupero el registro/entidad que corresponda a la ID pasada
     * @param id
     * 		Identificador del registro/entidad a buscar
     * @return La entidad si existe
     */
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
    
    /**
     * Elimino el registro/entidad que corresponda a la ID pasada
     * @param id
     * 		Identificador del registro/entidad a eliminar
     */
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

    /**
     * Recupero en una lista todos los registros de una tabla
     * @return la lista
     */
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
     * Consigo la clase de la entidad
     *
     * @return la clase de la entidad
     */
    private Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Rollback.
     *
     * @param session
     *            la session
     */
    private void rollback(final Session session) {
        try {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } catch (Exception exc) {
            LOGGER.log(Level.WARNING, "Error en la transaccion y no pudo revertirse", exc);
        }
    }

}
