package persistencia.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import persistencia.dao.UsuarioDAO;
import persistencia.entidades.Usuario;
import persistencia.hibernate.HibernateUtil;

/**
 * La clase UsuarioDAOImplHibernate.
 * Tiene metodos propios DAO Usuario a parte de los DAO genericos heredados.
 */
public class UsuarioHibernate extends GenericHibernate<Usuario, String> implements UsuarioDAO {

    /**
     * Retorna si existe un usuario en particular
     * @param nombreUsuario
     * 			Nombre del usuario
     * @return booleano, true o false
     */
    @Override
    public boolean existe(final String nombreUsuario) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> c = cb.createQuery(Long.class);
        Root<Usuario> r = c.from(Usuario.class);
        c.select(cb.count(c.from(Usuario.class)));
        ParameterExpression<String> a = cb.parameter(String.class, "userName");
        //r.getModel().getName()
        c.where(cb.equal(r.get("userName"), a));
        return session.createQuery(c).setParameter("userName", nombreUsuario).getSingleResult() > 0;
    }

    /**
     * Corroboro que el user y su pass existan en la db
     * @param username
     * 			Nombre del usuario
     * @param password
     * 			Password
     * @return boolean, true o false
     */
    @Override
    public boolean validarUsuario(final String username, final String password) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Usuario> c = cb.createQuery(Usuario.class);
        Root<Usuario> r = c.from(Usuario.class);
        c.select(r).where(cb.equal(r.get("userName"), username),cb.equal(r.get("password"), password));
        return session.createQuery(c).getResultList().size() > 0;

    }
}
