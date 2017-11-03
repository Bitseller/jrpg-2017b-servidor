package persistencia.dao;

import java.io.Serializable;
import java.util.List;

/**
 * The Interface GenericDAO.
 *
 * @param <T>
 *            the generic type
 * @param <ID>
 *            the generic type
 */
public interface GenericDAO<T, ID extends Serializable> {

    /**
     * Guardar O actualizar.
     *
     * @param entity
     *            the entity
     * @throws Exception
     *             the exception
     */
    void guardarOActualizar(T entity) throws Exception;

    /**
     * Actualizar.
     *
     * @param entity
     *            the entity
     * @throws Exception
     *             the exception
     */
    void actualizar(T entity) throws Exception;

    /**
     * Buscar por id.
     *
     * @param id
     *            the id
     * @return the t
     * @throws Exception
     *             the exception
     */
    T buscarPorId(ID id) throws Exception;

    /**
     * Borrar.
     *
     * @param id
     *            the id
     * @throws Exception
     *             the exception
     */
    void borrar(ID id) throws Exception;

    /**
     * Traer todos.
     *
     * @return the list
     * @throws Exception
     *             the exception
     */
    List<T> traerTodos() throws Exception;
}
