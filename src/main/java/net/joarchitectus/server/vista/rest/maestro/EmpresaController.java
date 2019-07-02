/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * EmpresaController Versión 1.0 14-sep-2017
 *
 * Copyright(c) 2007-2017, Boos IT.
 * admin@boos.com.co
 *
 * http://boos.com.co/license
 */
package net.joarchitectus.server.vista.rest.maestro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.joarchitectus.client.datos.dominio.Empresa;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.client.datos.model.EmpresaModel;
import net.joarchitectus.client.entidades.RespuestaRPC;
import net.joarchitectus.server.servicios.maestro.ServicioAdminEmpresa;
import net.joarchitectus.server.util.ModeloConverter;
import net.joarchitectus.server.util.PropiedadesLenguage;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller spring tipo REST json para CRUD de empresas.
 *
 * @version 1.0 14-sep-2017
 * @author josorio
 */
@Controller
@RequestMapping("/empresa")
public class EmpresaController extends MaestroController<Empresa> {

    /* Un comentario sobre la implementacion de esta clase podria ir aqui. */
    private ServicioAdminEmpresa servicioAdminEmpresa;

    private Usuarios usuarioSession;

    protected static org.slf4j.Logger log2 = LoggerFactory.getLogger(EmpresaController.class);

    @Autowired
    public void setServicioAdminEmpresa(ServicioAdminEmpresa servicioAdminEmpresa) {
        this.servicioAdminEmpresa = servicioAdminEmpresa;
        super.setServicioMaestro(servicioAdminEmpresa);
    }

    @Autowired
    @Override
    public void setUsuarioSession(Usuarios usuarioSession) {
        this.usuarioSession = usuarioSession;
        super.setUsuarioSession(usuarioSession);
    }

    /**
     * Constructor Acá puedo cambiar variables si lo necesito como "campoFiltro"
     */
    public EmpresaController() {
        log2.debug("Se contruye maestro " + this.getClass().getSimpleName());
    }
    
    /* Un comentario sobre la implementacion de esta clase podria ir aqui. */
    @RequestMapping("/details")
    @Override
    public ModelAndView someAction() {
        log2.warn("Petición al hijo controller");

        // Some Code
        ModelAndView retorno = new ModelAndView();
        retorno.addObject("success", true);
        retorno.addObject("message", "OK hijo");

        return retorno;
    }

    /**
     * Metodo para guardar un contacto en el sistema usando modelo. Esto más a
     * modo de ejemplo ya que con el /save es suficiente sino se manda la ciudad
     *
     * @param request
     * @param response
     * @param entidad
     * @return
     */
    @RequestMapping(method = {RequestMethod.POST}, value = "/guardar")
    public ModelAndView guardar(
            HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("model") EmpresaModel model) {

        ModelAndView retorno = new ModelAndView();

        try {
            if (servicioSesion.validarSesion(request, retorno, usuarioSession) == null) {
                return retorno;
            }
            log2.info("Petición de guardado de contacto en {}", extraerClaseDominio().getSimpleName());

            Empresa entidad;
            if (model.getId()!= null) {
                entidad = servicioAdminEmpresa.cargarEntidad(model.getId()).getObjetoRespuesta();
            } else {
                entidad = new Empresa();
            }

            //creo entidad a partir del modelo buscando no reemplazar datos con nulos
            entidad = ModeloConverter.convertirModeloToEntidadEmpresa(entidad, model, false);

            RespuestaRPC<Empresa> respuesta = servicioAdminEmpresa.guardarEntidad(entidad);
            if (respuesta.getResultado() == RespuestaRPC.RESULTADO_OK) {
                retorno.addObject("success", true);
                retorno.addObject("datos", respuesta.getObjetoRespuesta());
                retorno.addObject("message", PropiedadesLenguage.getValueES("mensajeOKGuardado"));
            } else {
                retorno.addObject("success", false);
                retorno.addObject("message", respuesta.getDescripcionError());
            }
        } catch (RuntimeException e) {
            log2.error("Error", e);
            retorno.addObject("success", false);
            retorno.addObject("message", "Error en tiempo de ejecución en el servidor");
        } catch (Exception e) {
            log2.error("Error", e);
            retorno.addObject("success", false);
            retorno.addObject("message", "Error inesperado en el servidor");
        }

        return retorno;
    }
}
