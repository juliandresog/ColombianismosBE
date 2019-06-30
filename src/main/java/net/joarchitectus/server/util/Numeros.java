/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.joarchitectus.server.util;

/**
 *
 * @author josorio
 */
public class Numeros {

    /**
     * Devuelve un string con el numero con la cantidad de ceros a la izquierda
     * para cumplir con la longitud.
     * @param numero
     * @param longitud
     * @return
     */
    public static String rellenarCeros(int numero, int longitud){
        String digito = numero+"";
        while(digito.length()<longitud){
            digito="0"+digito;
        }
        return digito;
    }

    public static String rellenarCeros(long numero, long longitud){
        String digito = numero+"";
        while(digito.length()<longitud){
            digito="0"+digito;
        }
        return digito;
    }

    /**
     * convierte la cantidad de segundos a un formato hh:mm:ss
     * @param segundos
     * @return 
     */
    public static String segundosAHoras(long segundos){
        boolean positivo = true;
        //Primero determino si es una cifra positiva o negativa
        if(segundos<0){
            positivo = false;
        }
        long horas = 0;
        long minutos = 0;
        //Lo combierto usando valor absoluto a positivo
        segundos = Math.abs(segundos);

        //Obento numero de horas
        if(segundos >= (60*60)){
           long aux = segundos - (segundos%(60*60));
           horas = aux / (60 * 60);
           segundos = (segundos%(60*60));
        }

        //obtengo numero de minutos
        if(segundos >= 60){
            long aux = segundos - (segundos % 60);
            minutos = aux / 60;
            segundos = segundos % 60;
        }


        return ((positivo==false ? "-" : "")+""+rellenarCeros(horas,2) + ":"+rellenarCeros(minutos,2)+":"+rellenarCeros(segundos, 2));
    }
}
