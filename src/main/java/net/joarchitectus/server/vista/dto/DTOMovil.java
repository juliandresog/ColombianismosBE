/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.vista.dto;

/**
 *
 * @author josorio
 */
public class DTOMovil {
    private String tipoMovil;
    private String placa;
    private String marca;
    private String modelo;

    public DTOMovil() {
    }

    public DTOMovil(String tipoMovil, String placa, String marca, String modelo) {
        this.tipoMovil = tipoMovil;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
    }
    
    

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipoMovil() {
        return tipoMovil;
    }

    public void setTipoMovil(String tipoMovil) {
        this.tipoMovil = tipoMovil;
    }
    
    
    
}
