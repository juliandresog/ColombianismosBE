/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * ServicioAdminEmpresaImpl Versión 1.0 18-sep-2017
 *
 * Copyright(c) 2007-2017, Boos IT.
 * admin@boos.com.co
 *
 * http://boos.com.co/license
 */

package net.joarchitectus.server.servicios.maestro.impl;

import java.util.ArrayList;
import java.util.List;
import net.joarchitectus.client.datos.dominio.Empresa;
import net.joarchitectus.client.datos.dominio.EntidadPerpetua;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.client.util.Pair;
import net.joarchitectus.client.util.consulta.Consulta;
import net.joarchitectus.client.util.consulta.Orden;
import net.joarchitectus.server.datos.dao.DaoEmpresa;
import net.joarchitectus.server.servicios.maestro.ServicioAdminEmpresa;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * La descripcion de la clase va aqui.
 * @version 	1.0 18-sep-2017
 * @author josorio
 */
@Service(value = "servicioAdminEmpresa")
public class ServicioAdminEmpresaImpl extends ServicioMaestroImpl<Empresa> implements ServicioAdminEmpresa {

    /* Un comentario sobre la implementacion de esta clase podria ir aqui. */
    
    private Usuarios usuarioSession;// NOPMD - variable usada en la implementacion
    private DaoEmpresa daoEmpresa;// NOPMD - dao
    protected static org.slf4j.Logger log2 = LoggerFactory.getLogger(ServicioAdminEmpresaImpl.class);

    @Autowired
    @Override
    public void setUsuarioSession(Usuarios usuarioSession) {
        this.usuarioSession = usuarioSession;
        super.setUsuarioSession(usuarioSession);
    }
    
    @Autowired
    public void setDaoEmpresa(DaoEmpresa daoEmpresa) {
        this.daoEmpresa = daoEmpresa;
        super.setDaoGenerico(daoEmpresa); 
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public List<Empresa> getEntidades(int tipo) {
        try{
            if (tipo == EntidadPerpetua.TIPO_UNDELETED) {
                //return daoEmpresa.listar();
                Consulta consulta = new Consulta();
                //indico que se ordena por el campo
                consulta.getOrden().add(new Pair("nombre", Orden.ASC));
                //Cargo la lsita de datos de la BD
                Pair<Long, List<Empresa>> result = daoEmpresa.listarPorConsulta(consulta, 0, 1000);
                return result.getB();
            } else if (tipo == EntidadPerpetua.TIPO_DELETED) {
                return daoEmpresa.listarInactivos();
            } else {
                return new ArrayList<Empresa>();
            }
        }catch(Exception e){
            log2.error("Error", e); 
            return new ArrayList<Empresa>();
        }
    }
   
}
