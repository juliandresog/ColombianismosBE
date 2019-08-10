/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.servicios.local;

import java.util.Date;
import javax.servlet.ServletRequest;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.client.util.Sumador;
import net.joarchitectus.server.datos.dao.DaoSmartphoneUser;
import net.joarchitectus.server.datos.dao.DaoUsuario;
import net.joarchitectus.server.servicios.ServicioSesion;
import net.joarchitectus.server.vista.rest.api.RespuestaRest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author yaristizabal
 */
@Service(value = "servicioSesion")
public class ServicioSesionImpl implements ServicioSesion {
    
    protected static Logger log = Logger.getLogger(ServicioSesionImpl.class);
    
    @Autowired
    private DaoSmartphoneUser daoSmartphoneUser;
    @Autowired
    private DaoUsuario daoUsuario;
    
    /**
     * Genero un token tipo hash usando md5
     *
     * @param usuario
     * @return String
     */
    public String generarToken(Usuarios usuario) {
        String token = Sumador.suma(usuario.getId() + "-" + new Date());
        usuario.setTokenAuth(token);
        daoUsuario.guardar(usuario);
        log.info("Sesion para " + token);
        return token;
    }

    /**
     * Valida que el token exista en la lista de usuarios.
     *
     * @param token
     * @return boolean
     */
    public boolean validarToken(String token) {
        log.info("Validando para " + token);
        boolean validar = daoUsuario.validarToken(token);

        if (!validar) {//reviso los otros smartphones
            validar = daoSmartphoneUser.validarToken(token);
        }

        return validar;
    }

    /**
     * Valida que el token exista en la lista de usuarios.
     *
     * @param token
     * @return boolean
     */
    public Usuarios validarTokenU(String token) {
        log.info("Validando para " + token);
        Usuarios validar = daoUsuario.validarTokenU(token);

        if (validar == null) {//reviso los otros smartphones
            validar = daoSmartphoneUser.validarTokenU(token);
        }

        return validar;
    }
    
    private boolean sessionInActiva(Usuarios usuarioSession) {
        return usuarioSession == null || usuarioSession.getId() == null || usuarioSession.getId() < 1;
    }
    
    /**
     * 
     * @param request
     * @param retorno
     * @return valida session y retorna un usuario en caso de que exista de lo contrario null
     */
    @Override
    public Usuarios validarSesion(ServletRequest request, ModelAndView retorno, Usuarios usuarioSession){
        if (sessionInActiva(usuarioSession)) {
            if (request.getParameter("token") == null) {
                retorno.addObject("success", false);
                retorno.addObject("message", "No tiene una sesión activa");
                return null;
            } else {
                Usuarios userWS = validarTokenU(request.getParameter("token"));
                if (userWS == null) {
                    retorno.addObject("success", false);
                    retorno.addObject("message", "No tiene una sesión activa");
                    return null;
                } else {
                    usuarioSession.copiarDatos(userWS); 
                    return usuarioSession;
                }
            }
        }else{
            return usuarioSession;
        }
    }
    
    /**
     *
     * @param request
     * @param retorno
     * @return valida session y retorna un usuario en caso de que exista de lo
     * contrario null
     */
    @Override
    public Usuarios validarSesion(ServletRequest request, RespuestaRest<?> retorno, Usuarios usuarioSession) {
        if (sessionInActiva(usuarioSession)) {
            if (request.getParameter("token") == null) {
                retorno.setSuccess(false);
                retorno.setMessage("No tiene una sesión activa");
                return null;
            } else {
                Usuarios userWS = validarTokenU(request.getParameter("token"));
                if (userWS == null) {
                    retorno.setSuccess(false);
                    retorno.setMessage("No tiene una sesión activa");
                    return null;
                } else {
                    //Indico al usuario cuales son los roles y permisos de este
                    //servicioPermisosYRoles.alimentarRolesPermisosUsuario(usuarioSession);
                    //copio datos
                    usuarioSession.copiarDatos(userWS);
                    return usuarioSession;
                }
            }
        } else {
            return usuarioSession;
        }
    }
}
