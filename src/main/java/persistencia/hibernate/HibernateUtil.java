package persistencia.hibernate;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.context.internal.ThreadLocalSessionContext;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static synchronized void crearSessionFactory() {
        if (sessionFactory == null) {
        	StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        	Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
        	sessionFactory = metaData.getSessionFactoryBuilder().build();
        }
    }

    public static void abrirSessionEnHilo() {
    	 if (sessionFactory==null)
             crearSessionFactory();
    	 Session session = sessionFactory.openSession();
    	 ThreadLocalSessionContext.bind(session);
    }


    public static SessionFactory getSessionFactory() {
        if (sessionFactory==null)
            crearSessionFactory();
        return sessionFactory;
    }

    public static void cerrarSessionEnHilo() {
        Session session = ThreadLocalSessionContext.unbind(sessionFactory);
        if (session!=null)
            session.close();
    }

    public static void cerrarSessionFactory() {
        if ( sessionFactory!=null && !sessionFactory.isClosed() )
            sessionFactory.close();
    }

    public static void eliminarSessionFactory() {  
    	if ( sessionFactory != null )
    	{	
    		cerrarSessionFactory();
    		sessionFactory = null;
    	}
    }
}