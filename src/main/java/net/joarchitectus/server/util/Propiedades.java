/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.util;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author jdbotero@gmail.com
 */
public class Propiedades {

    /**
     * Obtiene valor del properties.
     * @param key
     * @return 
     */
    public static String getValue(String key) {
        String retorno = null;

        Properties properties = new Properties();
        try {
            properties.load(Propiedades.class
                    .getResourceAsStream("/application.properties"));
            retorno = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retorno;
    }
}
