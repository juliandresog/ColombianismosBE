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
public class LemaDefinicionKey implements Serializable {
    
    @Column(name = "lema_id")
    private Long LemaId;
 
    @Column(name = "definicion_id")
    private Long definicionId;

    public Long getLemaId() {
        return LemaId;
    }

    public void setLemaId(Long LemaId) {
        this.LemaId = LemaId;
    }

    public Long getDefinicionId() {
        return definicionId;
    }

    public void setDefinicionId(Long definicionId) {
        this.definicionId = definicionId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.LemaId);
        hash = 97 * hash + Objects.hashCode(this.definicionId);
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
        final LemaDefinicionKey other = (LemaDefinicionKey) obj;
        if (!Objects.equals(this.LemaId, other.LemaId)) {
            return false;
        }
        if (!Objects.equals(this.definicionId, other.definicionId)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
