/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.client.datos.dominio;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author josorio
 */
@Entity
@Table(name = "pais")
public class Pais extends EntidadBase{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gen_pais")
    @SequenceGenerator(name = "gen_pais", sequenceName = "pais_id_seq")
    private Long id;
    @Column(name = "nombre", nullable = true, length = 250)
    private String nombre;
    @Column(name = "isocode", nullable = true, length = 150)
    private String isocode;
    @Column(name = "countrycode", nullable = true, length = 150)
    private String countrycode;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha")
    private Date fecha;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIsocode() {
        return isocode;
    }

    public void setIsocode(String isocode) {
        this.isocode = isocode;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Transient
    @Override
    public String getLabel() {
        return nombre;
    }
    
    
    
}
