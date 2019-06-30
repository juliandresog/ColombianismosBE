/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * AAuditLog
 * Versión 1.0
 * 21-nov-2016
 *
 * Copyright(c) 2007-2016, Boos IT.
 * admin@boos.cloud
 *
 * http://boos.cloud/license
 **/

package net.joarchitectus.client.datos.dominio;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Para auditoria de las tablas de la BD
 * @author josorio
 */
@MappedSuperclass
public abstract class AAuditLog extends EntidadBase{
    
    @Transient
    private String accion;
    @Transient
    private Long idActor;

    public abstract Long getIdEntidad();
    @JsonIgnore
    public abstract String getLogDeatil();

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Long getIdActor() {
        return idActor;
    }

    public void setIdActor(Long idActor) {
        this.idActor = idActor;
    }
    
    
    
}
