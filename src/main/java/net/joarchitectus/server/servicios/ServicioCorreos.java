/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.servicios;
;
import java.io.File;
import javax.mail.MessagingException;

public interface ServicioCorreos
{
    /**
     * Envía un correo general a partir de los parámetros dados
     * @param destinatario
     * @param asunto
     * @param mensajePlano
     * @param mensajeHTML
     * @param nombreAdjunto
     * @param archivoAdjunto
     * @throws MessagingException
     * @throws Exception
     */
    public void enviarCorreo(String destinatario, String asunto,
            String mensajePlano, String mensajeHTML, String nombreAdjunto,
            File archivoAdjunto)
            throws MessagingException, Exception;


}
