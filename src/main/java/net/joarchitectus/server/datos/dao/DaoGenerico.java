package net.joarchitectus.server.datos.dao;

import net.joarchitectus.client.util.Pair;
import net.joarchitectus.client.util.consulta.Consulta;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * Componente de acceso a datos reutilizable para no reescribir las
 * operaciones basicas sobre los objetos del dominio.
 * @param <T>
 */
public interface DaoGenerico<T extends Object> {

    /**
     * Permite cargar una entidad de manera generica
     * @param id el id de la entidad a cargar
     * @return La entidad cargada
     * @throws DataAccessException
     */
    public T getById(int id) throws DataAccessException;
    /**
     * Permite cargar una entidad de manera generica
     * @param id el id de la entidad a cargar
     * @return La entidad cargada
     * @throws DataAccessException
     */
    public T getById( Long id ) throws DataAccessException;

    /**
     * Permite guardar una entidad de manera generica
     * @param entidad la entidad a guardar
     * @throws DataAccessException
     */
    public void guardar(T entidad) throws DataAccessException;
    
    /**
     * Para guardar entidad usando metodo merge
     * @param entity 
     */
    public void guardarMerge(T entity);

    /**
     * Permite borrar una entidad de manera generica
     * @param entidad La entidad a borrar
     * @throws DataAccessException
     */
    public void borrar(T entidad) throws DataAccessException;
    
    /**
     * Para ejecutar borrado fisico de registro sin importar el tipo de entidad
     * @param entity 
     */
    public void borrarFisico(T entity);
    
    /**
     * Permite reactivar una entidad que ha sido previamente eliminada de la BD de forma logica.
     * @param entity
     * @throws Exception 
     */
    public void reactivar(T entity) throws Exception;

    /**
     * Refresca el objeto desde la base de datos aplicando bloqueo para update
     * @param entity
     */
    public void refrescarBloquear(T entity);

    /**
     * Lista todas las entidades en el repositorio del tipo definido,
     * si es de tipo perpetualentity solo muestra los activos
     * @return El listado de instancias
     * @throws DataAccessException
     */
    public List<T> listar() throws DataAccessException;
    
    /**
     * Lista las entidades marcadas como inactivadas.
     * @return
     * @throws DataAccessException 
     */
    public List<T> listarInactivos() throws DataAccessException;
    
    /**
     * Listar entidades a partir de campo y valor
     * @param campo
     * @param valor
     * @return 
     */
    public List<T> listarPor(String campo, Object valor)throws Exception;
    
    /**
     * Listar entidades activas a partir de campo y valor
     * @param campo
     * @param valor
     * @return 
     */
    public List<T> listarActivosPor(String campo, Object valor);
    
    /**
     * Listar de forma paginada los registros
     * @param start
     * @param limit
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    public List<T> listarP(int start, int limit) throws InstantiationException, IllegalAccessException;
    
    /**
     * Para ejecutar consultas de forma dinamica.
     * @param consulta
     * @param offset
     * @param limit
     * @return 
     */
    public Pair<Long, List<T>> listarPorConsulta(Consulta consulta, Integer offset, Integer limit) throws Exception;
    
    /**
     * Usado para ejecutar updates o deletes en la Base de Datos.
     * @param consulta
     * @return 
     */
    public int ejecutarUpdateDelete(String consulta);
    
    /**
     * Ejecutar query suministrado con los parametros dados
     * @param consulta
     * @param parametros
     * @return 
     */
    public List<Map> ejecutarSQLQueryMap(String consulta, List<Pair<String, Object>> parametros);
    
    /**
     * Ejecutar query suministrado con los parametros dados de forma paginada
     * @param consulta
     * @param parametros
     * @param start
     * @param limit
     * @return 
     */
    public List<Map> ejecutarSQLQueryMap(String consulta, List<Pair<String, Object>> parametros, int start, int limit);
    
    /**
     * Ejecutar query suministrado con los parametros dados que solo retornará un elemento
     * @param consulta
     * @param parametros
     * @return 
     */
    public Object ejecutarUniqueSQLQuery(String consulta, List<Pair<String, Object>> parametros);
    
    /**
     * Cantidad de registros activos en BD
     * @return
     * @throws Exception 
     */
    public Long cantidadRegistros() throws Exception;
    
    /**
     * Cantidad de registros inactivos en BD, solo para entidad perpetua
     * @return 
     * @throws java.lang.Exception 
     */
    public Long cantidadRegistrosInactivos()throws Exception;
    
    /**
     * Cantidad de registros activos en BD, solo para entidad perpetua
     * @return 
     * @throws java.lang.Exception 
     */
    public Long cantidadRegistrosActivos()throws Exception;
    
    /**
     * Para validad si un componente de la BD de postgres existe (vista, funcion, etc)
     * @param nombreComponente
     * @return 
     */
    public Boolean PGValidarComponente(String nombreComponente);
    
    /**
     * Para validad si existe un registro en bd con el id indicado
     * @param id
     * @return 
     */
    public Boolean existe(Long id);
}