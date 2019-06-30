/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * ServicioMaestro Versión 1.0 13-sep-2017
 *
 * Copyright(c) 2007-2016, Boos IT.
 * admin@boos.com.co
 *
 * http://boos.com.co/license
 **/

package net.joarchitectus.server.servicios.maestro;

import java.util.List;
import javax.servlet.ServletRequest;
import net.joarchitectus.client.datos.dominio.EntidadBase;
import net.joarchitectus.client.entidades.RespuestaRPC;
import net.joarchitectus.client.util.Pair;


/**
 * La descripcion de la interfaz va aqui.
 * @version 	1.0 13-sep-2017
 * @author josorio
 * @param <T>
 */
public interface ServicioMaestro<T extends EntidadBase> {

    /**
     * Procesa la consulta armando la estructura de esta segun los parametros recibidos y retornando la lista.
     * Este controller es capaz de paginar, tambien de ordenar y filtrar los resultados segun los parametros recividos en el request.<br/>
     * Parametros aceptados en el http request:<br/>
     * -start: inicio de pagina<br/>
     * -limit: fin de pagina<br/>
     * -query: filtro principal<br/>
     * -filtros: vector con filtros adicionales con este formato json: [{"value":"Marta", "field":"nombre", "comparison":"eq", "type":"string"}]<br/>
     * -SortField: nombre del campo por el que se desea ordenar<br/>
     * -SortDir: Asc o Desc, indica la dirección del ordenamiento.<br/>
     * Ejm: http://localhost:port/html/empresa/getPagin.json?tokenV=xxxval&SortField=nombre&SortDir=Desc&filtros=[{%22value%22:%221%22,%20%22field%22:%22id%22,%20%22comparison%22:%22gt%22,%20%22type%22:%22long%22}]
     * -inactivos: si lo pongo indico que solo deseo ver entidades inactivadas
     * @param request para ver todos los posibles parametros para filtrar la consulta
     * @return 
     * @throws java.lang.Exception 
     */
    public Pair<Long, List<T>> procesarConsulta(ServletRequest request) throws Exception;
    
    /**
     * Guarda la entidad entregada, si no existía lo crea
     *
     * @param entidad
     * @return Alguno de los siguientes valores:
     */
    public RespuestaRPC<T> guardarEntidad(T entidad);
    
    /**
     * Elimina la entidad indicada.
     * @param idEntidad
     * @return 
     */
    public RespuestaRPC<T> eliminarEntidad(Long idEntidad);
    
    /**
     * Elimina la entidad indicada.
     * @param entidad
     * @return 
     */
    public RespuestaRPC<T> eliminarEntidad(T entidad);
    
    /**
     * Reactiva una entidad previamente eliminada.
     * @param idEntidad
     * @return 
     */
    public RespuestaRPC<T> reactivarEntidad(Long idEntidad);
    
    /**
     * Lista las entidades segun el tipo
     * @param tipo EntidadPerpetua.TIPO_UNDELETED o EntidadPerpetua.TIPO_DELETED
     * @return 
     */
    public List<T> getEntidades(int tipo);
    
    /**
     * Lista todas las entidades de forma paginada
     * @param start
     * @param limit
     * @return 
     * @throws java.lang.Exception 
     */
    public List<T> getEntidades(int start, int limit)throws Exception;
    
    /**
     * Lista las entidades segun el tipo, se usa filtros
     * @param request por si tiene parametro query para filtrar
     * @param campofiltro
     * @param tipo tipo EntidadPerpetua.TIPO_UNDELETED o EntidadPerpetua.TIPO_DELETED
     * @return 
     */
    public List<T> getEntidades(ServletRequest request, String campofiltro, int tipo);
    
    /**
     * Carga una entidad por su id
     * @param idEntidad
     * @return 
     */
    public RespuestaRPC<T> cargarEntidad(Long idEntidad);
}
