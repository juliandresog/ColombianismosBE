/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.vista.dto;

/**
 *
 * @author josorio
 */
public class DTOEvento {
    private String fechaEvento;
    private String tipoEvento;
    private String placa;

    public DTOEvento(String fechaEvento, String tipoEvento, String placa) {
        this.fechaEvento = fechaEvento;
        this.tipoEvento = tipoEvento;
        this.placa = placa;
    }

    public DTOEvento() {
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    @Override
    public String toString() {
        return "DTOEvento{" + "fechaEvento=" + fechaEvento + ", tipoEvento=" + tipoEvento + ", placa=" + placa + '}';
    }
    
    
    
}
