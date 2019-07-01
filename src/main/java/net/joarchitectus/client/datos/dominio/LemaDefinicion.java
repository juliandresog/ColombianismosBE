/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.client.datos.dominio;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 *
 * @author josorio
 */
@Entity
@Table(name = "relacion_lema_definicion")
public class LemaDefinicion {
    
    @EmbeddedId
    private LemaDefinicionKey id;
 
    @ManyToOne
    @MapsId("lema_id")
    @JoinColumn(name = "lema_id")
    private Lema lema;
 
    @ManyToOne
    @MapsId("definicion_id")
    @JoinColumn(name = "definicion_id")
    private Definicion definicion;

    public LemaDefinicionKey getId() {
        return id;
    }

    public void setId(LemaDefinicionKey id) {
        this.id = id;
    }

    public Lema getLema() {
        return lema;
    }

    public void setLema(Lema lema) {
        this.lema = lema;
    }

    public Definicion getDefinicion() {
        return definicion;
    }

    public void setDefinicion(Definicion definicion) {
        this.definicion = definicion;
    }
    
    
}
