/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.vista.rest.api;

import net.joarchitectus.client.datos.dominio.Definicion;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.server.servicios.maestro.ServicioDefinicion;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author josorio
 */
@RestController
@RequestMapping("/admin/definicion")
public class DefinicionController extends MaestroRestController<Definicion> {

    /* Un comentario sobre la implementacion de esta clase podria ir aqui. */
    private ServicioDefinicion servicioDefinicion;

    private Usuarios usuarioSession;

    protected static org.slf4j.Logger log2 = LoggerFactory.getLogger(DefinicionController.class);

    @Autowired
    public void setServicioAdminDefinicion(ServicioDefinicion servicioDefinicion) {
        this.servicioDefinicion = servicioDefinicion;
        super.setServicioMaestro(servicioDefinicion);
    }

    @Autowired
    @Override
    public void setUsuarioSession(Usuarios usuarioSession) {
        this.usuarioSession = usuarioSession;
        super.setUsuarioSession(usuarioSession);
    }
    
}
