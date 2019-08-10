/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.servicios;

import javax.servlet.ServletRequest;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.server.vista.rest.api.RespuestaRest;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author josorio
 */
public interface ServicioSesion {
    
    /**
     * Genero un token tipo hash usando md5
     *
     * @param usuario
     * @return String
     */
    public String generarToken(Usuarios usuario);
    
    /**
     * Valida que el token exista en la lista de usuarios.
     *
     * @param token
     * @return boolean
     */
    public boolean validarToken(String token);
    
    /**
     * Valida que el token exista en la lista de usuarios.
     *
     * @param token
     * @return boolean
     */
    public Usuarios validarTokenU(String token);
    
    /**
     * Para validar sesion desde un controller
     * @param request
     * @param retorno
     * @param usuarioSession
     * @return 
     */
    public Usuarios validarSesion(ServletRequest request, ModelAndView retorno, Usuarios usuarioSession);
    
    /**
     * Para validar sesion desde un controller.
     *
     * @param request
     * @param retorno
     * @param usuarioSession
     * @return
     */
    public Usuarios validarSesion(ServletRequest request, RespuestaRest<?> retorno, Usuarios usuarioSession);
}
