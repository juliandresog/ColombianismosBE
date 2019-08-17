/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.servicios.maestro;

import net.joarchitectus.client.datos.dominio.Definicion;
import net.joarchitectus.client.datos.model.DefinicionModel;
import net.joarchitectus.client.entidades.RespuestaRPC;

/**
 *
 * @author josorio
 */
public interface ServicioDefinicion extends ServicioMaestro<Definicion>{

    /**
     * Guardar definicion en BD
     * @param model
     * @return 
     */
    public RespuestaRPC<Definicion> guardarDefinicion(DefinicionModel model);
    
}
