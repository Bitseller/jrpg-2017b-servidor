package persistencia.controladores;

import persistencia.dao.PersonajeDAO;
import persistencia.dao.impl.PersonajeHibernate;
import persistencia.entidades.Personaje;

/**
 * The Class ControladorPersonaje.
 */
public class PersonajeCtrl {
    private PersonajeDAO objetoDAO;

    /**
     * Instantiates a new controlador personaje.
     */
    public PersonajeCtrl() {
        //CAMBIANDO ESTA LINEA IMPLEMENTO OTRO TIPO DE DAO
        objetoDAO = new PersonajeHibernate();
    }

    /**
     * Guardar.
     *
     * @param objeto
     *            the objeto
     * @throws Exception
     *             the exception
     */
    public void guardar(final Personaje objeto) throws Exception {
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
    public void actualizar(final Personaje objeto) throws Exception {
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
    public Personaje buscarPorId(final int id) throws Exception {
        return objetoDAO.buscarPorId(id);
    }

}
