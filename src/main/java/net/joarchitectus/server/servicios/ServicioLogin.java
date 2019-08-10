/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.servicios;

import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.client.entidades.RespuestaRPC;

/**
 *
 * @author josorio
 */
public interface ServicioLogin {
    
    /**
     * Retorna el objeto usuario que se tiene en la sesión
     * @return
     */
    public Usuarios obtenerDatosUsuario();
    /**
     * Verifica si un usuario puede loguearse en el sistema o no, en caso
     * de ser los datos válidos se creará una sesión en el servidor
     * @param mail Documento del usuario para intentar el login
     * @param clave Clave del usuario a loguear (cifrado usando Cifrado.SHA256)
     * @return Nulo si el usuario no existe, en caso de existir, un objeto
     *         de la clase Usuario con los datos del mismo
     */
    public Usuarios loginMail( String email, String clave );
    
    /**
     * Método que valida si el hash de recueprar es correcto
     * @param hash
     * @return Integer
     */
    public Usuarios validarHashRecuperar( String hashRecu );
    
    /**
     * Método que cambia la contraseña de un usuario
     * @param contrasena
     * @param idUsuario
     * @return Integer
     */
    public Integer cambiarContrasenaUsuario( String contrasena, Long idUsuario );
    
    /**
     * Verifica si la sesion sigue activa.
     * @return 
     */
    public RespuestaRPC<Boolean> sessionActiva();
    
}
