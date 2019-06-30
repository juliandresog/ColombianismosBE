package net.joarchitectus.client.datos.dominio;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Rol generated by hbm2java
 */
@Entity
@Table(name = "rol")
public class Rol extends EntidadPerpetua   
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="gen_rol")
    @SequenceGenerator(name="gen_rol", sequenceName = "rol_id_seq")
    private Long id;
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;
    @Column(name = "autorizado_login")
    private Boolean autorizadoLogin;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rol")
    private Set<Usuarios> usuarios = new HashSet<Usuarios>(0);

    public static final long ROL_ADMINISTRADOR_SISTEMA = 1;
    public static final long ROL_ADMINISTRADOR_EMPRESA = 2;


    public Rol()
    {
    }

    public Rol(Long id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }

    public Rol(Long id, String nombre, Date fechaInactivado, Set<Usuarios> usuarios)
    {
        this.id = id;
        this.nombre = nombre;
        this.fechaInactivado = fechaInactivado;
        this.usuarios = usuarios;
    }

    
    @Override
    public Long getId()
    {
        return this.id;
    }

    @Override
    public void setId(Long id)
    {
        this.id = id;
    }

        public String getNombre()
    {
        return this.nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

//        public Date getFechaInactivado()
//    {
//        return this.fechaInactivado;
//    }
//
//    public void setFechaInactivado(Date fechaInactivado)
//    {
//        this.fechaInactivado = fechaInactivado;
//    }

        public Boolean getAutorizadoLogin() {
        return autorizadoLogin;
    }

    public void setAutorizadoLogin(Boolean autorizadoLogin) {
        this.autorizadoLogin = autorizadoLogin;
    }



    @JsonIgnore(true)
    public Set<Usuarios> getUsuarios()
    {
        return this.usuarios;
    }

    public void setUsuarios(Set<Usuarios> usuarios)
    {
        this.usuarios = usuarios;
    }

    @Transient
    @Override
    public String getLabel() {
        return getNombre();
    }
}


