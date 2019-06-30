/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.joarchitectus.server.datos.dao;

import net.joarchitectus.client.datos.dominio.Rol;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author agomez
 */
public interface DaoRol extends DaoGenerico<Rol> {

    public List<Rol> getActivos() throws DataAccessException;
}
