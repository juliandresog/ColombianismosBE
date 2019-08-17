/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.datos.dao;

import net.joarchitectus.client.datos.dominio.Definicion;
import net.joarchitectus.client.datos.dominio.LemaDefinicion;
import net.joarchitectus.client.datos.dominio.TipificadorDefinicion;

/**
 *
 * @author josorio
 */
public interface DaoDefinicion extends DaoGenerico<Definicion> {

    /**
     * Guardar relacion lema y definicion
     * @param lemaDef 
     */
    public void guardar(LemaDefinicion lemaDef);

    /**
     * Guardar relacion tipificador y definicion
     * @param tipDef 
     */
    public void guardar(TipificadorDefinicion tipDef);
    
}
