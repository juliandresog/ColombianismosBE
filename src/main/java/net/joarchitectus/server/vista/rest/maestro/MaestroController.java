/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * MaestroController Versión 1.0 13-sep-2017
 *
 * Copyright(c) 2007-2017, Boos IT.
 * admin@boos.com.co
 *
 * http://boos.com.co/license
 */
package net.joarchitectus.server.vista.rest.maestro;

import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import net.joarchitectus.client.datos.dominio.EntidadBase;
import net.joarchitectus.client.datos.dominio.EntidadPerpetua;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.client.entidades.RespuestaRPC;
import net.joarchitectus.client.util.Pair;
import net.joarchitectus.server.servicios.ServicioSesion;
import net.joarchitectus.server.servicios.maestro.ServicioMaestro;
import net.joarchitectus.server.util.PropiedadesLenguage;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller con los metodos genericos usados en cualquier CRUD estandar
 * http://www.kscodes.com/spring-mvc/spring-mvc-controller-extends-another-controller/
 *
 * @version 1.0 13-sep-2017
 * @author josorio
 * @param <T>
 */
@Controller
public abstract class MaestroController<T extends EntidadBase> {

    /* Un comentario sobre la implementacion de esta clase podria ir aqui. */
    /**
     * Referencia el servicio spring que deberá ser inyectado para hacer la
     * logica de negocio
     */
    private ServicioMaestro<T> servicioMaestro;
    /**
     * Objeto que almacena la info del usuario en sesión
     */
    private Usuarios usuarioSession;
    /**
     * Objeto para saber el tipo especifico de clase generica que estamos usando
     */
    protected Class<T> domainClass = extraerClaseDominio();
    /**
     * Variable que me indica el nombre del campo en la base de datos de la
     * tabla que usaremos para filtrar por ejemplo en comboboxes
     */
    protected String campoFiltro = "nombre";
    /**
     * Variable de log para trazas
     */
    private static org.slf4j.Logger log = LoggerFactory.getLogger(MaestroController.class);
    /**
     * Token para validar peticiones sin sesión
     */
    @Deprecated
    protected static final String TOKEN_VAL = "4FWGJS3Jgdd2zRpE3zu63VbFm7b";

    @Autowired
    protected ServicioSesion servicioSesion;

    /**
     * Formato general de fecha en estilo dd.MM.yyyy_HH.mm.ss
     */
    protected SimpleDateFormat dateFormatG = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss");

    /**
     * Constructor
     */
    public MaestroController() {
        log.debug("Se contruye maestro");
        dateFormatG.setLenient(false);
    }

    /**
     * Metodo para formatear parametros tipo fecha que llegan como dd.MM.yyyy.
     * http://kinjouj.github.io/2013/12/spring-webmvc-5-pathvariable.html.
     * //Probando con @DateTimeFormat(pattern = "dd.MM.yyyy")
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss");
        dateFormat.setLenient(false);

        binder.registerCustomEditor(
                Date.class,
                new CustomDateEditor(dateFormat, true)
        );
    }

    /**
     * Metodo a usar a la hora de inyectar el sentidad de la entidad a trabajar.
     *
     * @param servicioMaestro
     */
    public void setServicioMaestro(ServicioMaestro<T> servicioMaestro) {
        this.servicioMaestro = servicioMaestro;
    }

