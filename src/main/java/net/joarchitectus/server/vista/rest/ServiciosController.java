/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.joarchitectus.server.vista.rest;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.joarchitectus.client.datos.dominio.Pais;
import net.joarchitectus.client.util.consulta.Consulta;
import net.joarchitectus.server.datos.dao.DaoPais;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author josorio
 */
@Controller
public class ServiciosController {
    
    @Autowired
    private DaoPais daoPais;
    //private static Logger log = Logger.getLogger(ServiciosController.class);
    protected static org.slf4j.Logger log = LoggerFactory.getLogger(ServiciosController.class);
    private static final String ERROR = "error";
    private static final String MESSAGE = "message";
    private static final String NO_SESSION = "nosession";
    private static final String OK = "success";
    private static final String DATA = "data";    
    
    
    /**
     * Listar las calificaciones de un parqueadero de forma paginada.
     * @param request
     * @param response
     * @return 
     */
    @RequestMapping( method = {RequestMethod.GET}, value = "/listarPaises" )
    public ModelAndView listarPaises(            
            HttpServletRequest request, HttpServletResponse response)
    {
        log.info("Consultando lista de paises");
        ModelAndView retorno = new ModelAndView();
        try{
            //objento header
            String identificacion = request.getHeader("udid");
            if(identificacion==null){
                retorno.addObject(NO_SESSION, "Cliente no identificado");
                return retorno;
            } 
           
            List<Pais> lista = daoPais.listar();
            
            retorno.addObject( OK, true);
            retorno.addObject( DATA, lista );
        }catch(Exception e){
            log.error("Error", e); 
            retorno.addObject( OK, false);
            retorno.addObject( MESSAGE, e.getMessage());
            retorno.addObject( ERROR, "Error inesperado en el servidor");
        }       

        return retorno;
    }
}
