/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * ServicioLema Versión 1.0 12/07/2019
 *
 **/

package net.joarchitectus.server.servicios.maestro;

import java.util.Map;
import net.joarchitectus.client.datos.dominio.Lema;

/**
 * La descripcion de la clase va aqui.
 * @version     1.0 12/07/2019
 * @author josorio
 */
public interface ServicioLema extends ServicioMaestro<Lema>{

    /**
     * Busca los sinonimos de un lema y los retorna en un mapa
     * @param idLema
     * @return 
     */
    public Map buscarSinonimos(Long idLema);

}
