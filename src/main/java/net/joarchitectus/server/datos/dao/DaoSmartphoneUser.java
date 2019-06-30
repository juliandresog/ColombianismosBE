/**
 * DaoSmartphoneUser
 * Versión 1.0
 * 15/10/2014
 *
 * Copyright(c) 2007-2015, Boos IT.
 * admin@boos.com.co
 *
 * http://boos.com.co/license
 **/

package net.joarchitectus.server.datos.dao;

import java.util.Collection;
import net.joarchitectus.client.datos.dominio.SmartphoneUser;
import net.joarchitectus.client.datos.dominio.Usuarios;


/**
 *
 * @author josorio
 */
public interface DaoSmartphoneUser extends DaoGenerico<SmartphoneUser> {

    /**
     * Lista los smartphone asociados a un usuario.
     * @param idUsuario
     * @return 
     */
    public Collection<SmartphoneUser> listarMoviles(Long idUsuario);

    /**
     * Dispositivo asociado al usuario con udid indicado.
     * @param idUsuario
     * @param udid
     * @return 
     */
    public SmartphoneUser buscarMovil(Long idUsuario, String udid);
    
    /**
     * Dispositivo asociado al usuaroi con el devicetoken indicado.
     * @param idUsuario
     * @param devicetoken
     * @return 
     */
    public SmartphoneUser buscarMovilDT(Long idUsuario, String devicetoken);

    /**
     * Valida si el token existe.
     * @param token
     * @return 
     */
    public boolean validarToken(String token);
    
    /**
     * Valida si el token existe y retorna el usuario asociado..
     * @param token
     * @return 
     */
    public Usuarios validarTokenU(String token);

}
