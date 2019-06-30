/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.datos.dao.hibernate;

import net.joarchitectus.server.datos.dao.DaoUsuario;
import net.joarchitectus.client.datos.dominio.Usuarios;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jdbotero@gmail.com
 */
@Repository
public class DaoUsuarioImpl extends DaoGenericoImpl<Usuarios> implements DaoUsuario {

    @Autowired
    Usuarios usuarioSession;

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    public Usuarios getActivoByDocumento(String documento) {
        Usuarios retorno = null;

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuarios.class);
        criteria.add(Restrictions.eq("documento", documento));// NOPMD - 
        criteria.add(Restrictions.isNull("fechaInactivado"));// NOPMD - 
        retorno = (Usuarios) criteria.uniqueResult();

        return retorno;
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    public Usuarios getActivoByDocumentoAndClave(String documento, String clave) {
        Usuarios retorno = null;

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuarios.class);
        criteria.add(Restrictions.eq("documento", documento));
        criteria.add(Restrictions.eq("clave", clave));
        criteria.add(Restrictions.isNull("fechaInactivado"));
        retorno = (Usuarios) criteria.uniqueResult();

        return retorno;
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    public Usuarios getByLlaveCambioClave(String llave) {
        Usuarios retorno = null;

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuarios.class);
        criteria.add(Restrictions.eq("llaveCambioClave", llave));
        retorno = (Usuarios) criteria.uniqueResult();

        return retorno;
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    public List<Usuarios> getUsuariosActivos(String likeNombre) {
        List<Usuarios> retorno = null;

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuarios.class);
        criteria.add(Restrictions.isNull("fechaInactivado"));
        if (likeNombre != null && !likeNombre.isEmpty()) {
            criteria.add(
                    Restrictions.or(
                    Restrictions.ilike("documento", "%" + likeNombre + "%"),
                    Restrictions.or(Restrictions.ilike("nombres", "%" + likeNombre + "%"), Restrictions.ilike("apellidos", "%" + likeNombre + "%")))); // NOPMD - literales aceptados.
        }
        criteria.addOrder(Order.asc("nombres"));
        retorno = (List<Usuarios>) criteria.list();

        return retorno;
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    public List<Usuarios> getActivoByCorreo(String correo) {
        List<Usuarios> retorno = null;

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuarios.class);
        criteria.add(Restrictions.eq("correoElectronico", correo));// NOPMD - 
        criteria.add(Restrictions.isNull("fechaInactivado"));
        retorno = (List<Usuarios>) criteria.list();

        return retorno;
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    public List<Usuarios> getActivoByDocumentoAndNotId(String documento, Long id) {
        List<Usuarios> retorno = null;

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuarios.class);
        criteria.add(Restrictions.eq("documento", documento));
        criteria.add(Restrictions.ne("id", id));
        criteria.add(Restrictions.isNull("fechaInactivado"));
        retorno = (List<Usuarios>) criteria.list();

        return retorno;
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    public List<Usuarios> getActivoByCorreoAndNotId(String correo, Long id) {
        List<Usuarios> retorno = null;

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuarios.class);
        criteria.add(Restrictions.eq("correoElectronico", correo));
        criteria.add(Restrictions.ne("id", id));
        criteria.add(Restrictions.isNull("fechaInactivado"));
        retorno = (List<Usuarios>) criteria.list();

        return retorno;
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    public List<Usuarios> getUsuariosActivosByRol(int idRol, String likeNombre) {
        List<Usuarios> retorno = null;

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuarios.class);
        criteria.add(Restrictions.eq("rol.id", idRol));
        criteria.add(Restrictions.or(Restrictions.isNull("inactivo"), Restrictions.eq("inactivo", false)));// NOPMD - 
        criteria.add(Restrictions.isNull("fechaInactivado"));
        if (likeNombre != null && !likeNombre.isEmpty()) {
            criteria.add(
                    Restrictions.or(
                    Restrictions.ilike("documento", "%" + likeNombre + "%"),
                    Restrictions.or(Restrictions.ilike("nombres", "%" + likeNombre + "%"), Restrictions.ilike("apellidos", "%" + likeNombre + "%"))));
        }
        criteria.addOrder(Order.asc("nombres"));
        retorno = (List<Usuarios>) criteria.list();

