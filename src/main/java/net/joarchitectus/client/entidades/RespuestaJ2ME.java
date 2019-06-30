package net.joarchitectus.client.entidades;

/**
 *
 * @param <T> 
 * @author jdbotero
 */
public class RespuestaJ2ME<T /*extends Serializable*/> /*implements Serializable*/
{
    public final static int RESULTADO_OK = 0;
    public final static int RESULTADO_EXCEPCION_SERVER = 1;
    public final static int RESULTADO_FALLO = 2;
    public final static int RESULTADO_FALLO_PERMISOS_INSUFICIENTES = 3;
    public final static int RESULTADO_FALLO_NO_SESSION = 4;

    /** Estado de resultado de la respuesta */
    private int resultado;
    /** Descripción error */
    private String descripcionError;
    /** Objeto de respuesta */
    private T contenido;

    /**
     * Según el tipo de error reportado construye un string descriptivo del mismo
     * @return
     */
    private void construirDescripcionError()
    {
        // Si la tenemos la descripción del error no hacer nada
        if( descripcionError != null && !descripcionError.isEmpty() )
        {
            return;
        }
        switch( resultado )
        {

            case RESULTADO_EXCEPCION_SERVER: descripcionError = "Ocurrió un error inesperado en el servidor, por favor repórtelo a la línea de soporte de Sistema, ofrecemos disculpas por los inconvenientes ocasionados";
                                             break;
            case RESULTADO_FALLO: descripcionError = "Ocurrió un error inesperado en el servidor, por favor repórtelo a la línea de soporte de Sistema, ofrecemos disculpas por los inconvenientes ocasionados";
                                             break;
            case RESULTADO_FALLO_PERMISOS_INSUFICIENTES: descripcionError = "Usted no posee los privilegios necesarios para realizar esta acción";
                                             break;
            case RESULTADO_FALLO_NO_SESSION: descripcionError = "Al parecer lleva mucho tiempo sin interactuar con el sistema y su sesión ha caducado, por favor salga e ingrese de nuevo";
                                             break;
            default: descripcionError = "Desconocido";
        }
    }

    public RespuestaJ2ME()
    {
    }

    public String getDescripcionError()
    {
        return descripcionError;
    }

    public void setDescripcionError( String descripcionError )
    {
        this.descripcionError = descripcionError;
    }

    public T getContenido()
    {
        return contenido;
    }

    public void setContenido( T contenido )
    {
        this.contenido = contenido;
    }

    public int getResultado()
    {
        return resultado;
    }

    public void setResultado( int resultado )
    {
        this.resultado = resultado;
        construirDescripcionError();
    }
}
