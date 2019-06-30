package net.joarchitectus.client.util;

/**
 * Plantillas para las diferentes presentaciones del sistema
 * @author Juan David Botero <jdbotero@gmail.com>
 */
public class Plantillas 
{
    /**
     * Plantilla para las ayudas en línea
     * @param titulo
     * @param cuerpo
     * @return html para ser dibujado
     */
    public static String ToolTips( String titulo, String cuerpo )
    {
        StringBuffer retorno = new StringBuffer();
        
        retorno.append( "<div class='ayudasEnLinea'>" );
            retorno.append( "<img class='iconoAyudaTooltip' src='imagenes/iconos/iconoAyuda2.png'/>" );
            retorno.append( "<h1>" );
                retorno.append( titulo );
            retorno.append( "</h1>" );
            retorno.append( cuerpo );
        retorno.append( "</div>" );
        
        return retorno.toString();
    }
}
