package persistencia.controladores;

import persistencia.dao.PersonajeDAO;
import persistencia.dao.impl.PersonajeDAOImplHibernate;
import persistencia.entidades.EPersonaje;

/**
 * The Class ControladorPersonaje.
 */
public class ControladorPersonaje {
    private PersonajeDAO objetoDAO;

    /**
     * Instantiates a new controlador personaje.
     */
    public ControladorPersonaje() {
        //CAMBIANDO ESTA LINEA IMPLEMENTO OTRO TIPO DE DAO
        objetoDAO = new PersonajeDAOImplHibernate();
    }

    /**
     * Guardar.
     *
     * @param objeto
     *            the objeto
     * @throws Exception
     *             the exception
     */
    public void guardar(final EPersonaje objeto) throws Exception {
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
    public void actualizar(final EPersonaje objeto) throws Exception {
        objetoDAO.actualizar(objeto);

    }

    /**
     * Buscar por id.
     *
     * @param id
     *            the id
     * @return the e personaje
     * @throws Exception
     *             the exception
     */
    public EPersonaje buscarPorId(final int id) throws Exception {
        return objetoDAO.buscarPorId(id);
    }

}
