/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Auditoria
 * Versión 1.0
 * 15-dic-2016
 *
 * Copyright(c) 2007-2016, Boos IT.
 * admin@boos.cloud
 *
 * http://boos.cloud/license
 **/

package net.joarchitectus.client.datos.dominio;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author josorio
 */
@Entity
@Table(name = "trv_auditoria")
public class Auditoria extends EntidadBase{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", nullable = false)
    private Date fecha;
    
    @Column(name = "entidad", nullable = false, length = 100)
    private String entidad;
    
    @Column(name = "cod_entidad", nullable = false)
    private Long codEntidad;
    
    @Column(name = "accion", nullable = true, length = 30)
    private String accion;
    
    @Lob
    @Column(name = "detalle")
    private String detalle;
    
    @Column(name = "actor")
    private Long actor;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public Long getCodEntidad() {
        return codEntidad;
    }

    public void setCodEntidad(Long codEntidad) {
        this.codEntidad = codEntidad;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Long getActor() {
        return actor;
    }

    public void setActor(Long actor) {
        this.actor = actor;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
    
    

    @Transient
    @Override
    public String getLabel() {
        return id+"autid";
    }
    
    

}
