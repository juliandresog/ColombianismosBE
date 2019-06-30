/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.client.datos.dominio;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

/**
 *
 * @author josorio
 */
@MappedSuperclass
public abstract class EntidadPerpetuaCM extends EntidadPerpetua{
    
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
