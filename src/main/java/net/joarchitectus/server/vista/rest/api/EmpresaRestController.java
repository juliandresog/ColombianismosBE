/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.vista.rest.api;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.joarchitectus.client.datos.dominio.Empresa;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.server.servicios.maestro.ServicioAdminEmpresa;
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
@RequestMapping("/admin/empresa")
public class EmpresaRestController extends MaestroRestController<Empresa> {

    /* Un comentario sobre la implementacion de esta clase podria ir aqui. */
    private ServicioAdminEmpresa servicioAdminEmpresa;

    private Usuarios usuarioSession;

    protected static org.slf4j.Logger log2 = LoggerFactory.getLogger(EmpresaRestController.class);

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
    public EmpresaRestController() {
        log2.debug("Se contruye maestro " + this.getClass().getSimpleName());
    }

//    /**
//     * Metodo para guardar un contacto en el sistema usando modelo. Esto más a
//     * modo de ejemplo ya que con el /save es suficiente sino se manda la ciudad
//     * @modelAttribute to capture form parameter
//     * @RequestBody to capture entire request 
//     * @param request
//     * @param response
//     * @param entidad
//     * @return
//     */
//    @RequestMapping(method = {RequestMethod.POST}, value = "/guardar")
//    public ResponseEntity<RespuestaRest<Empresa>> guardar(
//            HttpServletRequest request, HttpServletResponse response,
//            @RequestBody EmpresaModel model) {
//
//        RespuestaRest<Empresa> retorno = new RespuestaRest<Empresa>();
//
//        try {
//            if (servicioSesion.validarSesion(request, retorno, usuarioSession) == null) {
//                return new ResponseEntity(retorno, HttpStatus.FORBIDDEN);
//            }
//            log2.info("Petición de guardado de contacto en {}", extraerClaseDominio().getSimpleName());
//
//            Empresa entidad;
//            if (model.getCodigo() != null) {
//                entidad = servicioAdminEmpresa.cargarEntidad(model.getCodigo()).getObjetoRespuesta();
//            } else {
//                entidad = new Empresa();
//            }
//
//            //creo entidad a partir del modelo buscando no reemplazar datos con nulos
//            entidad = ModeloConverter.convertirModeloToEntidadEmpresa(entidad, model, false);
//
//            RespuestaRPC<Empresa> respuesta = servicioAdminEmpresa.guardarEntidad(entidad);
//            if (respuesta.getResultado() == RespuestaRPC.RESULTADO_OK) {
//                retorno.setSuccess(true);
//                retorno.setMessage(PropiedadesLenguage.getValueES("mensajeOKGuardado"));
//                return new ResponseEntity(retorno, HttpStatus.OK);
//            } else {
//                retorno.setSuccess(false);
//                retorno.setMessage(respuesta.getDescripcionError());
//                return new ResponseEntity(retorno, HttpStatus.NOT_FOUND);
//            }
//        } catch (RuntimeException e) {
//            log2.error("Error", e);
//            retorno.setSuccess(false);
//            retorno.setMessage("Error en tiempo de ejecución en el servidor");
//            return new ResponseEntity(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (Exception e) {
//            log2.error("Error", e);
//            retorno.setSuccess(false);
//            retorno.setMessage("Error inesperado en el servidor");
//            return new ResponseEntity(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
