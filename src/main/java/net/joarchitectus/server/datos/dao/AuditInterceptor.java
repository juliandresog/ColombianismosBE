/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * AuditInterceptor Versión 1.0 20-oct-2017
 *
 * Copyright(c) 2007-2017, Boos IT.
 * admin@boos.com.co
 *
 * http://boos.com.co/license
 */
package net.joarchitectus.server.datos.dao;


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpSession;
import net.joarchitectus.client.datos.dominio.AAuditLog;
import net.joarchitectus.client.datos.dominio.AAuditLogPerpetua;
import net.joarchitectus.client.datos.dominio.Auditoria;
import net.joarchitectus.client.datos.dominio.EntidadBase;
import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * La descripcion de la clase va aqui.
 * https://www.mkyong.com/hibernate/hibernate-interceptor-example-audit-log/
 *
 * @version 1.0 20-oct-2017
 * @author josorio
 */
public class AuditInterceptor extends EmptyInterceptor {

    private static Logger log = Logger.getLogger(AuditInterceptor.class);
    private static final String DEFAULT_TENANTID = EntidadBase.DEFAULT_TENANTID;
    public static final String KEY_TENANTID_SESSION="key_tenantid";

    @Autowired
    protected SessionFactory sessionFactory;

    private Set inserts = new HashSet();
    private Set updates = new HashSet();
    private Set deletes = new HashSet();

    @Override
    public String onPrepareStatement(String sql) {
        String prepedStatement = super.onPrepareStatement(sql);
        //log.info("StatementB: " + prepedStatement); 
        return prepedStatement;
    }

