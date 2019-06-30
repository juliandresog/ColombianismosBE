/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.vista.rest;

import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.server.datos.dao.DaoUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author jdbotero
 */
@Controller
public class UsuariosController {

    @Autowired
    private DaoUsuario daoUsuario;
    @Autowired
    private Usuarios usuarioSession;// NOPMD - Esta variable contiene info del usuario en sesion.

    /**
     * Método que recibe las solicitudes por listas de usuarios
     *
     * @return
     */
    @RequestMapping(value = "/usuarios/{idEmpresa}")
    public ModelAndView usuariosEmpresa(@PathVariable int idEmpresa,
            ServletRequest request, ServletResponse response) {
        ModelAndView retorno = new ModelAndView();

        String nombreconsulta = null;

        if (request.getParameter("query") != null) {// NOPMD - 
            nombreconsulta = (request.getParameter("query"));
        }

        // Si especificaron empresa
        List<Usuarios> lista = null;
//        if (idEmpresa > 0) {
//        }

        lista = daoUsuario.getUsuariosActivos(nombreconsulta);


        if (lista == null) {
            lista = new ArrayList<Usuarios>();
        }

        retorno.addObject("cantidad", lista.size());// NOPMD - 
        retorno.addObject("datos", lista);// NOPMD - 

        return retorno;
    }

    /**
     * Método que recibe las solicitudes por listas de usuarios
     *
     * @return
     */
    @RequestMapping(value = "/usuariosdesactivados/{idEmpresa}")
    public ModelAndView usuariosDesactivados(@PathVariable int idEmpresa) {
        ModelAndView retorno = new ModelAndView();

        // Si especificaron empresa
        List<Usuarios> lista = null;
//        if (idEmpresa > 0) {
//            /////////////777
//        }
        lista = daoUsuario.getUsuariosInactivos();


        if (lista == null) {
            lista = new ArrayList<Usuarios>();
        }

        retorno.addObject("cantidad", lista.size());
        retorno.addObject("datos", lista);

        return retorno;
    }

    /**
     * Método que recibe las solicitudes por listas de usuarios
     *
     * @return
     */
    @RequestMapping(value = "/conductores/{idEmpresa}")
    public ModelAndView conductoresEmpresa(@PathVariable int idEmpresa,
            ServletRequest request, ServletResponse response) {
        ModelAndView retorno = new ModelAndView();
        retorno.setViewName("vistaJson");

        String nombreconsulta = null;// NOPMD - Esta variable se usara en filtros

        if (request.getParameter("query") != null) {
            nombreconsulta = (request.getParameter("query"));
        }

        // Si especificaron empresa
        List<Usuarios> lista = null;
//        if (idEmpresa > 0) {
//            ////////////
//        }

        if (lista == null) {
            lista = new ArrayList<Usuarios>();
        }

        retorno.addObject("cantidad", lista.size());
        retorno.addObject("datos", lista);

        return retorno;
    }

    /**
     * Método que recibe las solicitudes por listas de usuarios
     *
     * @return
     */
    @RequestMapping(value = "/usuariospaginado")
    public ModelAndView usuariosPaginado(
            ServletRequest request, ServletResponse response) {
        ModelAndView retorno = new ModelAndView();

        String nombreconsulta = null;// NOPMD - Esta variable se usara en filtros

        if (request.getParameter("query") != null) {
            nombreconsulta = (request.getParameter("query"));
        }
        int inicio = 0;
        int limit = 50;
        if (request.getParameter("start") != null) {
            inicio = Integer.parseInt(request.getParameter("start"));
        }
        if (request.getParameter("limit") != null) {
            limit = Integer.parseInt(request.getParameter("limit"));
        }

        //System.out.println("start: "+inicio + " limit: "+limit);

        // Si especificaron empresa
        List<Usuarios> lista = null;
        //Listo los usuarios en los limites descritos.
        lista = daoUsuario.listaUsuarios(inicio, limit);

        if (lista == null) {
            lista = new ArrayList<Usuarios>();
        }

        retorno.addObject("cantidad", daoUsuario.cantidadListaUsuarios());
        retorno.addObject("datos", lista);

        return retorno;
    }
}
