/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * ServicioMaestroImpl Versión 1.0 13-sep-2017
 *
 * Copyright(c) 2007-2017, Boos IT.
 * admin@boos.com.co
 *
 * http://boos.com.co/license
 */

package net.joarchitectus.server.servicios.maestro.impl;


import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletRequest;
import net.joarchitectus.client.datos.dominio.EntidadBase;
import net.joarchitectus.client.datos.dominio.EntidadPerpetua;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.client.entidades.RespuestaRPC;
import net.joarchitectus.client.util.Pair;
import net.joarchitectus.client.util.consulta.Comparacion;
import net.joarchitectus.client.util.consulta.Consulta;
import net.joarchitectus.client.util.consulta.Orden;
import net.joarchitectus.server.datos.dao.DaoGenerico;
import net.joarchitectus.server.servicios.maestro.ServicioMaestro;

import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Servicio con los metodos genericos usados en cualquier CRUD estandar
 * @version 	1.0 13-sep-2017
 * @author josorio
 */
public class ServicioMaestroImpl<T extends EntidadBase> implements ServicioMaestro<T>{

    /* Un comentario sobre la implementacion de esta clase podria ir aqui. */
    
    private DaoGenerico<T> daoGenerico;

    private Usuarios usuarioSession;
    
    protected Class<T> domainClass = extraerClaseDominio();

    private static org.slf4j.Logger log = LoggerFactory.getLogger(ServicioMaestroImpl.class);

