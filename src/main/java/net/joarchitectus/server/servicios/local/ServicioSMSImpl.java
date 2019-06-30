/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.servicios.local;

import net.joarchitectus.server.servicios.ServicioSMS;
import java.io.IOException;
import java.util.HashMap;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 *
 * @author jdbotero
 */
public class ServicioSMSImpl implements ServicioSMS
{
    /** Textos de los mensajes */
    private HashMap<Integer, String> mensajesEventos;
    private String remitente;
    private String urlBase;
    private String paramUsuarioNombre;
    private String paramUsuarioValor;
    private String paramClaveNombre;
    private String paramClaveValor;
    private String paramDeNombre;
    private String paramParaNombre;
    private String prefijo;
    private String paramMensajeNombre;
    

    /** @{inheritDoc} */
    public String[] enviarSMS(String mensaje, String destinatario) throws IOException
    {
        String[] retorno = new String[2];

        HttpClient cliente = new HttpClient();
        HttpMethod metodo = new GetMethod( urlBase );

        // Definimos parámetros
        NameValuePair nvp1 = new NameValuePair( paramUsuarioNombre,
                paramUsuarioValor );
        NameValuePair nvp2 = new NameValuePair( paramClaveNombre, paramClaveValor );
        NameValuePair nvp3 = new NameValuePair( paramDeNombre, remitente );
        NameValuePair nvp4 = new NameValuePair( paramParaNombre, prefijo + destinatario );
        NameValuePair nvp5 = new NameValuePair( paramMensajeNombre, mensaje );

        metodo.setQueryString( new NameValuePair[]
                {
                    nvp1, nvp2, nvp3, nvp4, nvp5
                });
//        String query = metodo.getQueryString();
        int statusCode = cliente.executeMethod(metodo);

        retorno[0] = HttpStatus.getStatusText(statusCode);
        retorno[1] = metodo.getResponseBodyAsString();

        // Libero la conexión
        metodo.releaseConnection();

        return retorno;
    }

    public String getParamClaveNombre()
    {
        return paramClaveNombre;
    }

    public void setParamClaveNombre(String paramClaveNombre)
    {
        this.paramClaveNombre = paramClaveNombre;
    }

    public String getParamClaveValor()
    {
        return paramClaveValor;
    }

    public void setParamClaveValor(String paramClaveValor)
    {
        this.paramClaveValor = paramClaveValor;
    }

    public String getParamDeNombre()
    {
        return paramDeNombre;
    }

    public void setParamDeNombre(String paramDeNombre)
    {
        this.paramDeNombre = paramDeNombre;
    }

    public String getParamMensajeNombre()
    {
        return paramMensajeNombre;
    }

    public void setParamMensajeNombre(String paramMensajeNombre)
    {
        this.paramMensajeNombre = paramMensajeNombre;
    }

    public String getParamParaNombre()
    {
        return paramParaNombre;
    }

    public void setParamParaNombre(String paramParaNombre)
    {
        this.paramParaNombre = paramParaNombre;
    }

    public String getParamUsuarioNombre()
    {
        return paramUsuarioNombre;
    }

    public void setParamUsuarioNombre(String paramUsuarioNombre)
    {
        this.paramUsuarioNombre = paramUsuarioNombre;
    }

    public String getParamUsuarioValor()
    {
        return paramUsuarioValor;
    }

    public void setParamUsuarioValor(String paramUsuarioValor)
    {
        this.paramUsuarioValor = paramUsuarioValor;
    }

    public String getPrefijo()
    {
        return prefijo;
    }

    public void setPrefijo(String prefijo)
    {
        this.prefijo = prefijo;
    }

    public String getUrlBase()
    {
        return urlBase;
    }

    public void setUrlBase(String urlBase)
    {
        this.urlBase = urlBase;
    }

    public HashMap<Integer, String> getMensajesEventos()
    {
        return mensajesEventos;
    }

    public void setMensajesEventos(HashMap<Integer, String> mensajesEventos)
    {
        this.mensajesEventos = mensajesEventos;
    }

    public String getRemitente()
    {
        return remitente;
    }

    public void setRemitente(String remitente)
    {
        this.remitente = remitente;
    }
}
