/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.client.datos.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author josorio
 */
@Entity
@Table(name = "lema", uniqueConstraints
        = {
            @UniqueConstraint(columnNames = "texto")
        })
public class Lema extends EntidadPerpetua{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "texto", nullable = false, columnDefinition = "text")
    private String texto;
    
    @Column(name = "sufijo", nullable = true, length = 100)
    private String sufijo;
    
    @Column(name = "genero", nullable = true)
    private Character genero;

    public Lema() {
    }

    public Lema(Long id) {
        this.id = id;
    }   
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getSufijo() {
        return sufijo;
    }

    public void setSufijo(String sufijo) {
        this.sufijo = sufijo;
    }

    public Character getGenero() {
        return genero;
    }

    public void setGenero(Character genero) {
        this.genero = genero;
    }
    
    

    @Transient
    @Override
    public String getLabel() {
        return texto;
    }
    
    
    
}