    /**
     * Metodo a usar a la hora de inyectar el dao de la entidad a trabajar.
     *
     * @param daoGenerico
     */
    public void setDaoGenerico(DaoGenerico<T> daoGenerico) {
        this.daoGenerico = daoGenerico;
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
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")// NOPMD - 
    protected Class<T> extraerClaseDominio() {
        if (domainClass == null) {
            ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
            domainClass = (Class) thisType.getActualTypeArguments()[0];
        }
        return domainClass;
    }
    
    /**
     * Procesa la consulta armando la estructura de esta segun los parametros
     * recibidos y retornando la lista.
     * {@inheritDoc}
     * @param config
     * @return
     */
    @Override
    public Pair<Long, List<T>> procesarConsulta(ServletRequest request) throws Exception{
        Consulta consulta = new Consulta();

        //Determino los paginadores.
        int start = 0;
        int limit = 100000;
        
        if(request.getParameter("start")!=null){
            start = Integer.parseInt(request.getParameter("start"));
        }
        if(request.getParameter("limit")!=null){
            limit = Integer.parseInt(request.getParameter("limit"));
        }
        
        //filtro particular
        if(request.getParameter("query")!=null && !request.getParameter("query").isEmpty()){
            consulta.getRestricciones().put(request.getAttribute("campoFiltro")==null ? "nombre" : request.getAttribute("campoFiltro").toString(), 
                    new Pair(Comparacion.LIKE, "%" + request.getParameter("query") + "%"));
        }
        
        if (extraerClaseDominio().newInstance() instanceof EntidadPerpetua) {
            if(request.getParameter("inactivos")!=null){
                consulta.getRestricciones().put("fechaInactivado", new Pair(Comparacion.NE, null));
            }else{
                consulta.getRestricciones().put("fechaInactivado", new Pair(Comparacion.EQ, null));
            }
        }
        
        //filtros generales
        String filtros = request.getParameter("filtros");//si llegan filtros, estos en formato json en un array

        //Determino los filtros a usar enviados en el config.
        // Tienen prelación los filtros
        if (filtros != null) {
            JSONArray filters = new JSONArray(filtros);
            for (int i=0; i < filters.length(); i++) {
                JSONObject f = filters.getJSONObject(i);
                
                Object value = f.get("value"); //f.getValue();                      //Maria
                String field = f.getString("field");                                //nombre 
                String comparison = f.getString("comparison");//getComparison();    //lt ó before
                String type = f.getString("type");                                  //string ó integer ó ....
                
                if(type!=null){
                    if(type.equals("long")){
                        value = f.getLong("value");
                    }else if(type.equals("double")){
                        value = f.getDouble("value");
                    }else if(type.equals("boolean")){
                        value = f.getBoolean("value");
                    }else if(type.equals("integer")){
                        value = f.getInt("value");
                    }
                }

                if (value != null) {

                    //necesito que cree los lefjoins de manera que pueda consultar datos de la tabla relacionada.
                    if (field.contains(".") && !field.contains(".id")) {// NOPMD - 
                        consulta.getAlias().add(new Pair<String, String>(
                                field.substring(0, field.indexOf(".")),
                                field.substring(0, field.indexOf("."))));
                    }

                    if (comparison == null) {
                        //consulta.getRestricciones().put(field, new Pair(Comparacion.EQ, value));
                        if (type.equals("string")) {
                            consulta.getRestricciones().put(field, new Pair(Comparacion.LIKE, "%" + value + "%"));
                        } else {
                            consulta.getRestricciones().put(field, new Pair(Comparacion.EQ, value));
                        }
                    } else if (comparison.equalsIgnoreCase("lt") || comparison.equalsIgnoreCase("before")) {
                        consulta.getRestricciones().put(field, new Pair(Comparacion.LT, value));
                    } else if (comparison.equalsIgnoreCase("gt") || comparison.equalsIgnoreCase("after")) {
                        consulta.getRestricciones().put(field, new Pair(Comparacion.GT, value));
                    } else if (comparison.equalsIgnoreCase("eq")) {
                        if (type.equals("string")) {
                            consulta.getRestricciones().put(field, new Pair(Comparacion.LIKE, "%" + value + "%"));
                        } else {
                            consulta.getRestricciones().put(field, new Pair(Comparacion.EQ, value));
                        }
                    } else if (comparison.equalsIgnoreCase("on")) {
                        consulta.getRestricciones().put(field, new Pair(Comparacion.EQ, value));
                    } else {
                        throw new UnsupportedOperationException("El tipo de comparación no está soportado");
                    }
                }
            }
        }
        
        if (request.getParameter("SortField")!=null) {
            //necesito que cree los lefjoins de manera que pueda consultar datos de la tabla relacionada.
            if (request.getParameter("SortField").contains(".") && !request.getParameter("SortField").contains(".id")) {// NOPMD - 
                consulta.getAlias().add(new Pair<String, String>(
                        request.getParameter("SortField").substring(0, request.getParameter("SortField").indexOf(".")),
                        request.getParameter("SortField").substring(0, request.getParameter("SortField").indexOf("."))));
            }
            if (request.getParameter("SortDir") == null || request.getParameter("SortDir").equals(Orden.ASC)) {
                consulta.getOrden().add(new Pair(request.getParameter("SortField"), Orden.ASC));
            } else{
                consulta.getOrden().add(new Pair(request.getParameter("SortField"), Orden.DESC));
            }
        }

//        try {
            //Cargo la lsita de usuarios de la BD
            Pair<Long, List<T>> result = daoGenerico.listarPorConsulta(consulta, start, limit);

            //this.antesRetorno(result.getB());
            return result;
//        } catch (Exception e) {
//            log.error("Error", e);
//            System.err.println("ERROR: error en la consulta. " + e.getMessage());
//            Pair<Long, List<T>> result = new Pair<Long, List<T>>(0l, new ArrayList<T>());
//            return result;
//        }

    }
    
    /**
     * Guarda la entidad entregada, si no existía lo crea
     *
     * @param entidad
     * @return Alguno de los siguientes valores:
     */
    @Override
    public RespuestaRPC<T> guardarEntidad(T entidad) {
        RespuestaRPC<T> retorno = new RespuestaRPC<T>();

//        // Validamos session
//        if (usuarioSession == null || usuarioSession.getId() == null || usuarioSession.getId().intValue() == 0) {
//            retorno.setResultado(RespuestaRPC.RESULTADO_FALLO_NO_SESSION);
//            return retorno;
//        }
        try {

            daoGenerico.guardar(entidad);
            retorno.setObjetoRespuesta(entidad);
            retorno.setResultado(RespuestaRPC.RESULTADO_OK);

        } catch (ConstraintViolationException e) {
            log.error("Error", e);
            retorno.setResultado(RespuestaRPC.RESULTADO_EXCEPCION_SERVER);
            retorno.setDescripcionError("Error de integridad de datos, por favor comunicarse con el área de soporte");
        } catch (DataIntegrityViolationException e) {
            log.error("Error", e);
            retorno.setResultado(RespuestaRPC.RESULTADO_EXCEPCION_SERVER);
            retorno.setDescripcionError("Error de integridad de datos, por favor comunicarse con el área de soporte");
        } catch (Exception e) {
            log.error("Error", e);
            e.printStackTrace();
            retorno.setResultado(RespuestaRPC.RESULTADO_EXCEPCION_SERVER);
        }
        return retorno;
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public RespuestaRPC<T> eliminarEntidad(Long idEntidad) {
        RespuestaRPC<T> retorno = new RespuestaRPC<T>();
        // Validamos session
//        if (usuarioSession == null || usuarioSession.getId() == null || usuarioSession.getId().intValue() == 0) {
//
//            retorno.setResultado(RespuestaRPC.RESULTADO_FALLO_NO_SESSION);
//            return retorno;
//        }
        try {
            T borrar = daoGenerico.getById(idEntidad);

            //Ejecuta borrado dependiendo del tipo de entidad es eliminado logico o fisico.
            daoGenerico.borrar(borrar);
            retorno.setResultado(RespuestaRPC.RESULTADO_OK);
        } catch (Exception e) {
            log.error("Error", e);
            retorno.setResultado(RespuestaRPC.RESULTADO_EXCEPCION_SERVER);
        }

        return retorno;
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public RespuestaRPC<T> eliminarEntidad(T entidad) {
        RespuestaRPC<T> retorno = new RespuestaRPC<T>();
        // Validamos session
//        if (usuarioSession == null || usuarioSession.getId() == null || usuarioSession.getId().intValue() == 0) {
//
//            retorno.setResultado(RespuestaRPC.RESULTADO_FALLO_NO_SESSION);
//            return retorno;
//        }
        try {

            //Ejecuta borrado dependiendo del tipo de entidad es eliminado logico o fisico.
            daoGenerico.borrar(entidad);
            retorno.setResultado(RespuestaRPC.RESULTADO_OK);
        } catch (Exception e) {
            log.error("Error", e);
            retorno.setResultado(RespuestaRPC.RESULTADO_EXCEPCION_SERVER);
        }

        return retorno;
    }

    /**
     * {@inheritdoc}
     */
    public RespuestaRPC<T> reactivarEntidad(Long idEntidad) {
        RespuestaRPC<T> retorno = new RespuestaRPC<T>();
        // Validamos session
//        if (usuarioSession == null || usuarioSession.getId() == null || usuarioSession.getId().intValue() == 0) {
//
//            retorno.setResultado(RespuestaRPC.RESULTADO_FALLO_NO_SESSION);
//            return retorno;
//        }
        try {
            T reactivar = daoGenerico.getById(idEntidad);

            daoGenerico.reactivar(reactivar);
            retorno.setResultado(RespuestaRPC.RESULTADO_OK);
        } catch (Exception e) {
            log.error("Error", e);
            retorno.setResultado(RespuestaRPC.RESULTADO_EXCEPCION_SERVER);
        }

        return retorno;
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public RespuestaRPC<T> cargarEntidad(Long idEntidad) {
        RespuestaRPC<T> retorno = new RespuestaRPC<T>();
//        // Validamos session
//        if (usuarioSession == null || usuarioSession.getId() == null || usuarioSession.getId().intValue() == 0) {
//
//            retorno.setResultado(RespuestaRPC.RESULTADO_FALLO_NO_SESSION);
//            return retorno;
//        }
        try {
            T entidad = daoGenerico.getById(idEntidad);
            retorno.setObjetoRespuesta(entidad);
            retorno.setResultado(RespuestaRPC.RESULTADO_OK);
        } catch (Exception e) {
            log.error("Error", e);
            retorno.setResultado(RespuestaRPC.RESULTADO_EXCEPCION_SERVER);
        }

        return retorno;
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public List<T> getEntidades(int tipo) {
        if (tipo == EntidadPerpetua.TIPO_UNDELETED) {
            return daoGenerico.listar();
        } else if (tipo == EntidadPerpetua.TIPO_DELETED) {
            return daoGenerico.listarInactivos();
        } else {
            return new ArrayList<T>();
        }
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public List<T> getEntidades(int start, int limit) throws Exception{
        return daoGenerico.listarP(start, limit);
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public List<T> getEntidades(ServletRequest request, String campofiltro, int tipo) {

        //Get query string
        String query = request.getParameter("query");
        //System.out.println("aa "+query);

        String consultax = null;
        if (query != null && !query.isEmpty()) {
            consultax = query;
        }

        if (tipo == EntidadPerpetua.TIPO_UNDELETED) {
            Consulta consulta = new Consulta();
            //indico que se ordena por el campo
            consulta.getOrden().add(new Pair(campofiltro, Orden.ASC));
            //activo filtrado de resultado.
            if (consultax != null) {
                consulta.getRestricciones().put(campofiltro, new Pair(Comparacion.LIKE, "%" + consultax + "%"));
            }
            try {
                //Cargo la lsita de datos de la BD
                Pair<Long, List<T>> result = daoGenerico.listarPorConsulta(consulta, 0, 1000);
                return result.getB();
            } catch (Exception e) {
                log.error("Error", e);
                return new ArrayList<T>();
            }

        } else if (tipo == EntidadPerpetua.TIPO_DELETED) {
            return daoGenerico.listarInactivos();
        } else {
            return new ArrayList<T>();
        }
    }
}
