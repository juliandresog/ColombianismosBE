/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.vista.rest.acceso;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.server.util.PropiedadesLenguage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import net.joarchitectus.server.servicios.*;

/**
 *
 * @author josorio
 */
@Controller
@RequestMapping("/login/")
public class LoginController {

    protected static org.slf4j.Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private Usuarios usuarioSession;
    @Autowired
    private ServicioLogin servicioLogin;
//    @Autowired
//    private ServicioRecuperarClave servicioRecuperarClave;

    /**
     * Me dice si la sesion esta inactiva.
     *
     * @return
     */
    private boolean sessionInActiva() {
        return usuarioSession == null || usuarioSession.getId() == null || usuarioSession.getId() < 1;
    }

    /**
     * Metodo para autenticación de usuario
     *
     * @param request
     * @param response
     * @param email
     * @param clave
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/loginMail")
    public ModelAndView loginMail(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "email", required = true, defaultValue = "NA") String email,
            @RequestParam(value = "clave", required = true, defaultValue = "NA") String clave) {

        ModelAndView retorno = new ModelAndView();

        try {
//            if (sessionInActiva()) {
//                retorno.addObject("success", false);
//                retorno.addObject("message", "No tiene una sesión activa");
//                return retorno;
//            }
            log.warn("Petición de logueo de {}", email);

            Usuarios usuario = servicioLogin.loginMail(email, clave);
            if (usuario != null) {
                //guaro información en sesion.
                usuarioSession.copiarDatos(usuario);

                // Seteo en sesión la variable que nos permite monitorear
                // el usuario desde el manager del Tomcat
                String usuarioTomcat = usuarioSession.getNombreCompleto()
                        + " BD( " + usuarioSession.getDocumento()+ " )";
                request.getSession().setAttribute("userName",
                        usuarioTomcat);

                retorno.addObject("success", true);
                retorno.addObject("datos", usuario);
                retorno.addObject("message", "OK");
            } else {
                retorno.addObject("success", false);
                retorno.addObject("message", "Usuario o clave incorrecto, o no ha verificado el email de activación de cuenta");
            }
        } catch (Exception e) {
            log.error("Error", e);
            retorno.addObject("success", false);
            retorno.addObject("message", "Error inesperado en el servidor");
        }

        return retorno;
    }

//    /**
//     * Metodo para solicitar llave de recuperación de clave
//     *
//     * @param request
//     * @param response
//     * @param email
//     * @return
//     */
//    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/solicitarLlave")
//    public ModelAndView solicitarLlave(
//            ServletRequest request, ServletResponse response,
//            @RequestParam(value = "email", required = true, defaultValue = "NA") String email) {
//
//        ModelAndView retorno = new ModelAndView();
//
//        try {
////            if (sessionInActiva()) {
////                retorno.addObject("success", false);
////                retorno.addObject("message", "No tiene una sesión activa");
////                return retorno;
////            }
//            log.warn("Petición de llave de recuperación de {}", email);
//
//            int resultado = servicioRecuperarClave.solicitarLlaveMail(email);
//
//            if (resultado == 0) {
//                retorno.addObject("success", true);
//                retorno.addObject("message", PropiedadesLenguage.getValueES("mensajeCorreoModificacionLlave"));
//            } else if (resultado == 1) {
//                retorno.addObject("success", false);
//                retorno.addObject("message", PropiedadesLenguage.getValueES("mensajeUsuarioNoExiste"));
//            } else if (resultado == 2) {
//                retorno.addObject("success", false);
//                retorno.addObject("message", PropiedadesLenguage.getValueES("mensajeErrorProcesandoSolicitud"));
//            } else if (resultado == 3) {
//                retorno.addObject("success", false);
//                retorno.addObject("message", PropiedadesLenguage.getValueES("mensajeUsuarioNoPoseeCorreo"));
//            } else {
//                retorno.addObject("success", false);
//                retorno.addObject("message", PropiedadesLenguage.getValueES("mensajeErrorInesperado"));
//            }
//        } catch (Exception e) {
//            log.error("Error", e);
//            retorno.addObject("success", false);
//            retorno.addObject("message", "Error inesperado en el servidor");
//        }
//
//        return retorno;
//    }
//
//    /**
//     * Metodo para solicitar llave de recuperación de clave
//     *
//     * @param request
//     * @param response
//     * @param llave
//     * @param claveNueva
//     * @return
//     */
//    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/cambiarClave")
//    public ModelAndView cambiarClave(
//            ServletRequest request, ServletResponse response,
//            @RequestParam(value = "llave", required = true, defaultValue = "NA") String llave,
//            @RequestParam(value = "clave_nueva", required = true, defaultValue = "NA") String claveNueva) {
//
//        ModelAndView retorno = new ModelAndView();
//
//        try {
////            if (sessionInActiva()) {
////                retorno.addObject("success", false);
////                retorno.addObject("message", "No tiene una sesión activa");
////                return retorno;
////            }
//            log.warn("Petición de cambio de clave de llave {}", llave);
//
//            int resultado = servicioRecuperarClave.cambiarClave(llave, claveNueva);
//
//            if (resultado == 0) {
//                retorno.addObject("success", true);
//                retorno.addObject("message", PropiedadesLenguage.getValueES("mensajeOkClaveModificada"));
//            } else if (resultado == 1) {
//                retorno.addObject("success", false);
//                retorno.addObject("message", PropiedadesLenguage.getValueES("mensajeErrorLlaveInvalida"));
//            } else if (resultado == 2) {
//                retorno.addObject("success", false);
//                retorno.addObject("message", PropiedadesLenguage.getValueES("mensajeErrorCambioClave"));
//            } else {
//                retorno.addObject("success", false);
//                retorno.addObject("message", PropiedadesLenguage.getValueES("mensajeErrorInesperado"));
//            }
//        } catch (Exception e) {
//            log.error("Error", e);
//            retorno.addObject("success", false);
//            retorno.addObject("message", "Error inesperado en el servidor");
//        }
//
//        return retorno;
//    }

    /**
     * Metodo para solicitar la información del usuario en sesión
     *
     * @param request
     * @param response
     * @param llave
     * @param claveNueva
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/obtenerDatosUsuario")
    public ModelAndView obtenerDatosUsuario(
            ServletRequest request, ServletResponse response) {

        ModelAndView retorno = new ModelAndView();

        try {
            if (sessionInActiva()) {
                retorno.addObject("success", false);
                retorno.addObject("message", "No tiene una sesión activa");
                return retorno;
            }
            log.debug("Petición de datos del usuario en sesión");

            Usuarios resultado = servicioLogin.obtenerDatosUsuario();

            if (resultado != null) {
                retorno.addObject("success", true);
                retorno.addObject("datos", resultado);
                retorno.addObject("message", "OK");
            } else {
                retorno.addObject("success", false);
                retorno.addObject("message", "No se pudo localizar información del usuaro ");
            }
        } catch (Exception e) {
            log.error("Error", e);
            retorno.addObject("success", false);
            retorno.addObject("message", "Error inesperado en el servidor");
        }

        return retorno;
    }

}
