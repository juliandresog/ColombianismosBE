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
@Table(name = "relacion_tipificador_definicion")
public class TipificadorDefinicion {
    
    @EmbeddedId
    private TipificadorDefinicionKey id;
 
    @ManyToOne
    @MapsId("tipificador_id")
    @JoinColumn(name = "tipificador_id")
    private Tipificador tipificador;
 
    @ManyToOne
    @MapsId("definicion_id")
    @JoinColumn(name = "definicion_id")
    private Definicion definicion;

    public TipificadorDefinicionKey getId() {
        return id;
    }

    public void setId(TipificadorDefinicionKey id) {
        this.id = id;
    }

    public Tipificador getTipificador() {
        return tipificador;
    }

    public void setTipificador(Tipificador tipificador) {
        this.tipificador = tipificador;
    }

    public Definicion getDefinicion() {
        return definicion;
    }

    public void setDefinicion(Definicion definicion) {
        this.definicion = definicion;
    }
    
    
}
