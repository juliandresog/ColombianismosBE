/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.joarchitectus.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jdbotero@gmail.com
 */
public class Formatos 
{
    /**
     * Genera un formato de hora con presentación humana a partir de un Date
     * @param fecha
     * @return
     */
    public static String fechaHora( Date fecha )
    {
        if( fecha == null )
        {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy hh:mm aaa" );
        return sdf.format( fecha );
    }

    /**
     * Genera un date a partir de una hora expresada en un string con el formato
     * hhmmssDDMMAA
     * @param fecha
     * @return
     * @throws ParseException
     */
    public static Date fechaHora( String fecha ) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat( "HHmmssddMMyy" );
        Date ret = sdf.parse( fecha );
        return ret;
    }

    /**
     * Genera un date a partir de una hora expresada en un string con el formato
     * DDMMAA
     * @param fecha
     * @return
     * @throws ParseException
     */
    public static Date fecha( String fecha ) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat( "ddMMyy" );
        return sdf.parse( fecha );
    }

    /**
     * Genera un date a partir de una hora expresada en un string con el formato
     * DDMMAA
     * @param fecha
     * @return
     * @throws ParseException
     */
    public static Date fecha2( String fecha ) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        return sdf.parse( fecha );
    }

    /**
     * Genera un String con el formato DDMMAA a partir de un Date
     * @param fecha
     * @return
     */
    public static String fecha( Date fecha )
    {
        if( fecha == null )
        {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat( "ddMMyy" );
        return sdf.format( fecha );
    }

    /**
     * Genera un String con el formato DDMMAA a partir de un Date
     * @param fecha dd-MM-yyyy
     * @return
     */
    public static String fecha2( Date fecha )
    {
        if( fecha == null )
        {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );
        return sdf.format( fecha );
    }

    /**
     * Genera un String con el formato HHMMSS a partir de un date
     * @param date
     * @return
     */
    public static String hora( Date date )
    {
        if( date == null )
        {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat( "HHmmss" );
        return sdf.format( date );
    }
    
    /**
     * Genera un String con el formato hh:mm aaa a partir de un date
     * @param date hh:mm aaa
     * @return
     */
    public static String hora2( Date date )
    {
        if( date == null )
        {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat( "hh:mm aaa" );
        return sdf.format( date );
    }

    /**
     * Genera un Date a partir de un String con el formato HHMMSS
     * @param fecha
     * @return
     * @throws ParseException 
     */
    public static Date hora( String fecha ) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat( "hh:mm aaa" );
        return sdf.parse( fecha );
    }

    /**
     * Genera un formato de hora con presentación humana a partir de un Date
     * @param fecha dd/MM/yyyy kk:mm:ss
     * @return
     */
    public static String fechaHoraMilitar( Date fecha )
    {
        if( fecha == null )
        {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
        return sdf.format( fecha );
    }
    /**
     * Genera un formato de hora con presentación humana a partir de un Date
     * @param fecha ddMMyyyykkmmss
     * @return
     */
    public static String fechaHoraMilitar2( Date fecha )
    {
        if( fecha == null )
        {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat( "ddMMyyyykkmmss" );
        return sdf.format( fecha );
    }

    /**
     * Genera un formato de hora con presentación humana a partir de un Date
     * @param fecha yyyy-MM-dd kk:mm:ss
     * @return
     */
    public static String fechaHoraMilitar3( Date fecha )
    {
        if( fecha == null )
        {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        return sdf.format( fecha );
    }
    
       /**
     * Retorna un string descriptivo de la fecha y hora dados como parámetro en el
     * formato HH:mm dd-MM-yyyy
     * @param fecha
     * @return
     */
    public static String fechaHora3( Date fecha )
    {
        SimpleDateFormat fmt = new SimpleDateFormat( "hh:mm a dd-MM-yyyy");
        return fmt.format( fecha );
    }
    
    /**
     * Genera un date a partir de una hora expresada en un string con el formato
     * yyyy-MM-dd
     * @param fecha
     * @return
     * @throws ParseException
     */
    public static Date parseFecha(String fecha) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date ret = sdf.parse(fecha);
        return ret;
    }
    
    /**
     * Retorna un string descriptivo de la fecha y hora dados como parámetro en el
     * formato yyyy-MM-dd
     *
     * @param fecha - fecha tipo date
     * @return
     */
    public static String fechaHoraSimple(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
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
    
    public static String rellenarCeros(long numero, long longitud){
        String digito = numero+"";
        while(digito.length()<longitud){
            digito="0"+digito;
        }
        return digito;
    }

    /**
     * Retorna un string descriptivo de la fecha y hora dados como parámetro en el
     * formato yyyy_MM_dd
     *
     * @param fecha - fecha tipo date
     * @return
     */
    public static String fechaHoraSimple2(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
        return sdf.format(date);
    }
    
    /**
     * Genera un date a partir de una hora expresada en un string con el formato
     * yyyy-MM-dd kk:mm
     * @param fecha
     * @return
     * @throws ParseException
     */
    public static Date parseFecha2(String fecha) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm");
        Date ret = sdf.parse(fecha);
        return ret;
    }
    
    /**
     * Genera un date a partir de una hora expresada en un string con el formato
     * yyyy-MM-dd kk:mm
     * @param fecha
     * @return
     * @throws ParseException
     */
    public static Date parseHoraMilitar(String fecha) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
        Date ret = sdf.parse(fecha);
        return ret;
    }
}
