/**
 * DTOTipo
 * Versión 1.0
 * 4/09/2013
 * Copyright(c) 2007-2013, Boos IT.
 * admin@boos.com.co
 *
 * http://boos.com.co/license
 **/

package net.joarchitectus.client.datos.dominio.dto;

import java.io.Serializable;

/**
 *
 * @author josorio
 */
public class DTOTipo implements Serializable {

    private int id;
    private String nombre;

    public DTOTipo() {
    }

    public DTOTipo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}

