/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.joarchitectus.server.datos.dao.hibernate;

import net.joarchitectus.client.datos.dominio.Rol;
import net.joarchitectus.server.datos.dao.DaoRol;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author agomez
 */
@Repository
public class DaoRolImpl extends DaoGenericoImpl<Rol> implements DaoRol {

    /** {@inheritdoc } **/
    @Transactional(readOnly=true)
    public List<Rol> getActivos() throws DataAccessException {
        List<Rol> retorno = null;

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Rol.class);
        criteria.add(Restrictions.isNull("fechaInactivado"));
        retorno = (List<Rol>) criteria.list();

        return retorno;
    }
}
