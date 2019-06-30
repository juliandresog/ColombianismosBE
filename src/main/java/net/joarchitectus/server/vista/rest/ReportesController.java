/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.vista.rest;

import net.joarchitectus.client.datos.dominio.Usuarios;

import net.joarchitectus.server.util.Formatos;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author josorio
 */
@Controller
@RequestMapping("/reportes")
public class ReportesController {

    private static Logger log = Logger.getLogger(ReportesController.class);
    @Autowired
    private Usuarios usuarioSession;
    
    private static final int TIPO_XLS = 1;
    private static final int TIPO_PDF = 2;// NOPMD - Esta variable se usara despues.
    
    /**
     * 
     * @param tipo
     * @param modelMap
     * @param modelAndView
     * @param request
     * @param response
     * @return 
     */
    @RequestMapping(value = "/reporteDemo/{tipo}",
    method = RequestMethod.GET)
    public ModelAndView doSalesReportPDF(
            @PathVariable Long tipo,
            ModelMap modelMap,
            ModelAndView modelAndView,
            HttpServletRequest request,
            HttpServletResponse response) {

        if (usuarioSession == null || usuarioSession.getId() == null) {
            ModelAndView retorno = new ModelAndView("errores");
            retorno.addObject("fecha_actual", Formatos.fechaHora(new Date()));
            retorno.addObject("mensaje", "Debe tener una sesion activa para mostrar este contenido.");
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "max-age=0");

            return retorno;
        }
        try {
            log.info("Entra a generar reporte");
            //Determino url del sistema para imagenes
            String url = request.getRequestURL().toString();
//            String contextPath = request.getContextPath();
            String servletPath = request.getServletPath();
            String pathInfo = request.getPathInfo();
//            String scheme = request.getScheme();
//            String serverName = request.getServerName();

            url = url.replaceAll(pathInfo, "");
            url = url.replaceAll(servletPath, "");
            
            Collection<Usuarios> items = new ArrayList<Usuarios>();
            Usuarios user = new Usuarios();
            user.copiarDatos(usuarioSession); 
            items.add(user); 
            
//            Map<String, Object> parameterMap = new HashMap<String, Object>();            
//            
//            parameterMap.put("datasource", new JRBeanCollectionDataSource(items));
//            parameterMap.put("fecha", Formatos.fecha2(new Date()));
//            parameterMap.put("urlLogo", url + "/imagenes/logo_pequenio.png");
//
//            if (tipo == TIPO_XLS) {
//                modelAndView = new ModelAndView("xlsReporteMio", parameterMap);
//            } else {
//                modelAndView = new ModelAndView("pdfReporteMio", parameterMap);
//            }
            
            modelMap.put("datasource", new JRBeanCollectionDataSource(items));
            if (tipo == TIPO_XLS) {
                modelMap.put("format", "xls");
//                Properties properties = new Properties();
//                properties.setProperty("Content-Disposition", "attachment; filename=newReportName1.xls");
//                jrvr.setHeaders(properties);
            } else {
                modelMap.put("format", "pdf");
//                Properties properties = new Properties();
//                properties.setProperty("Content-Disposition", "attachment; filename=newReportName2.pdf");
//                jrvr.setHeaders(properties);
            }

            modelMap.put("fecha", Formatos.fecha2(new Date()));
            modelMap.put("urlLogo", url + "/imagenes/logo_pequenio.png");

            modelAndView = new ModelAndView("rpt_reporteDemo", modelMap);

            return modelAndView;
            
        } catch (Exception e) {
            ModelAndView retorno = new ModelAndView("errores");
            retorno.addObject("fecha_actual", Formatos.fechaHora(new Date()));
            retorno.addObject("mensaje", "Ha ocurrido un error inesperado, por favor comuniquese con el area de soporte técnico.");
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "max-age=0");

            log.error("Error generando reporte", e);

            return retorno;
        }
    }
    
    /**
     * http://acodigo.blogspot.nl/2017/04/spring-mvc-generar-reportes.html
     * @param format valores:
     * <ul>
     * <li>csv - JasperReportsCsvView</li>
     * <li>html - JasperReportsHtmlView</li>
     * <li>pdf - JasperReportsPdfView </li>
     * <li>xls - JasperReportsXlsView</li>
     * </ul>
     * @param nombre
     * @param modelMap
     * @param modelAndView
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/reporteDemo2/{nombre}",
            method = RequestMethod.GET)
    public ModelAndView reporteDemo2(
            @PathVariable String nombre,
            @RequestParam(name = "format", defaultValue = "pdf", required = false) String format,
            ModelMap modelMap,
            ModelAndView modelAndView,
            HttpServletRequest request,
            HttpServletResponse response
            ) {

        if (usuarioSession == null || usuarioSession.getId() == null) {
            ModelAndView retorno = new ModelAndView("errores");
            retorno.addObject("fecha_actual", Formatos.fechaHora(new Date()));
            retorno.addObject("mensaje", "Debe tener una sesion activa para mostrar este contenido.");
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "max-age=0");

            return retorno;
        }
        try {
            log.info("Entra a generar reporte");
            //Determino url del sistema para imagenes
            String url = request.getRequestURL().toString();
//            String contextPath = request.getContextPath();
            String servletPath = request.getServletPath();
            String pathInfo = request.getPathInfo();
//            String scheme = request.getScheme();
//            String serverName = request.getServerName();

            url = url.replaceAll(pathInfo, "");
            url = url.replaceAll(servletPath, "");

            Collection<Usuarios> items = new ArrayList<Usuarios>();
            Usuarios user = new Usuarios();
            user.copiarDatos(usuarioSession);
            items.add(user);
            
            //@RequestParam(name = "format", defaultValue = "pdf", required = false) String format
            //String format = request.getParameter("format")==null ? "pdf" : request.getParameter("format");

            modelMap.put("datasource", new JRBeanCollectionDataSource(items));
            modelMap.put("format", format);

            modelMap.put("fecha", Formatos.fecha2(new Date()));
            modelMap.put("urlLogo", url + "/imagenes/logo_pequenio.png");

            modelAndView = new ModelAndView("rpt_reporteDemo", modelMap);

            return modelAndView;

        } catch (Exception e) {
            ModelAndView retorno = new ModelAndView("errores");
            retorno.addObject("fecha_actual", Formatos.fechaHora(new Date()));
            retorno.addObject("mensaje", "Ha ocurrido un error inesperado, por favor comuniquese con el area de soporte técnico.");
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "max-age=0");

            log.error("Error generando reporte", e);

            return retorno;
        }
    }
}