        return retorno;
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    public List<Usuarios> getUsuariosInactivos() throws DataAccessException {
//        List<Usuario> retorno = null;
//
//        Criteria criteria = sessionFactory.getCurrentSession().createCriteria( Usuario.class );
//        criteria.add( Restrictions.isNotNull( "fechaInactivado" ) );
//        retorno = ( List<Usuario> ) criteria.list();
//
//        return retorno;
        return sessionFactory.getCurrentSession().createQuery("from Usuarios where fechaInactivado is not null ").list();
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    public List<Usuarios> listaUsuarios(int inicio, int limit) {
        return sessionFactory.getCurrentSession().createQuery("from Usuarios where fechaInactivado is null order by id desc").setFirstResult(inicio).setMaxResults(limit).list();
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    public Long cantidadListaUsuarios() {
        Long count = 0l;
        count = (Long) sessionFactory.getCurrentSession().createQuery("select count(*) from Usuarios where fechaInactivado is null ").uniqueResult();
        if (count == null) {
            return 0l;
        } else {
            return count;
        }
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    public List<Usuarios> listaUsuarios(String documento, String nombrefull, int inicio, int limit) {

        if (documento != null) {
            return sessionFactory.getCurrentSession().createQuery("from Usuarios where fechaInactivado is null and documento like :Xdocumento order by id desc").setString("Xdocumento", "%" + documento + "%").setFirstResult(inicio).setMaxResults(limit).list();
        }
        if (nombrefull != null) {
            return sessionFactory.getCurrentSession().createQuery("from Usuarios where fechaInactivado is null and (nombres like :nombrefull or apellidos like :nombrefull) order by id desc").setString("nombrefull", "%" + nombrefull + "%").setFirstResult(inicio).setMaxResults(limit).list();
        }
        //Si no retorno full usuarios
        return sessionFactory.getCurrentSession().createQuery("from Usuarios where fechaInactivado is null order by id desc").setFirstResult(inicio).setMaxResults(limit).list();
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    public Long cantidadListaUsuarios(String documento, String nombrefull) {
        Long count = 0l;
        if (documento == null && nombrefull == null) {
            count = (Long) sessionFactory.getCurrentSession().createQuery("select count(*) from Usuarios where fechaInactivado is null ").uniqueResult();
        }
        if (documento != null) {
            count = (Long) sessionFactory.getCurrentSession().createQuery("select count(*) from Usuarios where fechaInactivado is null  and documento like :Xdocumento ").setString("Xdocumento", "%" + documento + "%").uniqueResult();
        }
        if (nombrefull != null) {
            count = (Long) sessionFactory.getCurrentSession().createQuery("select count(*) from Usuarios where fechaInactivado is null and (nombres like :nombrefull or apellidos like :nombrefull) ").setString("nombrefull", "%" + nombrefull + "%").uniqueResult();
        }
        if (count == null) {
            return 0l;
        } else {
            return count;
        }
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    public Usuarios getActivoByEmailAndClave(String email, String clave) {
        Usuarios retorno = null;

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuarios.class);
        criteria.add(Restrictions.eq("correoElectronico", email));
        criteria.add(Restrictions.eq("clave", clave));
        criteria.add(Restrictions.isNull("fechaInactivado"));
        retorno = (Usuarios) criteria.uniqueResult();

        return retorno;
    }

    @Transactional(readOnly = true)
    public List<Usuarios> getUsuariosActivosPropietarios(long idRol) throws DataAccessException {
        List<Usuarios> retorno = null;

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuarios.class);
        criteria.add(Restrictions.eq("rol.id", idRol));
        criteria.add(Restrictions.or(Restrictions.isNull("inactivo"), Restrictions.eq("inactivo", false)));
        criteria.add(Restrictions.isNull("fechaInactivado"));

        criteria.addOrder(Order.asc("nombres"));
        retorno = (List<Usuarios>) criteria.list();

        return retorno;
    }

    @Transactional(readOnly = true)
    public Usuarios getActivoByCorreo2(String correo) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuarios.class);
        criteria.add(Restrictions.eq("correoElectronico", correo));
        criteria.add(Restrictions.isNull("fechaInactivado"));
        return (Usuarios)criteria.uniqueResult();
    }
    
    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    @Override
    public boolean validarToken(String token) {
        Object reg = sessionFactory.getCurrentSession()
                .createSQLQuery("select id from usuarios where token_auth = :token ")
                .setString("token", token)
                .setMaxResults(1).uniqueResult();

        return reg != null;
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    @Override
    public Usuarios validarTokenU(String token) {
        Object reg = sessionFactory.getCurrentSession()
                .createQuery("from Usuarios where tokenAuth = :token ")
                .setString("token", token)
                .setMaxResults(1).uniqueResult();

        return (Usuarios) reg;
    }
}
