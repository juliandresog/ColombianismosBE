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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import net.joarchitectus.client.datos.dominio.Lema;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.client.util.Pair;
import net.joarchitectus.server.datos.dao.DaoLema;
import net.joarchitectus.server.servicios.maestro.ServicioLema;
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

        List<Map> sinonimos = daoLema.ejecutarSQLQueryMap("select lema.id, lema.texto\n"
                + "from relacion_lema_definicion a \n"
                + "inner join relacion_lema_definicion sn on sn.definicion_id = a.definicion_id\n"
                + "inner join lema on lema.id = sn.lema_id\n"
                + "where a.lema_id = :idLema and sn.lema_id <> :idLema\n"
                + "order by lema.texto", parametros);
        
        retorno.put("sinonimos", sinonimos);
        
        return retorno;
    }

}
