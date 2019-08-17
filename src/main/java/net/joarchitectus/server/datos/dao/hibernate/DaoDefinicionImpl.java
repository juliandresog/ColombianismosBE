/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.datos.dao.hibernate;

import net.joarchitectus.client.datos.dominio.Definicion;
import net.joarchitectus.client.datos.dominio.LemaDefinicion;
import net.joarchitectus.client.datos.dominio.TipificadorDefinicion;
import net.joarchitectus.server.datos.dao.DaoDefinicion;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author josorio
 */
@Repository
public class DaoDefinicionImpl extends DaoGenericoImpl<Definicion> implements DaoDefinicion {

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void guardar(LemaDefinicion lemaDef) {
        //Procedo a guardar en BD
        sessionFactory.getCurrentSession().saveOrUpdate(lemaDef);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void guardar(TipificadorDefinicion tipDef) {
        //Procedo a guardar en BD
        sessionFactory.getCurrentSession().saveOrUpdate(tipDef);
    }
    
}
