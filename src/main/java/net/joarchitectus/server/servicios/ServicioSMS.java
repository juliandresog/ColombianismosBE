/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.joarchitectus.server.servicios;

import java.io.IOException;

/**
 *
 * @author jdbotero
 */
public interface ServicioSMS
{  

    /**
     * Envía un mensaje SMS a través del protocolo http
     * @param mensaje
     * @param remitente
     * @param destinatario
     * @return Un arreglo de String de 2 posiciones que contiene en la primera
     * el status de respuesta y en la segunda el cuerpo de la respuesta
     * @throws IOException
     */
    public String[] enviarSMS( String mensaje, String destinatario ) throws IOException;
}


