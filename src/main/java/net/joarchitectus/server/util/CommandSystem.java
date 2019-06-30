/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author josorio
 */
public class CommandSystem {
    //Esta funcion devuelve un proceso que es la ejecucion del comando
    //que se le pasa como argumento con el nombre c

    private Process comando(String c) throws IOException {
        return Runtime.getRuntime().exec(c);
    }

    //Un lector de la salida que provoca el comando
    private BufferedReader salidaComando(Process p) {
        return new BufferedReader(new InputStreamReader(p.getInputStream()));
    }

    //Un lector de los errores que pudieron cometerse al ejecutar el comando
    private BufferedReader errorComando(Process p) {
        return new BufferedReader(new InputStreamReader(p.getErrorStream()));
    }

    //Tranforma los Buffers en texto legible
    private String leerBufer(BufferedReader b) throws IOException {
        String aux = "", aux2 = "";
        while ((aux2 = b.readLine()) != null) {
            aux += String.format(" %s \n", aux2);
        }
        if (aux == "") {
            return "SIN ERRORES!";
        }
        return aux;
    }

    /**
     * Crea un backup de la base de datos y retorna la ruta donde se genero!
     * @return
     * @throws IOException
     */
    public String backupbd() throws IOException {            
            Date fecha = new Date();
            String fileName = ""+fecha.getTime();
            //fileName="";

            String path = "/tmp/boos_"+fileName+".sql";
            String zip = "/tmp/boos_"+fileName+".sql.zip";
//            Runtime r;// = Runtime.getRuntime();

            //PostgreSQL variables
            String user = "boos_web";
            String dbase = "boos_user";
            String password = "fkdjdsdffa3g_dShIqA52";
            Process p;
            ProcessBuilder pb;

            /**
             * Ejecucion del proceso de respaldo
             */
//            r = Runtime.getRuntime();
            System.out.println("Se ejecuta un backup de bd");
            pb = new ProcessBuilder("pg_dump", "-f", path, "-U", user, dbase);
            pb.environment().put("PGPASSWORD", password);
            pb.redirectErrorStream(true);
            p = pb.start();
            System.out.println("Salida comando:");
            System.out.println(leerBufer(this.salidaComando(p)));
            System.out.println("Errores comando:");
            System.out.println(leerBufer(this.errorComando(p)));

            Process p2 = this.comando("zip -r "+zip+" "+path+"");
            System.out.println("Salida comando:");
            System.out.println(this.leerBufer(this.salidaComando(p2)));
            System.out.println("Errores comando:");
            System.out.println(this.leerBufer(this.errorComando(p2)));

            

            return zip;

    }
    /**
     * Borra el archivo del servidor
     * @param archivo
     * @throws IOException
     */
    public void borrararchivobkp(String archivo) throws IOException{
        Process p2 = this.comando("rm "+archivo);
        System.out.println("Salida comando:");
        System.out.println(this.leerBufer(this.salidaComando(p2)));
        System.out.println("Errores comando:");
        System.out.println(this.leerBufer(this.errorComando(p2)));
    }

    public static void main(String[] arg) {
        CommandSystem ejecutor = new CommandSystem();

        try {
            
            ejecutor.backupbd();

           
        } catch (IOException e) {
            System.out.println("No se ejecuto correctamente por las sgtes razones: ");
            e.printStackTrace();
            System.exit(0);
        } catch (Exception e){
            System.out.println("No se ejecuto correctamente: ");
            e.printStackTrace();
            System.exit(0);
        }

    }
}
