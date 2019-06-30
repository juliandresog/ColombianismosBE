/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * ATipo
 * Versión 1.0
 * 19-dic-2016
 *
 * Copyright(c) 2007-2016, Boos IT.
 * admin@boos.cloud
 *
 * http://boos.cloud/license
 **/

package net.joarchitectus.client.datos.dominio;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 *
 * @author josorio
 */
@MappedSuperclass
public abstract class ATipo extends EntidadPerpetua{
    

    @Column(name = "nombre", nullable = false)
    private String nombre;

    public ATipo() {
    }

    public ATipo(Long id) {
        setId(id); 
    }

    public ATipo(String nombre) {
        this.nombre = nombre;
    }
    
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    @Transient
    @Override
    public String getLabel(){
        return nombre;
    }
}
