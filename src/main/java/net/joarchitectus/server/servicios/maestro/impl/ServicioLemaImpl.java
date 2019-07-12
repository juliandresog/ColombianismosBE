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

import net.joarchitectus.client.datos.dominio.Lema;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.server.datos.dao.DaoLema;
import net.joarchitectus.server.servicios.maestro.ServicioLema;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * La descripcion de la clase va aqui.
 * @version     1.0 12/07/2019
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

}
