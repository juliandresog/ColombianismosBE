/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.vista.rest.api;

import net.joarchitectus.client.datos.dominio.Tipificador;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.server.servicios.maestro.ServicioTipificador;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author josorio
 */
@RestController
@RequestMapping("/admin/tipificador")
public class TipificadorController extends MaestroRestController<Tipificador> {

    /* Un comentario sobre la implementacion de esta clase podria ir aqui. */
    private ServicioTipificador servicioTipificador;

    private Usuarios usuarioSession;

    protected static org.slf4j.Logger log2 = LoggerFactory.getLogger(TipificadorController.class);

    @Autowired
    public void setServicioAdminTipificador(ServicioTipificador servicioTipificador) {
        this.servicioTipificador = servicioTipificador;
        super.setServicioMaestro(servicioTipificador);
    }

    @Autowired
    @Override
    public void setUsuarioSession(Usuarios usuarioSession) {
        this.usuarioSession = usuarioSession;
        super.setUsuarioSession(usuarioSession);
    }
    
    /**
     * Constructor Acá puedo cambiar variables si lo necesito como "campoFiltro"
     */
    public TipificadorController() {
        log2.debug("Se construye {}", this.getClass().getSimpleName());
        //campoFiltro = "nombre"; es el mismo de pordefecto
    }
    
    
}
