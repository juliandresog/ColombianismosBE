/*
 * ColombianismosController Versión 1.0 12/07/2019
 *
 **/
package net.joarchitectus.server.vista.rest.api;

import java.util.List;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * La descripcion de la clase va aqui.
 *
 * @version 1.0 12/07/2019
 * @author josorio
 */
@RestController
@RequestMapping("/colombianismos")
public class ColombianismosController {

    private static org.slf4j.Logger log = LoggerFactory.getLogger(ColombianismosController.class);
    @Autowired
    private ServicioLema servicioLema;

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
            log.debug("Petición de consulta buscarLemas {}", ColombianismosController.class.getSimpleName());
            
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

}