    /**
     * Get tenantId in the session attribute KEY_TENANTID_SESSION
     *
     * @return TenantId on KEY_TENANTID_SESSION
     */
    public String resolveTenantByHttpSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //If session attribute exists returns tenantId saved on the session
        if (attr != null) {
            HttpSession session = attr.getRequest().getSession(false); // true == allow create
            if (session != null) {
                String tenant = (String) session.getAttribute(KEY_TENANTID_SESSION);
                if (tenant != null) {
                    return tenant;
                }
            }
        }
        //otherwise return default tenant
        log.warn("Tenant resolved in session is: " + DEFAULT_TENANTID);
        return DEFAULT_TENANTID;
    }

    @Override
    public void onDelete(Object entity,
            Serializable id,
            Object[] state,
            String[] propertyNames,
            Type[] types) {
        // do nothing
        log.info("Delete from " + entity.getClass().getName());
        if (entity instanceof AAuditLog) {
            log.warn("Delete from " + entity.getClass().getSimpleName() + " : " + ((AAuditLog) entity).getLogDeatil());
            deletes.add(entity);
        } else if (entity instanceof AAuditLogPerpetua) {
            log.warn("Delete from " + entity.getClass().getSimpleName() + " : " + ((AAuditLogPerpetua) entity).getLogDeatil());
            deletes.add(entity);
        }
    }

    public boolean onFlushDirty(Object entity,
            Serializable id,
            Object[] currentState,
            Object[] previousState,
            String[] propertyNames,
            Type[] types) {

//        if ( entity instanceof EntidadPerpetua ) {
//            updates++;
//            for ( int i=0; i < propertyNames.length; i++ ) {
//                if ( "lastUpdateTimestamp".equals( propertyNames[i] ) ) {
//                    currentState[i] = new Date();
//                    return true;
//                }
//            }
//        }
        if (entity instanceof AAuditLog) {
            log.warn("Update from " + entity.getClass().getSimpleName() + " : " + ((AAuditLog) entity).getLogDeatil());
            updates.add(entity);
        } else if (entity instanceof AAuditLogPerpetua) {
            log.warn("Update from " + entity.getClass().getSimpleName() + " : " + ((AAuditLogPerpetua) entity).getLogDeatil());
            updates.add(entity);
        }
        return false;
    }

    @Override
    public boolean onLoad(Object entity,
            Serializable id,
            Object[] state,
            String[] propertyNames,
            Type[] types) {
//        if (entity instanceof EntidadPerpetua) {
//            loads++;
//        }
        return false;
    }

    @Override
    public boolean onSave(Object entity,
            Serializable id,
            Object[] state,
            String[] propertyNames,
            Type[] types) {
        if (entity instanceof AAuditLog) {
            log.warn("Create from " + entity.getClass().getSimpleName() + " : " + ((AAuditLog) entity).getLogDeatil());
            inserts.add(entity);
        } else if (entity instanceof AAuditLogPerpetua) {
            log.warn("Create from " + entity.getClass().getSimpleName() + " : " + ((AAuditLogPerpetua) entity).getLogDeatil());
            inserts.add(entity);
        }

//        if ( entity instanceof EntidadPerpetua ) {
//            creates++;
//            for ( int i=0; i<propertyNames.length; i++ ) {
//                if ( "createTimestamp".equals( propertyNames[i] ) ) {
//                    state[i] = new Date();
//                    return true;
//                }
//            }
//        }
        return false;
    }

    @Override
    public void afterTransactionCompletion(Transaction tx) {
//        if (tx.wasCommitted() && (creates > 0 || updates > 0 || loads > 0)) {
//            log.debug("Creations: " + creates + ", Updates: " + updates + "Loads: " + loads);
//        }
//        updates = 0;
//        creates = 0;
//        loads = 0;
    }

    //called before commit into database
    @Override
    public void preFlush(Iterator iterator) {
        log.debug("preFlush");
    }

    //called after committed into database
    @Override
    public void postFlush(Iterator iterator) {
        log.debug("postFlush");
        try {

            for (Iterator it = inserts.iterator(); it.hasNext();) {
                Object entity = it.next();
                if (entity instanceof AAuditLog) {
                    Auditoria auditoria = new Auditoria();
                    auditoria.setAccion("CREATE");
                    auditoria.setActor(((AAuditLog) entity).getIdActor());
                    auditoria.setCodEntidad(((AAuditLog) entity).getId());
                    auditoria.setEntidad(((AAuditLog) entity).getClass().getSimpleName());
                    auditoria.setDetalle(((AAuditLog) entity).getLogDeatil());
                    auditoria.setFecha(new Date());
                    if (auditoria.getCodEntidad() == null) {
                        auditoria.setCodEntidad(-1l);
                    }
                    sessionFactory.getCurrentSession().save(auditoria);
                } else if (entity instanceof AAuditLogPerpetua) {
                    Auditoria auditoria = new Auditoria();
                    auditoria.setAccion("CREATE");
                    auditoria.setActor(((AAuditLogPerpetua) entity).getIdActor());
                    auditoria.setCodEntidad(((AAuditLogPerpetua) entity).getId());
                    auditoria.setEntidad(((AAuditLogPerpetua) entity).getClass().getSimpleName());
                    auditoria.setDetalle(((AAuditLogPerpetua) entity).getLogDeatil());
                    auditoria.setFecha(new Date());
                    if (auditoria.getCodEntidad() == null) {
                        auditoria.setCodEntidad(-1l);
                    }
                    sessionFactory.getCurrentSession().save(auditoria);
                }
            }

            for (Iterator it = updates.iterator(); it.hasNext();) {
                Object entity = it.next();
                if (entity instanceof AAuditLog) {
                    Auditoria auditoria = new Auditoria();
                    auditoria.setAccion("UPDATE");
                    auditoria.setActor(((AAuditLog) entity).getIdActor());
                    auditoria.setCodEntidad(((AAuditLog) entity).getId());
                    auditoria.setEntidad(((AAuditLog) entity).getClass().getSimpleName());
                    auditoria.setDetalle(((AAuditLog) entity).getLogDeatil());
                    auditoria.setFecha(new Date());
                    if (auditoria.getCodEntidad() == null) {
                        auditoria.setCodEntidad(-1l);
                    }
                    sessionFactory.getCurrentSession().save(auditoria);
                } else if (entity instanceof AAuditLogPerpetua) {
                    Auditoria auditoria = new Auditoria();
                    auditoria.setAccion("UPDATE");
                    auditoria.setActor(((AAuditLogPerpetua) entity).getIdActor());
                    auditoria.setCodEntidad(((AAuditLogPerpetua) entity).getId());
                    auditoria.setEntidad(((AAuditLogPerpetua) entity).getClass().getSimpleName());
                    auditoria.setDetalle(((AAuditLogPerpetua) entity).getLogDeatil());
                    auditoria.setFecha(new Date());
                    if (auditoria.getCodEntidad() == null) {
                        auditoria.setCodEntidad(-1l);
                    }
                    sessionFactory.getCurrentSession().save(auditoria);
                }
            }

            for (Iterator it = deletes.iterator(); it.hasNext();) {
                Object entity = it.next();
                if (entity instanceof AAuditLog) {
                    Auditoria auditoria = new Auditoria();
                    auditoria.setAccion("DELETE");
                    auditoria.setActor(((AAuditLog) entity).getIdActor());
                    auditoria.setCodEntidad(((AAuditLog) entity).getId());
                    auditoria.setEntidad(((AAuditLog) entity).getClass().getSimpleName());
                    auditoria.setDetalle(((AAuditLog) entity).getLogDeatil());
                    auditoria.setFecha(new Date());
                    if (auditoria.getCodEntidad() == null) {
                        auditoria.setCodEntidad(-1l);
                    }
                    sessionFactory.getCurrentSession().save(auditoria);
                } else if (entity instanceof AAuditLogPerpetua) {
                    Auditoria auditoria = new Auditoria();
                    auditoria.setAccion("DELETE");
                    auditoria.setActor(((AAuditLogPerpetua) entity).getIdActor());
                    auditoria.setCodEntidad(((AAuditLogPerpetua) entity).getId());
                    auditoria.setEntidad(((AAuditLogPerpetua) entity).getClass().getSimpleName());
                    auditoria.setDetalle(((AAuditLogPerpetua) entity).getLogDeatil());
                    auditoria.setFecha(new Date());
                    if (auditoria.getCodEntidad() == null) {
                        auditoria.setCodEntidad(-1l);
                    }
                    sessionFactory.getCurrentSession().save(auditoria);
                }
            }

        } finally {
            inserts.clear();
            updates.clear();
            deletes.clear();
        }
    }

}
