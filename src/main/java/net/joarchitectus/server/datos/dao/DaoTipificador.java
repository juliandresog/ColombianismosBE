/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.datos.dao;

import java.util.List;
import net.joarchitectus.client.datos.dominio.Tipificador;

/**
 *
 * @author josorio
 */
public interface DaoTipificador extends DaoGenerico<Tipificador> {

    /**
     * Retorna registros segun sus ids
     * @param ids
     * @return 
     */
    public List<Tipificador> buscarRegistros(List<Long> ids);
    
}
