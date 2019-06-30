/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.servicios.local;

import net.joarchitectus.server.servicios.ServicioCorreos;
import java.io.File;
import java.util.HashMap;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 *
 * @author ANDRES
 */
public class ServicioCorreosImpl implements ServicioCorreos
{
    /** Textos de los mensajes */
    private HashMap<Integer, String> mensajesEventos;
    /** Objeto para el envio de correos */
    private JavaMailSender emisorCorreos;
    /** Objeto para personalizar el correo */
    private SimpleMailMessage mailMessage;
    /** email del usuario que envia */
    private String remitente;
    /** Mensaje de confirmación de premio raspa en html */
    private String urlLogo;
   

    /** @{inheritDoc} */
    public void enviarCorreo(final String destinatario, final String asunto,
            final String mensajePlano, String mensajeHTML,
            final String nombreAdjunto, final File archivoAdjunto)
            throws MessagingException
    {
        // Añado siempre el logo al final de los mensajes
        final String mensajeHTML2 = mensajeHTML + "<br/><br/><img src='" + urlLogo + "'>";

        // Preparo el mensaje
        final MimeMessagePreparator preparador = new MimeMessagePreparator()
        {

            public void prepare(MimeMessage mimeMessage) throws MessagingException
            {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setFrom(remitente);
                message.setTo(new InternetAddress(destinatario));
                message.setSubject(asunto);
                message.setText(mensajePlano, mensajeHTML2);
                // Si hay adjunto
                if (nombreAdjunto != null && !nombreAdjunto.isEmpty()
                        && archivoAdjunto != null)
                {
                    message.addAttachment(nombreAdjunto, archivoAdjunto);
                }
            }
        };

        // Envío el mail en otro hilo para evitar bloqueos en la transacción
        new Thread(new Runnable()
        {

            public void run()
            {
                try
                {
                    emisorCorreos.send(preparador);
                } catch (MailException ex)
                {
                    System.out.println("Error al enviar correo electronico" + ex.getMessage());
                    return;
                }
            }
        }).start();
    }

    public JavaMailSender getEmisorCorreos()
    {
        return emisorCorreos;
    }

    public void setEmisorCorreos(JavaMailSender emisorCorreos)
    {
        this.emisorCorreos = emisorCorreos;
    }

    public SimpleMailMessage getMailMessage()
    {
        return mailMessage;
    }

    public void setMailMessage(SimpleMailMessage mailMessage)
    {
        this.mailMessage = mailMessage;
    }

    public String getRemitente()
    {
        return remitente;
    }

    public void setRemitente(String remitente)
    {
        this.remitente = remitente;
    }

    public String getUrlLogo()
    {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo)
    {
        this.urlLogo = urlLogo;
    }

    public HashMap<Integer, String> getMensajesEventos()
    {
        return mensajesEventos;
    }

    public void setMensajesEventos(HashMap<Integer, String> mensajesEventos)
    {
        this.mensajesEventos = mensajesEventos;
    }
}

