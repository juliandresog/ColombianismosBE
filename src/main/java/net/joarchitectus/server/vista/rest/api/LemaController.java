/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.vista.rest.api;

import net.joarchitectus.client.datos.dominio.Lema;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.server.servicios.maestro.ServicioLema;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author josorio
 */
@RestController
@RequestMapping("/admin/lema")
public class LemaController extends MaestroRestController<Lema> {

    /* Un comentario sobre la implementacion de esta clase podria ir aqui. */
    private ServicioLema servicioLema;

    private Usuarios usuarioSession;

    protected static org.slf4j.Logger log2 = LoggerFactory.getLogger(LemaController.class);

    @Autowired
    public void setServicioAdminLema(ServicioLema servicioLema) {
        this.servicioLema = servicioLema;
        super.setServicioMaestro(servicioLema);
    }

    @Autowired
    @Override
    public void setUsuarioSession(Usuarios usuarioSession) {
        this.usuarioSession = usuarioSession;
        super.setUsuarioSession(usuarioSession);
    }
    
}
