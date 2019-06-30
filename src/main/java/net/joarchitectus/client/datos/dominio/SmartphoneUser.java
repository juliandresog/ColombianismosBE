/**
 * SmartphoneUser
 * Versión 1.0
 * 15/10/2014
 *
 * Copyright(c) 2007-2015, Boos IT.
 * admin@boos.com.co
 *
 * http://boos.com.co/license
 **/

package net.joarchitectus.client.datos.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author josorio
 */
@Entity
//@Table(name = "trv_smartphone_user", indexes = { @Index( name="idx_smartphone_tokenauth", columnList = "token_auth" ) } )
@Table(name = "trv_smartphone_user")
public class SmartphoneUser extends EntidadBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "device_token", nullable = false, length = 500)
    private String deviceToken;
    
    @Column(name = "udid", nullable = false, length = 500)
    private String udid;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario", nullable = false)
    private Usuarios usuario;
    
    @Column(name = "token_auth", length = 200)
    private String tokenAuth;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    
    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }   

    public String getTokenAuth() {
        return tokenAuth;
    }

    public void setTokenAuth(String tokenAuth) {
        this.tokenAuth = tokenAuth;
    }
    
    @Transient
    @Override
    public String getLabel() {
        return deviceToken;
    }

    
    
}
