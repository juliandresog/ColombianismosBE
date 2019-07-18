/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*
 * ServicioLemaImpl Versión 1.0 12/07/2019
 *
 **/
package net.joarchitectus.server.servicios.maestro.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import net.joarchitectus.client.datos.dominio.Lema;
import net.joarchitectus.client.datos.dominio.Tipificador;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.client.util.Pair;
import net.joarchitectus.server.datos.dao.DaoDefinicion;
import net.joarchitectus.server.datos.dao.DaoLema;
import net.joarchitectus.server.datos.dao.DaoTipificador;
import net.joarchitectus.server.servicios.maestro.ServicioLema;
import org.json.JSONArray;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * La descripcion de la clase va aqui.
 *
 * @version 1.0 12/07/2019
 * @author josorio
 */
@Service(value = "servicioLema")
public class ServicioLemaImpl extends ServicioMaestroImpl<Lema> implements ServicioLema {

    /* Un comentario sobre la implementacion de esta clase podria ir aqui. */
    private Usuarios usuarioSession;// NOPMD - variable usada en la implementacion
    private DaoLema daoLema;// NOPMD - dao
    protected static org.slf4j.Logger log2 = LoggerFactory.getLogger(ServicioLemaImpl.class);
    @Autowired
    private DaoTipificador daoTipificador;
    @Autowired
    private DaoDefinicion daoDefinicion;

    @Autowired
    @Override
    public void setUsuarioSession(Usuarios usuarioSession) {
        this.usuarioSession = usuarioSession;
        super.setUsuarioSession(usuarioSession);
    }

    @Autowired
    public void setDaoLema(DaoLema daoLema) {
        this.daoLema = daoLema;
        super.setDaoGenerico(daoLema);
    }

    @Override
    public Map buscarSinonimos(Long idLema) {
        Map retorno = new HashMap();

        Lema lema = daoLema.getById(idLema);
        if (lema == null) {
            throw new NoSuchElementException("El lema buscado no existe");
        }

        retorno.put("id", lema.getId());
        retorno.put("texto", lema.getTexto());

        List<Pair<String, Object>> parametros = new ArrayList<>();
        parametros.add(new Pair<>("idLema", idLema));

        List<Map> sinonimos = daoLema.ejecutarSQLQueryMap(""
                + "select lema.id, lema.texto\n"
                + "from relacion_lema_definicion a \n"
                + "inner join relacion_lema_definicion sn on sn.definicion_id = a.definicion_id\n"
                + "inner join lema on lema.id = sn.lema_id\n"
                + "where a.lema_id = :idLema and sn.lema_id <> :idLema\n"
                + "order by lema.texto", parametros);

        retorno.put("sinonimos", sinonimos);

        return retorno;
    }

    @Override
    public Map obtenerLema(Long idLema) {
        Map retorno = new HashMap();

        Lema lema = daoLema.getById(idLema);
        if (lema == null) {
            throw new NoSuchElementException("El lema buscado no existe");
        }

        retorno.put("id", lema.getId());
        retorno.put("texto", lema.getTexto());
        retorno.put("sufijo", lema.getSufijo());
        retorno.put("genero", lema.getGenero());

        List<Pair<String, Object>> parametros = new ArrayList<>();
        parametros.add(new Pair<>("idLema", idLema));

        //consulto las definiciones asociadas al lema
        List<Map> definiciones = daoDefinicion.ejecutarSQLQueryMap(""
                + "select def.id, def.definicion, def.ejemplo as ejem1, defr.ejemplo, defr.regiones\n"
                + "from relacion_lema_definicion defr \n"
                + "inner join definicion def on def.id = defr.definicion_id\n"
                + "where defr.lema_id = :idLema \n"
                + "order by def.definicion", parametros);

        List<Map> definicionesR = new ArrayList<>();
        
        //por cada lema encontrado reviso sus tipificadores o etiquetas
        for (Map definicion : definiciones) {
            Map reg = new HashMap();
            reg.put("id", definicion.get("id"));
            reg.put("definicion", definicion.get("definicion"));
            reg.put("ejemplo", definicion.get("ejemplo"));

            //consulto las marcar regionales
            List<String> regiones = new ArrayList<>();
            if (definicion.get("regiones") != null) {
                JSONArray regs = new JSONArray(definicion.get("regiones").toString());
                regiones = new ArrayList<>();
                for (int i = 0; i < regs.length(); i++) {
                    regiones.add(regs.getString(i));
                }
            }
            reg.put("marcas_regionales", regiones);

            //consulto las marcas de uso
            List<Pair<String, Object>> parametrosD = new ArrayList<>();
            parametrosD.add(new Pair<>("idDefinicion", Long.parseLong(reg.get("id")+"")));
            parametrosD.add(new Pair<>("tipo", Tipificador.Tipo.MARCA_USO.name()));

            List<Map> tipificadores = daoTipificador.ejecutarSQLQueryMap(""
                    + "select tip.nombre\n"
                    + "from relacion_tipificador_definicion tipr \n"
                    + "inner join tipificador tip on tip.id = tipr.tipificador_id\n"
                    + "where tipr.definicion_id = :idDefinicion and tip.tipo = :tipo", parametrosD);
            List<String> registros = new ArrayList<>();
            for (Map datoTipo : tipificadores) {
                registros.add(datoTipo.get("nombre").toString());
            }
            reg.put("marcas_uso", registros);
            
            //consulto las fuentes
            parametrosD = new ArrayList<>();
            parametrosD.add(new Pair<>("idDefinicion", Long.parseLong(reg.get("id")+"")));
            parametrosD.add(new Pair<>("tipo", Tipificador.Tipo.FUENTE.name()));

            tipificadores = daoTipificador.ejecutarSQLQueryMap(""
                    + "select tip.nombre\n"
                    + "from relacion_tipificador_definicion tipr \n"
                    + "inner join tipificador tip on tip.id = tipr.tipificador_id\n"
                    + "where tipr.definicion_id = :idDefinicion and tip.tipo = :tipo", parametrosD);
            registros = new ArrayList<>();
            for (Map datoTipo : tipificadores) {
                registros.add(datoTipo.get("nombre").toString());
            }
            reg.put("fuentes", registros);
            
            //consulto las marcas gramaticales
            parametrosD = new ArrayList<>();
            parametrosD.add(new Pair<>("idDefinicion", Long.parseLong(reg.get("id")+"")));
            parametrosD.add(new Pair<>("tipo", Tipificador.Tipo.MARCA_GRAMATICAL.name()));

            tipificadores = daoTipificador.ejecutarSQLQueryMap(""
                    + "select tip.nombre\n"
                    + "from relacion_tipificador_definicion tipr \n"
                    + "inner join tipificador tip on tip.id = tipr.tipificador_id\n"
                    + "where tipr.definicion_id = :idDefinicion and tip.tipo = :tipo", parametrosD);
            registros = new ArrayList<>();
            if(lema.getGenero()!=null && lema.getGenero().equals('f')){
                registros.add("femenino");
            }else if(lema.getGenero()!=null && lema.getGenero().equals('m')){
                registros.add("masculino");
            }
            for (Map datoTipo : tipificadores) {
                registros.add(datoTipo.get("nombre").toString());
            }
            reg.put("marcas_gramaticales", registros);
            
            reg.put("frecuencia_uso", null);

            definicionesR.add(reg);
        }

        retorno.put("definiciones", definicionesR);

        return retorno;
    }

}
