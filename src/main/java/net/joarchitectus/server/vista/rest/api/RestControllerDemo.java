/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.vista.rest.api;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import net.joarchitectus.client.datos.model.EmpresaModel;
import net.joarchitectus.client.entidades.RespuestaRPC;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author josorio
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/restfull")
public class RestControllerDemo {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private static org.slf4j.Logger log = LoggerFactory.getLogger(RestControllerDemo.class);

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {

        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @GetMapping("/checkrest")
    public ResponseEntity<EmpresaModel> checkRest() {
        if (true) {
            //logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name JJJJJJ"), HttpStatus.CONFLICT);
        }

        EmpresaModel cm = new EmpresaModel();
        cm.setId(-1l);
        cm.setNombre("Mi restfull");
        return new ResponseEntity<EmpresaModel>(cm, HttpStatus.OK);
    }

    @RequestMapping("/map")
    public Map map(@RequestParam(value = "name", defaultValue = "World") String name) {

        Map mapa = new HashMap<String, Object>();
        mapa.put("succes", false);

        return mapa;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<String>> listAllUsers() {
        List<String> users = new ArrayList<String>();
        users.add("primero");
        users.add("segundo");
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<String>>(users, HttpStatus.OK);
    }

    /**
     * Demo de empresas paginado
     *
     * @param request
     * @param response
     * @param id
     * @param query
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET}, value = "/getList/{id}")
    public ResponseEntity<RespuestaRest<List<EmpresaModel>>> get(
            ServletRequest request, ServletResponse response,
            @PathVariable("id") Long id,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
            @RequestParam(value = "limit", required = false, defaultValue = "0") Integer limit
    ) {

        RespuestaRest<List<EmpresaModel>> retorno = new RespuestaRest<List<EmpresaModel>>();

        try {
//            if (servicioSesion.validarSesion(request, retorno, usuarioSession) == null) {
//                return new ResponseEntity(retorno, HttpStatus.FORBIDDEN);
//            }
            log.info("Petición de consulta de {}", EmpresaModel.class.getSimpleName());

            List<EmpresaModel> lista;
            if (request.getParameter("query") == null || request.getParameter("query").isEmpty()) {
                Random rand = new Random();
                lista = new ArrayList<>();
                for (long i = 20; i < 30; i++) {
                    EmpresaModel cm = new EmpresaModel();
                    cm.setId(rand.nextLong());
                    cm.setNombre("Mi empresa "+i);
                    
                    lista.add(cm);
                }
            } else {
                Random rand = new Random();
                lista = new ArrayList<>();
                for (long i = 0; i < 10; i++) {
                    EmpresaModel cm = new EmpresaModel();
                    cm.setId(rand.nextLong());
                    cm.setNombre("Mi empresa "+i);
                    
                    lista.add(cm);
                }
            }
            
            //ordeno lista con java 8
            lista.sort(Comparator.comparing(EmpresaModel::getId));

            retorno.setSuccess(true);
            retorno.setData(lista);
            retorno.setTotal(lista.size());
            retorno.setMessage("OK");
            return new ResponseEntity<RespuestaRest<List<EmpresaModel>>>(retorno, HttpStatus.OK);
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
