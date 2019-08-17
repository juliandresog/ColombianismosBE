/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.datos.dao.hibernate;

import java.util.List;
import net.joarchitectus.client.datos.dominio.Tipificador;
import net.joarchitectus.server.datos.dao.DaoTipificador;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author josorio
 */
@Repository
public class DaoTipificadorImpl extends DaoGenericoImpl<Tipificador> implements DaoTipificador {

    @Transactional(readOnly = true)
    @Override
    public List<Tipificador> buscarRegistros(List<Long> ids) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Tipificador where id in (:ids)")
                .setParameterList("ids", ids)
                .list();
    }
    
}
