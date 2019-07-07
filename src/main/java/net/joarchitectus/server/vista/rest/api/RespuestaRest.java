/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.vista.rest.api;

import java.io.Serializable;

/**
 *
 * @author josorio
 */
public class RespuestaRest <T extends Object> implements Serializable {
    
    /** Objeto de respuesta */
    private T data;
    private boolean success;
    private String message;
    private Object total;

    public T getData() {
        return data;
    }

    public void setData(T datos) {
        this.data = datos;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getTotal() {
        return total;
    }

    public void setTotal(Object total) {
        this.total = total;
    }
    
    
}
