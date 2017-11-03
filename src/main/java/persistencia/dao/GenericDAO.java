package persistencia.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T,ID extends Serializable> {
    void guardarOActualizar(T entity) throws Exception;
    
    void actualizar(T entity) throws Exception;
    
    T buscarPorId(ID id) throws Exception;
    
    void borrar(ID id) throws Exception;
    
    List<T> traerTodos() throws Exception;
}