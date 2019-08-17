/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.vista.rest.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.joarchitectus.client.datos.dominio.Definicion;
import net.joarchitectus.client.datos.dominio.Empresa;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.client.datos.model.DefinicionModel;
import net.joarchitectus.client.datos.model.EmpresaModel;
import net.joarchitectus.client.entidades.RespuestaRPC;
import net.joarchitectus.server.servicios.maestro.ServicioDefinicion;
import net.joarchitectus.server.util.ModeloConverter;
import net.joarchitectus.server.util.PropiedadesLenguage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author josorio
 */
@RestController
@RequestMapping("/admin/definicion")
public class DefinicionController extends MaestroRestController<Definicion> {

    /* Un comentario sobre la implementacion de esta clase podria ir aqui. */
    private ServicioDefinicion servicioDefinicion;

    private Usuarios usuarioSession;

    protected static org.slf4j.Logger log2 = LoggerFactory.getLogger(DefinicionController.class);

    @Autowired
    public void setServicioAdminDefinicion(ServicioDefinicion servicioDefinicion) {
        this.servicioDefinicion = servicioDefinicion;
        super.setServicioMaestro(servicioDefinicion);
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
    public DefinicionController() {
        log2.debug("Se construye {}", this.getClass().getSimpleName());
        campoFiltro = "definicion";
    }
    
    /**
     * Guarda una definicion en BD
     * @param request
     * @param response
     * @param model
     * @return 
     */
    @RequestMapping(method = {RequestMethod.POST}, value = "/guardar")
    public ResponseEntity<RespuestaRest<Empresa>> guardar(
            HttpServletRequest request, HttpServletResponse response,
            @RequestBody DefinicionModel model) {

        RespuestaRest<Definicion> retorno = new RespuestaRest<Definicion>();

        try {
            if (servicioSesion.validarSesion(request, retorno, usuarioSession) == null) {
                return new ResponseEntity(retorno, HttpStatus.FORBIDDEN);
            }
            log2.info("Petición de guardado de definicion en {}", extraerClaseDominio().getSimpleName());

            RespuestaRPC<Definicion> respuesta = servicioDefinicion.guardarDefinicion(model);
            if (respuesta.getResultado() == RespuestaRPC.RESULTADO_OK) {
                retorno.setSuccess(true);
                retorno.setMessage("Se ha guardado con éxito la definición");//PropiedadesLenguage.getValueES("mensajeOKGuardado"));
                retorno.setData(respuesta.getObjetoRespuesta());
                return new ResponseEntity(retorno, HttpStatus.OK);
            } else {
                retorno.setSuccess(false);
                retorno.setMessage(respuesta.getDescripcionError());
                return new ResponseEntity(retorno, HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            log2.error("Error", e);
            retorno.setSuccess(false);
            retorno.setMessage("Error en tiempo de ejecución en el servidor");
            return new ResponseEntity(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log2.error("Error", e);
            retorno.setSuccess(false);
            retorno.setMessage("Error inesperado en el servidor");
            return new ResponseEntity(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
