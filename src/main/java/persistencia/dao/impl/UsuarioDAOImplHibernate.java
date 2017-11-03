package persistencia.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import persistencia.dao.UsuarioDAO;
import persistencia.entidades.EUsuario;
import persistencia.hibernate.HibernateUtil;

/**
 * The Class UsuarioDAOImplHibernate.
 */
public class UsuarioDAOImplHibernate extends GenericDAOImplHibernate<EUsuario, String> implements UsuarioDAO {
    @Override
    public boolean existe(final String nombreUsuario) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> c = cb.createQuery(Long.class);
        Root<EUsuario> r = c.from(EUsuario.class);
        c.select(cb.count(c.from(EUsuario.class)));
        ParameterExpression<String> a = cb.parameter(String.class, "userName");
        c.where(cb.equal(r.get("userName"), a));//r.getModel().getName()
        return session.createQuery(c).setParameter("userName", nombreUsuario).getSingleResult() > 0;
    }

    @Override
    public boolean validarUsuario(final String username, final String password) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<EUsuario> c = cb.createQuery(EUsuario.class);
        Root<EUsuario> r = c.from(EUsuario.class);
        c.select(r).where(cb.equal(r.get("userName"), username));
        c.select(r).where(cb.equal(r.get("password"), password));
        return session.createQuery(c).getResultList().size() > 0;

    }
}
