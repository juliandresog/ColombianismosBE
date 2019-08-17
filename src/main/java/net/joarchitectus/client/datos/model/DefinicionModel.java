/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.client.datos.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author josorio
 */
public class DefinicionModel implements Serializable{
    
    private Long id;
    private String definicion;
    private String ejemplo;
    private Float frecuenciaUso;
    private List<Long> lemas;
    private List<Long> marcaGramatical;
    private List<Long> marcaRegional;
    private List<Long> marcaUso;

    public DefinicionModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDefinicion() {
        return definicion;
    }

    public void setDefinicion(String definicion) {
        this.definicion = definicion;
    }

    public String getEjemplo() {
        return ejemplo;
    }

    public void setEjemplo(String ejemplo) {
        this.ejemplo = ejemplo;
    }

    public Float getFrecuenciaUso() {
        return frecuenciaUso;
    }

    public void setFrecuenciaUso(Float frecuenciaUso) {
        this.frecuenciaUso = frecuenciaUso;
    }

    public List<Long> getLemas() {
        return lemas;
    }

    public void setLemas(List<Long> lemas) {
        this.lemas = lemas;
    }

    public List<Long> getMarcaGramatical() {
        return marcaGramatical;
    }

    public void setMarcaGramatical(List<Long> marcaGramatical) {
        this.marcaGramatical = marcaGramatical;
    }

    public List<Long> getMarcaRegional() {
        return marcaRegional;
    }

    public void setMarcaRegional(List<Long> marcaRegional) {
        this.marcaRegional = marcaRegional;
    }

    public List<Long> getMarcaUso() {
        return marcaUso;
    }

    public void setMarcaUso(List<Long> marcaUso) {
        this.marcaUso = marcaUso;
    }
    
    
    
}
