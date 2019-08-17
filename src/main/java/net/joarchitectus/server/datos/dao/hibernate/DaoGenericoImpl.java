package net.joarchitectus.server.datos.dao.hibernate;

import net.joarchitectus.client.datos.dominio.EntidadPerpetua;
import net.joarchitectus.client.util.Pair;
import net.joarchitectus.client.util.consulta.Comparacion;
import net.joarchitectus.client.util.consulta.Consulta;
import net.joarchitectus.client.util.consulta.Orden;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import net.joarchitectus.server.datos.dao.DaoGenerico;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import net.joarchitectus.client.datos.dominio.EntidadBaseCM;
import net.joarchitectus.client.datos.dominio.EntidadPerpetuaCM;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

/**
 * {@inheritDoc}
 */
public class DaoGenericoImpl<T> implements DaoGenerico<T> {

    protected Class<T> domainClass = extraerClaseDominio();
    @Autowired
    protected SessionFactory sessionFactory;

    private Logger logger = Logger.getLogger(DaoGenericoImpl.class);

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")// NOPMD - 
    protected Class<T> extraerClaseDominio() {
        if (domainClass == null) {
            ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
            domainClass = (Class) thisType.getActualTypeArguments()[0];
        }
        return domainClass;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public T getById(int id) {
        return (T) sessionFactory.getCurrentSession().get(extraerClaseDominio(), id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public T getById(Long id) {
        return (T) sessionFactory.getCurrentSession().get(extraerClaseDominio(), id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void guardar(T entity) {
        //reviso si la entidad tiene datos de fecha
        if (entity instanceof EntidadPerpetuaCM) {
            if (((EntidadPerpetuaCM) entity).getCreado() == null) {
                ((EntidadPerpetuaCM) entity).setCreado(new Date());
            }
            ((EntidadPerpetuaCM) entity).setModificado(new Date());
        } else if (entity instanceof EntidadBaseCM) {
            if (((EntidadBaseCM) entity).getCreado() == null) {
                ((EntidadBaseCM) entity).setCreado(new Date());
            }
            ((EntidadBaseCM) entity).setModificado(new Date());
        }
        //Procedo a guardar en BD
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void guardarMerge(T entity) {
        //reviso si la entidad tiene datos de fecha
        if (entity instanceof EntidadPerpetuaCM) {
            if (((EntidadPerpetuaCM) entity).getCreado() == null) {
                ((EntidadPerpetuaCM) entity).setCreado(new Date());
            }
            ((EntidadPerpetuaCM) entity).setModificado(new Date());
        } else if (entity instanceof EntidadBaseCM) {
            if (((EntidadBaseCM) entity).getCreado() == null) {
                ((EntidadBaseCM) entity).setCreado(new Date());
            }
            ((EntidadBaseCM) entity).setModificado(new Date());
        }
        //Procedo a guardar en BD
        sessionFactory.getCurrentSession().merge(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void borrar(T entity) {
        //Si es una entidad perpetua hago borrado logico
        if (entity instanceof EntidadPerpetua) {
            ((EntidadPerpetua) entity).setFechaInactivado(new Date());
            guardar(entity);
        } else {
            sessionFactory.getCurrentSession().delete(entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void borrarFisico(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void reactivar(T entity) throws Exception {
        //Si es una entidad perpetua hago borrado logico
        if (entity instanceof EntidadPerpetua) {
            ((EntidadPerpetua) entity).setFechaInactivado(null);
            guardar(entity);
        } else {
            //la entidad no se puede reactivar.
            throw new Exception("La entidad indicada no se puede reactivar");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void refrescarBloquear(T entity) {
        sessionFactory.getCurrentSession().refresh(entity, LockMode.UPGRADE);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<T> listar() {
        try {
            if (extraerClaseDominio().newInstance() instanceof EntidadPerpetua) {
                StringBuilder sb = new StringBuilder("from ");// NOPMD - 
                sb.append(extraerClaseDominio().getName());
                sb.append(" entity");// NOPMD - 
                sb.append(" where fechaInactivado is null ");
                return sessionFactory.getCurrentSession().createQuery(sb.toString()).list();
            } else {
                StringBuilder sb = new StringBuilder("from ");
                sb.append(extraerClaseDominio().getName());
                sb.append(" entity");
                return sessionFactory.getCurrentSession().createQuery(sb.toString()).list();
            }
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        StringBuilder sb = new StringBuilder("from ");
        sb.append(extraerClaseDominio().getName());
        sb.append(" entity");
        return sessionFactory.getCurrentSession().createQuery(sb.toString()).list();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<T> listarInactivos() throws DataAccessException {
        try {
            if (extraerClaseDominio().newInstance() instanceof EntidadPerpetua) {
                StringBuilder sb = new StringBuilder("from ");
                sb.append(extraerClaseDominio().getName());
                sb.append(" entity");
                sb.append(" where fechaInactivado is not null ");
                return sessionFactory.getCurrentSession().createQuery(sb.toString()).list();
            } else {
                return new ArrayList<T>();
            }
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<T>();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    @Override
    public List<T> listarPor(String campo, Object valor) throws Exception {
        if (extraerClaseDominio().newInstance() instanceof EntidadPerpetua) {
            return listarActivosPor(campo, valor);
        } else {
            StringBuilder sb = new StringBuilder("from ");
            sb.append(extraerClaseDominio().getName());
            sb.append(" entity");
            sb.append(" where " + campo + "= :valor ");
            return sessionFactory.getCurrentSession()
                    .createQuery(sb.toString())
                    .setParameter("valor", valor)
                    .list();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    @Override
    public List<T> listarActivosPor(String campo, Object valor) {
        StringBuilder sb = new StringBuilder("from ");
        sb.append(extraerClaseDominio().getName());
        sb.append(" entity");
        sb.append(" where " + campo + "= :valor and fechaInactivado is null ");
        return sessionFactory.getCurrentSession()
                .createQuery(sb.toString())
                .setParameter("valor", valor)
                .list();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    @Override
    public List<T> listarP(int start, int limit) throws InstantiationException, IllegalAccessException {
        if (extraerClaseDominio().newInstance() instanceof EntidadPerpetua) {
            StringBuilder sb = new StringBuilder("from ");// NOPMD - 
            sb.append(extraerClaseDominio().getName());
            sb.append(" entity");// NOPMD - 
            sb.append(" where activo='s' ");
            return sessionFactory.getCurrentSession().createQuery(sb.toString())
                    .setMaxResults(limit).setFirstResult(start).list();
        } else {
            StringBuilder sb = new StringBuilder("from ");
            sb.append(extraerClaseDominio().getName());
            sb.append(" entity");
            return sessionFactory.getCurrentSession().createQuery(sb.toString())
                    .setMaxResults(limit).setFirstResult(start).list();
        }
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    @Override
    public Pair<Long, List<T>> listarPorConsulta(Consulta consulta, Integer offset, Integer limit) throws Exception {
        Map<String, Pair<Comparacion, Object>> restricciones = consulta.getRestricciones();
        List<Pair<String, Orden>> orden = consulta.getOrden();
        List<Pair<String, String>> aliasC = consulta.getAlias();

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(domainClass);

        for (Pair<String, String> pair : aliasC) {
            criteria.createAlias(pair.getA(), pair.getB());
        }

        //si es una entidad marcada como entidad perpetua solo listo las activas.
        if (extraerClaseDominio().newInstance() instanceof EntidadPerpetua) {
            criteria.add(Restrictions.isNull("fechaInactivado"));
        }

        for (String prop : restricciones.keySet()) {

            Pair<Comparacion, Object> pair = restricciones.get(prop);

            if (pair.getB() == null) {
                if (pair.getA() != Comparacion.EQ && pair.getA() != Comparacion.NE) {
                    logger.error("No pueden haber campos con valor null con una comparacion diferente a EQ o NE");
                    throw new RuntimeException("No pueden haber haber campos en null que no sean EQ o NE");
                }
            }

            try {

                if (pair.getA() == Comparacion.EQ) {
                    if (pair.getB() instanceof Collection) {
                        Collection params = (Collection) pair.getB();

                        if (!params.isEmpty()) {
                            criteria.add(Restrictions.in(prop, (Collection) pair.getB()));
                        }
                    } else if (pair.getB() == null) {
                        criteria.add(Restrictions.isNull(prop));
                    } else {
                        criteria.add(Restrictions.eq(prop, pair.getB()));
                    }
                } else if (pair.getA() == Comparacion.GE) {
                    criteria.add(Restrictions.ge(prop, pair.getB()));
                } else if (pair.getA() == Comparacion.GT) {
                    criteria.add(Restrictions.gt(prop, pair.getB()));
                } else if (pair.getA() == Comparacion.LE) {
                    criteria.add(Restrictions.le(prop, pair.getB()));
                } else if (pair.getA() == Comparacion.LIKE) {
                    criteria.add(Restrictions.ilike(prop, pair.getB()));
                } else if (pair.getA() == Comparacion.LT) {
                    criteria.add(Restrictions.lt(prop, pair.getB()));
                } else if (pair.getA() == Comparacion.NE) {
                    if (pair.getB() instanceof Collection) {
                        Collection params = (Collection) pair.getB();

                        if (!params.isEmpty()) {
                            criteria.add(Restrictions.not(Restrictions.in(prop, (Collection) pair.getB())));
                        }
                    } else if (pair.getB() == null) {
                        criteria.add(Restrictions.isNotNull(prop));

                    } else {
                        criteria.add(Restrictions.ne(prop, pair.getB()));
                    }
//                } else if (pair.getA() == Comparacion.ISNULL) {
//                    criteria.add(Restrictions.isNull(prop));    
//                } else if (pair.getA() == Comparacion.ISNOTNULL) {
//                    criteria.add(Restrictions.isNotNull(prop));        
                } else {
                    throw new UnsupportedOperationException("El criterio no está soportado");
                }

            } catch (Exception e) {
                logger.error(String.format("Errror adicionando el criterio con nombre \"%s\"", pair.getB()));
                throw new RuntimeException(e);
            }
        }
        Long cantidad = null;
        try {
            cantidad = Long.parseLong("" + criteria.setProjection(Projections.projectionList()
                    .add(Projections.rowCount())).uniqueResult());
        } catch (Exception e) {
            logger.error("Error en el query", e);
            throw new RuntimeException(e);
        }
        criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.ROOT_ENTITY);

        for (Pair<String, Orden> pair : orden) {

            try {

                if (pair.getB() == Orden.ASC) {
                    criteria.addOrder(Order.asc(pair.getA()));
                } else if (pair.getB() == Orden.DESC) {
                    criteria.addOrder(Order.desc(pair.getA()));
                } else {
                    throw new UnsupportedOperationException("El orden no está soportado");
                }

            } catch (Exception e) {
                logger.error(String.format("Error adicionando el orden con nombre \"%s\"", pair.getA()));
                throw new RuntimeException(e);
            }

        }
        List<T> resultado;
        if (offset != null && limit != null) {
            resultado = criteria.setFirstResult(offset).setMaxResults(limit).list();
        } else {
            resultado = criteria.list();
        }
        return new Pair(cantidad, resultado);
    }

    @Transactional
    @SuppressWarnings("unchecked")
    @Override
    public int ejecutarUpdateDelete(String consulta) {
        return sessionFactory.getCurrentSession()
                .createSQLQuery(consulta).executeUpdate();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    @Override
    public List<Map> ejecutarSQLQueryMap(String consulta, List<Pair<String, Object>> parametros) {

        Query query = sessionFactory.getCurrentSession()
                .createSQLQuery(consulta)
                .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        for (Pair<String, Object> parametro : parametros) {
            if (parametro.getB() instanceof String) {
                query.setString(parametro.getA(), (String) parametro.getB());
            } else if (parametro.getB() instanceof Integer) {
                query.setInteger(parametro.getA(), (Integer) parametro.getB());
            } else if (parametro.getB() instanceof Long) {
                query.setLong(parametro.getA(), (Long) parametro.getB());
            } else if (parametro.getB() instanceof Double) {
                query.setDouble(parametro.getA(), (Double) parametro.getB());
            } else if (parametro.getB() instanceof Date) {
                query.setDate(parametro.getA(), (Date) parametro.getB());
            } else if (parametro.getB() instanceof Calendar) {
                query.setCalendar(parametro.getA(), (Calendar) parametro.getB());
            } else if (parametro.getB() instanceof Collection) {
                query.setParameterList(parametro.getA(), (Collection) parametro.getB());
            } else{
                query.setParameter(parametro.getA(), parametro.getB());
            }

        }

        return (List<Map>) query.list();
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    @Override
    public List<Map> ejecutarSQLQueryMap(String consulta, List<Pair<String, Object>> parametros, int start, int limit) {

        Query query = sessionFactory.getCurrentSession()
                .createSQLQuery(consulta)
                .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        for (Pair<String, Object> parametro : parametros) {
            if (parametro.getB() instanceof String) {
                query.setString(parametro.getA(), (String) parametro.getB());
            } else if (parametro.getB() instanceof Integer) {
                query.setInteger(parametro.getA(), (Integer) parametro.getB());
            } else if (parametro.getB() instanceof Long) {
                query.setLong(parametro.getA(), (Long) parametro.getB());
            } else if (parametro.getB() instanceof Double) {
                query.setDouble(parametro.getA(), (Double) parametro.getB());
            } else if (parametro.getB() instanceof Date) {
                query.setDate(parametro.getA(), (Date) parametro.getB());
            } else if (parametro.getB() instanceof Calendar) {
                query.setCalendar(parametro.getA(), (Calendar) parametro.getB());
            } else if (parametro.getB() instanceof Collection) {
                query.setParameterList(parametro.getA(), (Collection) parametro.getB());
            }

        }

        return (List<Map>) query.setMaxResults(limit).setFirstResult(start).list();
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    @Override
    public Object ejecutarUniqueSQLQuery(String consulta, List<Pair<String, Object>> parametros) {

        SQLQuery query = sessionFactory.getCurrentSession()
                .createSQLQuery(consulta);
        for (Pair<String, Object> parametro : parametros) {
            if (parametro.getB() instanceof String) {
                query.setString(parametro.getA(), (String) parametro.getB());
            } else if (parametro.getB() instanceof Integer) {
                query.setInteger(parametro.getA(), (Integer) parametro.getB());
            } else if (parametro.getB() instanceof Long) {
                query.setLong(parametro.getA(), (Long) parametro.getB());
            } else if (parametro.getB() instanceof Double) {
                query.setDouble(parametro.getA(), (Double) parametro.getB());
            } else if (parametro.getB() instanceof Date) {
                query.setDate(parametro.getA(), (Date) parametro.getB());
            } else if (parametro.getB() instanceof Calendar) {
                query.setCalendar(parametro.getA(), (Calendar) parametro.getB());
            }

        }

        return query.uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    @Override
    public Long cantidadRegistros() throws Exception {
        Object cantidad = null;
        if (extraerClaseDominio().newInstance() instanceof EntidadPerpetua) {
            StringBuilder sb = new StringBuilder("select count(id) from ");// NOPMD - 
            sb.append(extraerClaseDominio().getName());
            sb.append(" entity");// NOPMD - 
            sb.append(" where fechaInactivado is not null ");
            cantidad = sessionFactory.getCurrentSession().createQuery(sb.toString())
                    .uniqueResult();
        } else {
            StringBuilder sb = new StringBuilder("select count(id) from ");
            sb.append(extraerClaseDominio().getName());
            sb.append(" entity");
            cantidad = sessionFactory.getCurrentSession().createQuery(sb.toString())
                    .uniqueResult();
        }

        if (cantidad == null) {
            return 0l;
        } else {
            return Long.parseLong(cantidad + "");
        }

    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    @Override
    public Long cantidadRegistrosInactivos() throws Exception {
        Object cantidad = null;

        StringBuilder sb = new StringBuilder("select count(id) from ");// NOPMD - 
        sb.append(extraerClaseDominio().getName());
        sb.append(" entity");// NOPMD - 
        sb.append(" where fechaInactivado is null ");
        cantidad = sessionFactory.getCurrentSession().createQuery(sb.toString())
                .uniqueResult();

        if (cantidad == null) {
            return 0l;
        } else {
            return Long.parseLong(cantidad + "");
        }

    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    @Override
    public Long cantidadRegistrosActivos() throws Exception {
        Object cantidad = null;

        StringBuilder sb = new StringBuilder("select count(id) from ");// NOPMD - 
        sb.append(extraerClaseDominio().getName());
        sb.append(" entity");// NOPMD - 
        sb.append(" where fechaInactivado is not null ");
        cantidad = sessionFactory.getCurrentSession().createQuery(sb.toString())
                .uniqueResult();

        if (cantidad == null) {
            return 0l;
        } else {
            return Long.parseLong(cantidad + "");
        }

    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    @Override
    public Boolean PGValidarComponente(String nombreComponente) {
        Object obj = sessionFactory.getCurrentSession()
                .createSQLQuery("SELECT cast(to_regclass('" + nombreComponente + "') as text) ")
                //.setString("nombreComponente", nombreComponente) 
                .uniqueResult();

        return obj != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Boolean existe(Long id) {
        Object o = this.getById(id);

        return (o != null);
    }
}
