/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.client.datos.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author josorio
 */
@Entity
@Table(name = "definicion")
public class Definicion extends EntidadPerpetua{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "definicion", nullable = false, columnDefinition = "text")
    private String definicion;
    
    @Column(name = "ejemplo", nullable = true, columnDefinition = "text")
    private String ejemplo;
    
    @Column(name = "frecuencia_uso", nullable = true)
    private Float frecuenciaUso;

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
    
    @Transient
    @Override
    public String getLabel() {
        return definicion;
    }
}
