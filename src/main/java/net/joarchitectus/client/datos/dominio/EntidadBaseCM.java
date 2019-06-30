/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * EntidadBaseCM Versión 1.0 03-oct-2017
 *
 * Copyright(c) 2007-2017, Boos IT.
 * admin@boos.com.co
 *
 * http://boos.com.co/license
 */

package net.joarchitectus.client.datos.dominio;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

/**
 * La descripcion de la clase va aqui.
 * @version 	1.0 03-oct-2017
 * @author josorio
 */
@MappedSuperclass
public abstract class EntidadBaseCM extends EntidadBase {

    @Column(name = "creado", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date creado;
    
    @Column(name = "modificado", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date modificado;

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public Date getModificado() {
        return modificado;
    }

    public void setModificado(Date modificado) {
        this.modificado = modificado;
    }
}
