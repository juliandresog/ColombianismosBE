/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.servicios.maestro.impl;

import net.joarchitectus.client.datos.dominio.Definicion;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.server.datos.dao.DaoDefinicion;
import net.joarchitectus.server.datos.dao.DaoTipificador;
import net.joarchitectus.server.servicios.maestro.ServicioDefinicion;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author josorio
 */
@Service(value = "servicioDefinicion")
public class ServicioDefinicionImpl extends ServicioMaestroImpl<Definicion> implements ServicioDefinicion {

    /* Un comentario sobre la implementacion de esta clase podria ir aqui. */
    private Usuarios usuarioSession;// NOPMD - variable usada en la implementacion
    private DaoDefinicion daoDefinicion;// NOPMD - dao
    protected static org.slf4j.Logger log2 = LoggerFactory.getLogger(ServicioDefinicionImpl.class);
    @Autowired
    private DaoTipificador daoTipificador;

    @Autowired
    @Override
    public void setUsuarioSession(Usuarios usuarioSession) {
        this.usuarioSession = usuarioSession;
        super.setUsuarioSession(usuarioSession);
    }

    @Autowired
    public void setDaoDefinicion(DaoDefinicion daoDefinicion) {
        this.daoDefinicion = daoDefinicion;
        super.setDaoGenerico(daoDefinicion);
    }
    
}