    /**
     * Metodo a usar a la hora de inyectar el usuario en sesion
     *
     * @param usuarioSession
     */
    public void setUsuarioSession(Usuarios usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    /**
     * Para cambiar el nombre del campo filtro
     *
     * @param campoFiltro
     */
    public void setCampoFiltro(String campoFiltro) {
        this.campoFiltro = campoFiltro;
    }

    /**
     * Me dice si la sesion esta inactiva.
     *
     * @return
     */
    protected boolean sessionInActiva() {
        return usuarioSession == null || usuarioSession.getId()== null || usuarioSession.getId() < 1;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @SuppressWarnings("unchecked")// NOPMD - 
    protected Class<T> extraerClaseDominio() {
        if (domainClass == null) {
            ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
            domainClass = (Class) thisType.getActualTypeArguments()[0];
        }
        return domainClass;
    }

    /* Un comentario sobre la implementacion de esta clase podria ir aqui. */
    @RequestMapping("/someAction")
    public ModelAndView someAction() {
        log.warn("Petición al padre controller");

        // Some Code
        ModelAndView retorno = new ModelAndView();
        retorno.addObject("success", true);
        retorno.addObject("message", "OK padre");

        return retorno;
    }

    /**
     * Metodo consultar una entidad en particular
     *
     * @param request
     * @param response
     * @param email
     * @param clave
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET}, value = "/get/{id}")
    public ModelAndView get(
            ServletRequest request, ServletResponse response,
            @PathVariable Long id) {

        ModelAndView retorno = new ModelAndView();

        try {
            if (servicioSesion.validarSesion(request, retorno, usuarioSession) == null) {
                return retorno;
            }
            log.info("Petición de consulta de {}", extraerClaseDominio().getSimpleName());

            RespuestaRPC<T> respuesta = servicioMaestro.cargarEntidad(id);
            if (respuesta.getResultado() == RespuestaRPC.RESULTADO_OK) {
                retorno.addObject("success", true);
                retorno.addObject("datos", respuesta.getObjetoRespuesta());
                retorno.addObject("message", "OK");
            } else {
                retorno.addObject("success", false);
                retorno.addObject("message", respuesta.getDescripcionError());
            }
        } catch (RuntimeException e) {
            log.error("Error", e);
            retorno.addObject("success", false);
            retorno.addObject("message", "Error en tiempo de ejecución en el servidor");
        } catch (Exception e) {
            log.error("Error", e);
            retorno.addObject("success", false);
            retorno.addObject("message", "Error inesperado en el servidor");
        }

        return retorno;
    }

    /**
     * Metodo listar las entidades en particular, este método no pagína
     *
     * @param request
     * @param response
     * @param tipo
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET}, value = "/get")
    public ModelAndView get(
            ServletRequest request, ServletResponse response,
            @RequestParam(value = "tipo", required = false, defaultValue = "" + EntidadPerpetua.TIPO_UNDELETED) Integer tipo) {

        ModelAndView retorno = new ModelAndView();

        try {
            if (servicioSesion.validarSesion(request, retorno, usuarioSession) == null) {
                return retorno;
            }
            log.info("Petición de listar de {}", extraerClaseDominio().getSimpleName());

            List<T> lista;
            if (request.getParameter("query") == null || request.getParameter("query").isEmpty()) {
                lista = servicioMaestro.getEntidades(tipo);
            } else {
                lista = servicioMaestro.getEntidades(request, campoFiltro, tipo);
            }

            retorno.addObject("success", true);
            retorno.addObject("datos", lista);
            retorno.addObject("cantidad", lista.size());
            retorno.addObject("message", "OK");

        } catch (RuntimeException e) {
            log.error("Error", e);
            retorno.addObject("success", false);
            retorno.addObject("message", "Error en tiempo de ejecución en el servidor");
        } catch (Exception e) {
            log.error("Error", e);
            retorno.addObject("success", false);
            retorno.addObject("message", "Error inesperado en el servidor");
        }

        return retorno;
    }

    /**
     * Metodo para eliminar una entidad en particular de la BD
     *
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.DELETE}, value = "/delete/{id}")
    public ModelAndView delete(
            ServletRequest request, ServletResponse response,
            @PathVariable Long id) {

        ModelAndView retorno = new ModelAndView();

        try {
            if (servicioSesion.validarSesion(request, retorno, usuarioSession) == null) {
                return retorno;
            }
            log.info("Petición de eliminar de {} con id {} ", extraerClaseDominio().getSimpleName(), id);

            RespuestaRPC<T> respuesta = servicioMaestro.eliminarEntidad(id);
            if (respuesta.getResultado() == RespuestaRPC.RESULTADO_OK) {
                retorno.addObject("success", true);
                retorno.addObject("message", PropiedadesLenguage.getValueES("mensajeOKEliminado"));
            } else {
                retorno.addObject("success", false);
                retorno.addObject("message", respuesta.getDescripcionError());
            }
        } catch (RuntimeException e) {
            log.error("Error", e);
            retorno.addObject("success", false);
            retorno.addObject("message", "Error en tiempo de ejecución en el servidor");
        } catch (Exception e) {
            log.error("Error", e);
            retorno.addObject("success", false);
            retorno.addObject("message", "Error inesperado en el servidor");
        }

        return retorno;
    }

    /**
     * Metodo para reactivar una entidad en particular de la BD
     *
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.DELETE}, value = "/reactivate/{id}")
    public ModelAndView reactivate(
            ServletRequest request, ServletResponse response,
            @PathVariable Long id) {

        ModelAndView retorno = new ModelAndView();

        try {
            if (servicioSesion.validarSesion(request, retorno, usuarioSession) == null) {
                return retorno;
            }
            log.info("Petición de reactivar de {} con id {} ", extraerClaseDominio().getSimpleName(), id);

            RespuestaRPC<T> respuesta = servicioMaestro.reactivarEntidad(id);
            if (respuesta.getResultado() == RespuestaRPC.RESULTADO_OK) {
                retorno.addObject("success", true);
                retorno.addObject("message", PropiedadesLenguage.getValueES("mensajeOKReactivado"));
            } else {
                retorno.addObject("success", false);
                retorno.addObject("message", respuesta.getDescripcionError());
            }
        } catch (RuntimeException e) {
            log.error("Error", e);
            retorno.addObject("success", false);
            retorno.addObject("message", "Error en tiempo de ejecución en el servidor");
        } catch (Exception e) {
            log.error("Error", e);
            retorno.addObject("success", false);
            retorno.addObject("message", "Error inesperado en el servidor");
        }

        return retorno;
    }

    /**
     * Metodo para guardar una entidad en particular en la BD
     *
     * @param request
     * @param response
     * @param entidad
     * @return
     */
    @RequestMapping(method = {RequestMethod.POST}, value = "/save")
    public ModelAndView save(
            ServletRequest request, ServletResponse response,
            @ModelAttribute("entidad") T entidad) {

        ModelAndView retorno = new ModelAndView();

        try {
            if (servicioSesion.validarSesion(request, retorno, usuarioSession) == null) {
                return retorno;
            }
            log.info("Petición de guardado de {}", extraerClaseDominio().getSimpleName());

            RespuestaRPC<T> respuesta = servicioMaestro.guardarEntidad(entidad);
            if (respuesta.getResultado() == RespuestaRPC.RESULTADO_OK) {
                retorno.addObject("success", true);
                retorno.addObject("datos", respuesta.getObjetoRespuesta());
                retorno.addObject("message", PropiedadesLenguage.getValueES("mensajeOKGuardado"));
            } else {
                retorno.addObject("success", false);
                retorno.addObject("message", respuesta.getDescripcionError());
            }
        } catch (RuntimeException e) {
            log.error("Error", e);
            retorno.addObject("success", false);
            retorno.addObject("message", "Error en tiempo de ejecución en el servidor");
        } catch (Exception e) {
            log.error("Error", e);
            retorno.addObject("success", false);
            retorno.addObject("message", "Error inesperado en el servidor");
        }

        return retorno;
    }

    /**
     * Metodo listar las entidades de forma paginada. Este controller es capaz
     * de paginar, tambien de ordenar y filtrar los resultados segun los
     * parametros recividos en el request.<br/>
     * Parametros aceptados en el http request:<br/>
     * -start: inicio de pagina<br/>
     * -limit: fin de pagina<br/>
     * -query: filtro principal<br/>
     * -filtros: vector con filtros adicionales con este formato json:
     * [{"value":"Marta", "field":"nombre", "comparison":"eq",
     * "type":"string"}]<br/>
     * -SortField: nombre del campo por el que se desea ordenar<br/>
     * -SortDir: Asc o Desc, indica la dirección del ordenamiento.<br/>
     * Ejm:
     * http://localhost:port/html/empresa/getPagin.json?tokenV=xxxval&SortField=nombre&SortDir=Desc&filtros=[{%22value%22:%221%22,%20%22field%22:%22id%22,%20%22comparison%22:%22gt%22,%20%22type%22:%22long%22}]
     * -inactivos: si lo pongo indico que solo deseo ver entidades inactivadas
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET}, value = "/getPagin")
    public ModelAndView getPagin(
            ServletRequest request, ServletResponse response) {

        ModelAndView retorno = new ModelAndView();

        try {
            if (servicioSesion.validarSesion(request, retorno, usuarioSession) == null) {
                return retorno;
            }
            log.info("Petición de listar pagin de {}", extraerClaseDominio().getSimpleName());

            if (request.getParameter("query") != null && !request.getParameter("query").isEmpty()) {
                request.setAttribute("campoFiltro", campoFiltro);
            }

            Pair<Long, List<T>> lista = servicioMaestro.procesarConsulta(request);

            retorno.addObject("success", true);
            retorno.addObject("datos", lista.getB());
            retorno.addObject("cantidad", lista.getA());
            retorno.addObject("message", "OK");

        } catch (RuntimeException e) {
            log.error("Error", e);
            retorno.addObject("success", false);
            retorno.addObject("message", "Error en tiempo de ejecución en el servidor. "+e.getMessage());
        } catch (Exception e) {
            log.error("Error", e);
            retorno.addObject("success", false);
            retorno.addObject("message", "Error inesperado en el servidor");
        }

        return retorno;
    }
}
