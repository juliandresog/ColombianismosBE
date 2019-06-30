/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.datos.dao;

import net.joarchitectus.client.datos.dominio.Usuarios;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author jdbotero@gmail.com
 */
public interface DaoUsuario extends DaoGenerico<Usuarios>
{
    /**
     *
     * @param documento
     * @return
     * @throws DataAccessException
     */
    public Usuarios getActivoByDocumento( String documento ) throws DataAccessException;

    /**
     *
     * @param documento
     * @param clave
     * @return
     * @throws DataAccessException
     */
    public Usuarios getActivoByDocumentoAndClave( String documento, String clave ) throws DataAccessException;

    /**
     * 
     * @param llave
     * @return
     * @throws DataAccessException
     */
    public Usuarios getByLlaveCambioClave( String llave ) throws DataAccessException;

    
     /**
     * Obtiene los usuarios activos (no borrados)
      * @param likeNombre para filtrar usando like si null no se filtra
     * @return
     * @throws DataAccessException
     */
    public List<Usuarios> getUsuariosActivos( String likeNombre ) throws DataAccessException;

    /**
     * Obtiene los usuarios con el correo suministrado
     * @param correo
     * @return
     * @throws DataAccessException
     */
    public List<Usuarios> getActivoByCorreo( String correo) throws DataAccessException;

     

     /**
     * Obtiene los usuarios con el documento suministrado cuyo id sea distinto al suministrado
     * @param documento
     * @param id
     * @return
     * @throws DataAccessException
     */
    public List<Usuarios> getActivoByDocumentoAndNotId( String documento, Long id );
     /**
     * Obtiene los usuarios con el correo suministrado cuyo id sea distinto al suministrado
     * @param correo
     * @param id
     * @return
     * @throws DataAccessException
     */
    public List<Usuarios> getActivoByCorreoAndNotId( String correo, Long id );

    
    

     /**
     * Obtiene los usuarios activos (no borrados)
     * @param idRol
      * @param likeNombre para filtrar usando like si null no se filtra
     * @return
     * @throws DataAccessException
     */
    public List<Usuarios> getUsuariosActivosByRol( int idRol, String likeNombre ) throws DataAccessException;

    

     /**
     * Obtiene los usuarios inactivos (borrados)
     * @return
     * @throws DataAccessException
     */
    public List<Usuarios> getUsuariosInactivos( ) throws DataAccessException;

    /**
     * Lista los usuarios activos en los limites indicados.
     * @param inicio
     * @param limit
     * @return 
     */
    public List<Usuarios> listaUsuarios(int inicio, int limit);
    /**
     * Lista los usuarios activos en los limites y filtros indicados indicados.
     * @param documento si null no filtra por documento
     * @param nombrefull si null no filtra por nombre
     * @param inicio
     * @param limit
     * @return 
     */
    public List<Usuarios> listaUsuarios(String documento, String nombrefull, int inicio, int limit);

   /**
    * Cantidad de todos los usuarios activos sin paginar 
    * @return 
    */
    public Long cantidadListaUsuarios();
    /**
     * Cantidad de todos los usuarios activos sin paginar usando filtros
     * @param documento si null no filtra por documento
     * @param nombrefull si null no filtra por nombre
     */
    public Long cantidadListaUsuarios(String documento, String nombrefull);

    /**
     * Busca usuario activo por email y clave
     * @param email
     * @param clave
     * @return 
     */
    public Usuarios getActivoByEmailAndClave(String email, String clave);

     /**
     * Obtiene los usuarios activos que sean propietarios (no borrados)
     * @param idRol
      * @param likeNombre para filtrar usando like si null no se filtra
     * @return
     * @throws DataAccessException
     */
    public List<Usuarios> getUsuariosActivosPropietarios( long idRol  ) throws DataAccessException;

    /**
     * Busca un usuario por su correo
     * @param correo
     * @return 
     */
    public Usuarios getActivoByCorreo2(String correo);
    
    /**
     * Para validar un token de autenticacion
     * @param token
     * @return 
     */
    public boolean validarToken(String token);
    
    /**
     * Para validar un token de autenticacion y retornar el usuario asociado
     * @param token
     * @return 
     */
    public Usuarios validarTokenU(String token);
}
