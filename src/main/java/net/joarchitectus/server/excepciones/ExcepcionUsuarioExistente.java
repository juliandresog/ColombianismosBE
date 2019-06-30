/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.excepciones;

/**
 *
 * @author Juan David Botero <jdbotero@gmail.com>
 */
public class ExcepcionUsuarioExistente extends Exception
{

    public static final int DOCUMENTO_EXISTENTE = 1;
    public static final int USUARIO_EXISTENTE = 2;
    private int tipoExistencia;

    public ExcepcionUsuarioExistente( int tipoExistencia )
    {
        this.tipoExistencia = tipoExistencia;
    }

    public int getTipoExistencia()
    {
        return tipoExistencia;
    }

    public void setTipoExistencia( int tipoExistencia )
    {
        this.tipoExistencia = tipoExistencia;
    }
}
