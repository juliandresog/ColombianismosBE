/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.datos.dao;

import java.util.List;
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

    /**
     * IDs de lemas asociadas a la definicion
     * @param idDefinicion
     * @return 
     */
    public List<Long> getIDsLemas(Long idDefinicion);

    /**
     * IDs de marcas gramaticales asociadas a la definicion
     * @param idDefinicion
     * @return 
     */
    public List<Long> getIDsMarcasGramaticales(Long idDefinicion);

    /**
     * IDs de marcas de uso asociadas a la definicion
     * @param idDefinicion
     * @return 
     */
    public List<Long> getIDsMarcasUso(Long idDefinicion);

    /**
     * IDs de marcas regionales asociadas a la definicion
     * @param idDefinicion
     * @return 
     */
    public List<Long> getIDsMarcasRegionales(Long idDefinicion);
    
}
