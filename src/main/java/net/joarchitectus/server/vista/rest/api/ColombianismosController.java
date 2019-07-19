/*
 * ColombianismosController Versión 1.0 12/07/2019
 *
 **/
package net.joarchitectus.server.vista.rest.api;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import net.joarchitectus.client.datos.dominio.Lema;
import net.joarchitectus.client.datos.model.EmpresaModel;
import net.joarchitectus.client.util.Pair;
import net.joarchitectus.server.servicios.maestro.ServicioLema;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * La descripcion de la clase va aqui.
 *
 * @version 1.0 12/07/2019
 * @author josorio
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/colombianismos")
public class ColombianismosController {

    private static org.slf4j.Logger log = LoggerFactory.getLogger(ColombianismosController.class);
    @Autowired
    private ServicioLema servicioLema;
    
//    @ControllerAdvice esto esta deprecado y es mejor usar CORS
//    static class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
//        public JsonpAdvice() {
//            super("callback");
//        }
//    }

    /**
     * Lista los lemas existentes en BD
     *
     * @param request
     * @param response
     * @param query
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET}, value = "/buscarLemas")
    public ResponseEntity<RespuestaRest<List<Lema>>> buscarLemas(
            ServletRequest request, ServletResponse response,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
            @RequestParam(value = "limit", required = false, defaultValue = "50") Integer limit
    ) {
        RespuestaRest<List<Lema>> retorno = new RespuestaRest<List<Lema>>();

        try {
            log.debug("Petición de consulta buscarLemas  {}", ColombianismosController.class.getSimpleName());
            
            request.setAttribute("campoFiltro", "texto");
            request.setAttribute("SortField", "texto");
            
            Pair<Long, List<Lema>> lista = servicioLema.procesarConsulta(request);
            
            retorno.setSuccess(true);
            retorno.setData(lista.getB());
            retorno.setTotal(lista.getA());
            retorno.setMessage("OK");
            
            return new ResponseEntity<RespuestaRest<List<Lema>>>(retorno, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Error", e);
            retorno.setSuccess(false);
            retorno.setMessage("Error en tiempo de ejecución en el servidor");
            return new ResponseEntity(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error", e);
            retorno.setSuccess(false);
            retorno.setMessage("Error inesperado en el servidor");
            return new ResponseEntity(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Lista los sinonimos de un lema especifico
     * @param request
     * @param response
     * @param idLema
     * @return 
     */
    @RequestMapping(method = {RequestMethod.GET}, value = "/buscarSinonimos/{idLema}")
    public ResponseEntity<RespuestaRest<Map>> buscarSinonimos(
            ServletRequest request, ServletResponse response,
            @PathVariable Long idLema
    ) {
        RespuestaRest<Map> retorno = new RespuestaRest<Map>();

        try {
            log.debug("Petición de consulta buscarSinonimos  {}", ColombianismosController.class.getSimpleName());            
            
            Map sinonimos = servicioLema.buscarSinonimos(idLema);
            
            retorno.setSuccess(true);
            retorno.setData(sinonimos);
            retorno.setTotal(1);
            retorno.setMessage("OK");
            
            return new ResponseEntity<RespuestaRest<Map>>(retorno, HttpStatus.OK);
        } catch (NoSuchElementException e){
            log.warn("Alerta", e);
            retorno.setSuccess(false);
            retorno.setMessage(e.getMessage());
            return new ResponseEntity(retorno, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            log.error("Error", e);
            retorno.setSuccess(false);
            retorno.setMessage("Error en tiempo de ejecución en el servidor");
            return new ResponseEntity(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error", e);
            retorno.setSuccess(false);
            retorno.setMessage("Error inesperado en el servidor");
            return new ResponseEntity(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtener definiciones de una palabra o lema
     * @param request
     * @param response
     * @param idLema
     * @return 
     */
    @RequestMapping(method = {RequestMethod.GET}, value = "/obtenerLema/{idLema}")
    public ResponseEntity<RespuestaRest<Map>> obtenerLema(
            ServletRequest request, ServletResponse response,
            @PathVariable Long idLema
    ) {
        RespuestaRest<Map> retorno = new RespuestaRest<Map>();

        try {
            log.debug("Petición de consulta obtenerLema  {}", ColombianismosController.class.getSimpleName());            
            
            Map definiciones = servicioLema.obtenerLema(idLema);
            
            retorno.setSuccess(true);
            retorno.setData(definiciones);
            retorno.setTotal(1);
            retorno.setMessage("OK");
            
            return new ResponseEntity<RespuestaRest<Map>>(retorno, HttpStatus.OK);
        } catch (NoSuchElementException e){
            log.warn("Alerta", e);
            retorno.setSuccess(false);
            retorno.setMessage(e.getMessage());
            return new ResponseEntity(retorno, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            log.error("Error", e);
            retorno.setSuccess(false);
            retorno.setMessage("Error en tiempo de ejecución en el servidor");
            return new ResponseEntity(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error", e);
            retorno.setSuccess(false);
            retorno.setMessage("Error inesperado en el servidor");
            return new ResponseEntity(retorno, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
