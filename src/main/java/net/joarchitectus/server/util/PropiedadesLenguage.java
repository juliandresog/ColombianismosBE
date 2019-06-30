/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.util;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author josorio
 */
public class PropiedadesLenguage {

    static public final Locale SPAIN = new Locale("es");

    /**
     * Obtengo el texto en español o el idioma del cliente, segun la clave
     * enviada, el texto se tomará de acuerdo al lenguage de la empresa. Primero
     * reviso en las definiciones del cliente y luego reviso en el i18n gral.
     *
     * @param key
     * @return String
     */
    public static String getValueES(String key) {
        return texto(SPAIN, key);
    }

    /**
     * Obtiene el valor de la clave segun el lenguela definido en locale
     *
     * @param locale
     * @param clave
     * @return String
     * @throws Exception
     */
    public static String texto(Locale locale, String clave) {
        if (locale == null) {
            locale = SPAIN;
        }
        try {

            ResourceBundle messages;
            messages
                    = ResourceBundle.getBundle("net.joarchitectus/client/aplI18n/Main", locale);

            return messages.getString(clave);

        } catch (Exception ex) {
            Logger.getLogger(PropiedadesLenguage.class.getName()).log(Level.ERROR, null, ex);
            return null;
        }
    }
    
    /**
     * Obtiene el valor de la clave segun el lenguela definido en locale; ademas
     * se recibe unos parametros que corresponde a los comodines definidos en el
     * texto de la forma (hola senor <1> bienvenido)<br>
     * Ejemplo:<br>
     * Map<Integer, String> parametros = new HashMap<Integer, String>();
     * parametros.put(1, varialbe+ ""); parametros.put(2,"valor"));
     *
     * getWindow().showNotification(I18NUtil.texto(getLocale(), "nombreclave",
     * parametros), Notification.TYPE_ERROR_MESSAGE);
     *
     * @param locale si null por defecto español
     * @param clave
     * @param parametros
     * @return String
     */
    public static String texto(Locale locale, String clave, Map<Integer, String> parametros) {
        if (locale == null) {
            locale = SPAIN;
        }
        try {
            ResourceBundle messages;
            messages
                    = ResourceBundle.getBundle("net.joarchitectus/client/aplI18n/Main", locale);

            return reemplazarComodines(parametros, messages.getString(clave));

        } catch (Exception ex) {
            Logger.getLogger(PropiedadesLenguage.class.getName()).log(Level.ERROR, null, ex);
            return null;
        }
    }

    /**
     * Reemplazo los comodines estilo <1><2> .... segun los parametros enviados
     *
     * @param parametros
     * @param texto
     * @return String
     */
    private static String reemplazarComodines(Map<Integer, String> parametros, String texto) {
        Iterator iterator = parametros.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> me = (Map.Entry<Integer, String>) iterator.next();
            String valor = me.getValue();
            Integer clave = me.getKey();
            String comodin = "<" + clave + ">";
            texto = texto.replaceAll(comodin, valor);
        }
        return texto;
    }
}
