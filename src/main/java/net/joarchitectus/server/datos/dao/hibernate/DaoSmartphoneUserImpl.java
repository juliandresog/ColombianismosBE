/**
 * DaoSmartphoneUserImpl
 * Versión 1.0
 * 15/10/2014
 *
 * Copyright(c) 2007-2015, Boos IT.
 * admin@boos.com.co
 *
 * http://boos.com.co/license
 **/

package net.joarchitectus.server.datos.dao.hibernate;

import java.util.Collection;
import net.joarchitectus.client.datos.dominio.SmartphoneUser;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.server.datos.dao.DaoSmartphoneUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author josorio
 */
@Repository
public class DaoSmartphoneUserImpl extends DaoGenericoImpl<SmartphoneUser> implements DaoSmartphoneUser {

    @Transactional(readOnly = true)
    @Override
    public Collection<SmartphoneUser> listarMoviles(Long idUsuario) {
        //no olvidar crear indice de BD
        return sessionFactory.getCurrentSession()
                .createQuery("from SmartphoneUser where usuario.id = :idUsuario ")
                .setInteger("idUsuario", idUsuario.intValue())
                .list();
    }

    @Transactional(readOnly = true)
    @Override
    public SmartphoneUser buscarMovil(Long idUsuario, String udid) {
        return (SmartphoneUser)sessionFactory.getCurrentSession()
                .createQuery("from SmartphoneUser where usuario.id = :idUsuario and udid = :udid")
                .setInteger("idUsuario", idUsuario.intValue())
                .setString("udid", udid) 
                .setMaxResults(1).uniqueResult(); 
    }
    
    @Transactional(readOnly = true)
    @Override
    public SmartphoneUser buscarMovilDT(Long idUsuario, String devicetoken) {
        return (SmartphoneUser)sessionFactory.getCurrentSession()
                .createQuery("from SmartphoneUser where usuario.id = :idUsuario and deviceToken = :devicetoken")
                .setInteger("idUsuario", idUsuario.intValue())
                .setString("devicetoken", devicetoken) 
                .setMaxResults(1).uniqueResult(); 
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    @Override
    public boolean validarToken(String token) {
        Object reg = sessionFactory.getCurrentSession()
                .createSQLQuery("select id from smartphone_user where token_auth = :token ")
                .setString("token", token)
                .setMaxResults(1).uniqueResult();
        
        return reg != null;
    }
    
    @Transactional(readOnly = true)
    @Override
    public Usuarios validarTokenU(String token) {
        SmartphoneUser smartphone = (SmartphoneUser)sessionFactory.getCurrentSession()
                .createQuery("from SmartphoneUser where tokenAuth = :token")                
                .setString("token", token) 
                .setMaxResults(1).uniqueResult(); 
        
        if(smartphone==null){
            return null;
        }else{
            return smartphone.getUsuario();
        }
        
    }
}
