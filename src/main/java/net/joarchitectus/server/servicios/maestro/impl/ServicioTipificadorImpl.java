/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.servicios.maestro.impl;

import java.util.List;
import javax.servlet.ServletRequest;
import net.joarchitectus.client.datos.dominio.EntidadPerpetua;
import net.joarchitectus.client.datos.dominio.Tipificador;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.client.util.Pair;
import net.joarchitectus.client.util.consulta.Comparacion;
import net.joarchitectus.client.util.consulta.Consulta;
import net.joarchitectus.client.util.consulta.Orden;
import net.joarchitectus.server.datos.dao.DaoTipificador;
import net.joarchitectus.server.servicios.maestro.ServicioTipificador;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author josorio
 */
@Service(value = "servicioTipificador")
public class ServicioTipificadorImpl extends ServicioMaestroImpl<Tipificador> implements ServicioTipificador {

    /* Un comentario sobre la implementacion de esta clase podria ir aqui. */
    private Usuarios usuarioSession;// NOPMD - variable usada en la implementacion
    private DaoTipificador daoTipificador;// NOPMD - dao
    protected static org.slf4j.Logger log2 = LoggerFactory.getLogger(ServicioTipificadorImpl.class);

    @Autowired
    @Override
    public void setUsuarioSession(Usuarios usuarioSession) {
        this.usuarioSession = usuarioSession;
        super.setUsuarioSession(usuarioSession);
    }

    @Autowired
    public void setDaoTipificador(DaoTipificador daoTipificador) {
        this.daoTipificador = daoTipificador;
        super.setDaoGenerico(daoTipificador);
    }

    /** {@inheritDoc}*/
    @Override
    public Pair<Long, List<Tipificador>> procesarConsulta(ServletRequest request) throws Exception {
        Consulta consulta = new Consulta();

        //Determino los paginadores.
        int start = 0;
        int limit = 100000;

        if (request.getParameter("start") != null) {
            start = Integer.parseInt(request.getParameter("start"));
        }
        if (request.getParameter("limit") != null) {
            limit = Integer.parseInt(request.getParameter("limit"));
        }

        //filtro particular
        if (request.getParameter("query") != null && !request.getParameter("query").isEmpty()) {
            consulta.getRestricciones().put(request.getAttribute("campoFiltro") == null ? "nombre" : request.getAttribute("campoFiltro").toString(),
                    new Pair(Comparacion.LIKE, "%" + request.getParameter("query") + "%"));
        }

        if (extraerClaseDominio().newInstance() instanceof EntidadPerpetua) {
            if (request.getParameter("inactivos") != null) {
                consulta.getRestricciones().put("fechaInactivado", new Pair(Comparacion.NE, null));
            } else {
                consulta.getRestricciones().put("fechaInactivado", new Pair(Comparacion.EQ, null));
            }
        }

        //filtros generales
        String filtros = request.getParameter("filtros");//si llegan filtros, estos en formato json en un array

        //Determino los filtros a usar enviados en el config.
        // Tienen prelación los filtros
        if (filtros != null) {
            JSONArray filters = new JSONArray(filtros);
            for (int i = 0; i < filters.length(); i++) {
                JSONObject f = filters.getJSONObject(i);

                Object value = f.get("value"); //f.getValue();                      //Maria
                String field = f.getString("field");                                //nombre 
                String comparison = f.getString("comparison");//getComparison();    //lt ó before
                String type = f.getString("type");                                  //string ó integer ó ....

                if (type != null) {
                    if (type.equals("long")) {
                        value = f.getLong("value");
                    } else if (type.equals("double")) {
                        value = f.getDouble("value");
                    } else if (type.equals("boolean")) {
                        value = f.getBoolean("value");
                    } else if (type.equals("integer")) {
                        value = f.getInt("value");
                    } else if (type.equals("enum") && field.equals("tipo")) {
                        value = Tipificador.Tipo.valueOf(f.getString("value"));
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

        if (request.getParameter("SortField") != null) {
            //necesito que cree los lefjoins de manera que pueda consultar datos de la tabla relacionada.
            if (request.getParameter("SortField").contains(".") && !request.getParameter("SortField").contains(".id")) {// NOPMD - 
                consulta.getAlias().add(new Pair<String, String>(
                        request.getParameter("SortField").substring(0, request.getParameter("SortField").indexOf(".")),
                        request.getParameter("SortField").substring(0, request.getParameter("SortField").indexOf("."))));
            }
            if (request.getParameter("SortDir") == null || request.getParameter("SortDir").equals(Orden.ASC)) {
                consulta.getOrden().add(new Pair(request.getParameter("SortField"), Orden.ASC));
            } else {
                consulta.getOrden().add(new Pair(request.getParameter("SortField"), Orden.DESC));
            }
        } else if (request.getAttribute("SortField") != null) {
            //necesito que cree los lefjoins de manera que pueda consultar datos de la tabla relacionada.
            if (request.getAttribute("SortField").toString().contains(".") && !request.getAttribute("SortField").toString().contains(".id")) {// NOPMD - 
                consulta.getAlias().add(new Pair<String, String>(
                        request.getAttribute("SortField").toString().substring(0, request.getAttribute("SortField").toString().indexOf(".")),
                        request.getAttribute("SortField").toString().substring(0, request.getAttribute("SortField").toString().indexOf("."))));
            }
            if (request.getAttribute("SortDir") == null || request.getAttribute("SortDir").equals(Orden.ASC)) {
                consulta.getOrden().add(new Pair(request.getAttribute("SortField"), Orden.ASC));
            } else {
                consulta.getOrden().add(new Pair(request.getAttribute("SortField"), Orden.DESC));
            }
        }

        Pair<Long, List<Tipificador>> result = daoTipificador.listarPorConsulta(consulta, start, limit);

        return result;

    }
}
