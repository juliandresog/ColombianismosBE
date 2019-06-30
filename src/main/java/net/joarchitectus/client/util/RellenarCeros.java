/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.client.util;

/**
 *
 * @author agomez
 */
public class RellenarCeros {

    public static String rellenar(String cadena, int longitud) {
        if (cadena.length() < longitud) {
            StringBuffer res = new StringBuffer();
            for (int i = 0; i < (longitud - cadena.trim().length()); i++) {
                ;
                res.append("0");
            }
            res.append(cadena);
            cadena = res.toString();
        }

        return cadena;
    }
}
