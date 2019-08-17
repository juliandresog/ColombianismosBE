/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.datos.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.joarchitectus.client.datos.dominio.Definicion;
import net.joarchitectus.client.datos.dominio.LemaDefinicion;
import net.joarchitectus.client.datos.dominio.Tipificador;
import net.joarchitectus.client.datos.dominio.TipificadorDefinicion;
import net.joarchitectus.client.util.Pair;
import net.joarchitectus.server.datos.dao.DaoDefinicion;
import org.json.JSONArray;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author josorio
 */
@Repository
public class DaoDefinicionImpl extends DaoGenericoImpl<Definicion> implements DaoDefinicion {

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void guardar(LemaDefinicion lemaDef) {
        //Procedo a guardar en BD
        sessionFactory.getCurrentSession().saveOrUpdate(lemaDef);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void guardar(TipificadorDefinicion tipDef) {
        //Procedo a guardar en BD
        sessionFactory.getCurrentSession().saveOrUpdate(tipDef);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Long> getIDsLemas(Long idDefinicion) {
        List<Object> lista = sessionFactory.getCurrentSession()
                .createSQLQuery("select lema_id from relacion_lema_definicion where definicion_id = :idDefinicion")
                .setParameter("idDefinicion", idDefinicion)
                .list();

        List<Long> retorno = new ArrayList<>();

        for (Object id : lista) {
            retorno.add(Long.parseLong(id + ""));
        }

        return retorno;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Long> getIDsMarcasGramaticales(Long idDefinicion) {
        List<Object> lista = sessionFactory.getCurrentSession()
                .createSQLQuery("select tipificador_id "
                        + "from relacion_tipificador_definicion tipr "
                        + "inner join tipificador tip on tip.id = tipr.tipificador_id "
                        + "where tipr.definicion_id = :idDefinicion and tip.tipo = :tipo")
                .setParameter("idDefinicion", idDefinicion)
                .setParameter("tipo", Tipificador.Tipo.MARCA_GRAMATICAL.name())
                .list();

        List<Long> retorno = new ArrayList<>();

        for (Object id : lista) {
            retorno.add(Long.parseLong(id + ""));
        }

        return retorno;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Long> getIDsMarcasUso(Long idDefinicion) {
        List<Object> lista = sessionFactory.getCurrentSession()
                .createSQLQuery("select tipificador_id "
                        + "from relacion_tipificador_definicion tipr "
                        + "inner join tipificador tip on tip.id = tipr.tipificador_id "
                        + "where tipr.definicion_id = :idDefinicion and tip.tipo = :tipo")
                .setParameter("idDefinicion", idDefinicion)
                .setParameter("tipo", Tipificador.Tipo.MARCA_USO.name())
                .list();

        List<Long> retorno = new ArrayList<>();

        for (Object id : lista) {
            retorno.add(Long.parseLong(id + ""));
        }

        return retorno;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<Long> getIDsMarcasRegionales(Long idDefinicion) {
        List<Object> lista = sessionFactory.getCurrentSession()
                .createSQLQuery("select tipificador_id "
                        + "from relacion_tipificador_definicion tipr "
                        + "inner join tipificador tip on tip.id = tipr.tipificador_id "
                        + "where tipr.definicion_id = :idDefinicion and tip.tipo = :tipo")
                .setParameter("idDefinicion", idDefinicion)
                .setParameter("tipo", Tipificador.Tipo.MARCA_REGIONAL.name())
                .list();

        List<Long> retorno = new ArrayList<>();

        for (Object id : lista) {
            retorno.add(Long.parseLong(id + ""));
        }

        if (retorno.isEmpty()) {//debo buscar las regiones dentro de la relacion_lema_definicion
            List<Pair<String, Object>> parametros = new ArrayList<>();
            parametros.add(new Pair<>("idDefinicion", idDefinicion));

            //consulto las definiciones asociadas al lema donde hay info de regiones
            List<Map> lemas = this.ejecutarSQLQueryMap(""
                    + "select defr.lema_id, defr.definicion_id, defr.ejemplo, defr.regiones\n"
                    + "from relacion_lema_definicion defr \n"
                    + "where defr.definicion_id = :idDefinicion \n"
                    + "", parametros);
            
            List<String> lasRegiones = new ArrayList<>();
            for (Map lemaDef : lemas) {
                //en esa tabla se guardan como array json ["Antioquia","Caldas"]
                if(lemaDef.get("regiones")!=null && !lemaDef.get("regiones").toString().isEmpty()){
                    JSONArray regiones = new JSONArray(lemaDef.get("regiones").toString());
                    for (int i = 0; i < regiones.length(); i++) {
                        lasRegiones.add(regiones.getString(i));                        
                    }
                }
            }
            
            if(!lasRegiones.isEmpty()){
                parametros = new ArrayList<>();
                parametros.add(new Pair<>("lasRegiones", lasRegiones));
                
                List<Map> registros = this.ejecutarSQLQueryMap(""
                        + "select id from tipificador where nombre in (:lasRegiones) ",parametros);
                
                for (Map registro : registros) {
                    retorno.add(Long.parseLong(registro.get("id") + "")); 
                }
                
            }

        }

        return retorno;
    }

}
