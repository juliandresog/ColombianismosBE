/**
 * Empresa Versión 1.0 15/09/2013
 * 
* Copyright(c) 2007-2012, Boos IT. admin@boos.com.co
 * 
* http://boos.com.co/license
*
 */
package net.joarchitectus.client.datos.dominio;

import javax.persistence.*;

/**
 *
 * @author josorio
 */
@Entity
@Table(name = "empresa")
public class Empresa extends EntidadPerpetua{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gen_empresa")
    @SequenceGenerator(name = "gen_empresa", sequenceName = "empresa_id_seq")
    private Long id;
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Transient
    @Override
    public String getLabel() {
        return getNombre();
    }
}
