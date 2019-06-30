/**
* Orden
* Versión 1.0
* 15/09/2013
*
* Copyright(c) 2007-2012, Boos IT.
* admin@boos.com.co
*
* http://boos.com.co/license
**/

package net.joarchitectus.client.util.consulta;

import java.io.Serializable;

/**
 *
 * @author josorio
 */
public enum Orden implements Serializable{
    ASC("Asc"), DESC("Desc");
    
    private String label;
    
    private Orden(String label) {
        this.label= label;
    }
    
    @Override
    public String toString() {
        return this.label;
    }
}
