/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.client.datos.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author josorio
 */
@Entity
@Table(name = "tipificador")
public class Tipificador extends EntidadPerpetua{
    
    public enum Tipo{MARCA_GRAMATICAL, MARCA_USO, MARCA_REGIONAL, FUENTE};
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false, columnDefinition = "text")
    private String nombre;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 150)
    private Tipo tipo;

    public Tipificador() {
    }

    public Tipificador(Long id) {
        this.id = id;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Transient
    @Override
    public String getLabel() {
        return nombre;
    }
    
    
}
