/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.client.datos.dominio;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author josorio
 */
@Embeddable
public class TipificadorDefinicionKey implements Serializable {
    
    @Column(name = "tipificador_id")
    private Long tipificadorId;
 
    @Column(name = "definicion_id")
    private Long definicionId;

    public Long getTipificadorId() {
        return tipificadorId;
    }

    public void setTipificadorId(Long tipificadorId) {
        this.tipificadorId = tipificadorId;
    }

    public Long getDefinicionId() {
        return definicionId;
    }

    public void setDefinicionId(Long definicionId) {
        this.definicionId = definicionId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.tipificadorId);
        hash = 53 * hash + Objects.hashCode(this.definicionId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipificadorDefinicionKey other = (TipificadorDefinicionKey) obj;
        if (!Objects.equals(this.tipificadorId, other.tipificadorId)) {
            return false;
        }
        if (!Objects.equals(this.definicionId, other.definicionId)) {
            return false;
        }
        return true;
    }
    
    
    
}
