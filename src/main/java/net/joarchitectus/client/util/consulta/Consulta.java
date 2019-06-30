/**
* Consulta
* Versión 1.0
* 15/09/2013
*
* Copyright(c) 2007-2012, Boos IT.
* admin@boos.com.co
*
* http://boos.com.co/license
**/

package net.joarchitectus.client.util.consulta;

import net.joarchitectus.client.util.Pair;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author josorio
 */
public class Consulta implements Serializable {
    /**
     * Conetiene las restricciones que va a tener la consulta.
     */
    private Map<String, Pair<Comparacion, Object>> restricciones;
    /**
     * Contiene el ordenamiento que tendra la consulta
     */
    private List<Pair<String, Orden>> orden;
    /**
     * Contiene los alias que tendra la consulta para las tablas asociadas con la entidad principal.
     */
    private List<Pair<String, String>> alias;
    
    public Consulta() {
        restricciones= new HashMap<String, Pair<Comparacion, Object>>();
        orden= new ArrayList<Pair<String, Orden>>();
        alias = new ArrayList<Pair<String, String>>();
    }
    
    public Map<String, Pair<Comparacion, Object>> getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(Map<String, Pair<Comparacion, Object>> restricciones) {
        this.restricciones = restricciones;
    }

    public List<Pair<String, Orden>> getOrden() {
        return orden;
    }

    public void setOrden(List<Pair<String, Orden>> orden) {
        this.orden = orden;
    }

    public List<Pair<String, String>> getAlias() {
        return alias;
    }

    public void setAlias(List<Pair<String, String>> alias) {
        this.alias = alias;
    }
    
    
}

