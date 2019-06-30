/**
* DTOHistorico
* Versión 1.0
* 11/04/2013
* Copyright(c) 2007-2013, Boos IT.
* admin@boos.com
* http://boos.com/license
**/

package net.joarchitectus.client.datos.dominio.dto;

import java.io.Serializable;

/**
 * Se crea el DTO para el reporte histórico
 * 0001705: Se requiere poder exportar el resultado del histórico a Excel
 * @author Mario López
 */
public class DTOHistorico implements Serializable{
    
    private String id;
    private String latitud;
    private String longitud;
    private String fecha;
    private String velocidad;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(String velocidad) {
        this.velocidad = velocidad;
    }
    
}
