/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.joarchitectus.server.excepciones;

/**
 *
 * @author jdbotero
 */
public class ExcepcionMensajesInsuficientes extends Exception
{
    public ExcepcionMensajesInsuficientes()
    {
        super( "El saldo es insuficiente para enviar el mensaje" );
    }

}
