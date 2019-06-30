/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * ModeloConverter Versión 1.0 19-sep-2017
 *
 * Copyright(c) 2007-2017, Boos IT.
 * admin@boos.com.co
 *
 * http://boos.com.co/license
 */

package net.joarchitectus.server.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.joarchitectus.client.datos.dominio.Empresa;
import net.joarchitectus.client.datos.dominio.EntidadBase;
import net.joarchitectus.client.datos.model.EmpresaModel;

import org.slf4j.LoggerFactory;

/**
 * Esta clase es capaz de copiar los datos de un objeto a otro en tanto tengan datos comunes y del mismo tipo.
 * @version 	1.0 19-sep-2017
 * @author josorio
 */
public class ModeloConverter {

    protected static org.slf4j.Logger log = LoggerFactory.getLogger(ModeloConverter.class);
    
    /**
     * Para convertir una entidad en su modelo usando Java Reflexivo; asumiendo que los métodos van a tener los mismos nombres y tipos.
     * @param entidad
     * @param modelo
     * @param reemplazarANulos
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public static Object convertirEntidadToModelo(EntidadBase entidad, Object modelo, boolean reemplazarANulos) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method[] methods = entidad.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                Class[] cArg = new Class[1];
                cArg[0] = method.getReturnType();

                try {
                    Method methodset = modelo.getClass().getMethod(method.getName().replace("get", "set"), cArg);
                    //si los metodos son del mismo tipo los llamo
                    if (method.getReturnType().getName().equals(method.getReturnType().getName())) {
                        Object getValue = method.invoke(entidad);
                        if (reemplazarANulos || getValue != null) {
                            methodset.invoke(modelo, getValue);
                        }
                    }
                } catch (NoSuchMethodException ex) {
                    log.info(ex.getMessage()); 
                }
            }
        }

        return modelo;
    }
    
    /**
     * Para convertir un modelo en su entidad usando Java Reflexivo; asumiendo que los métodos van a tener los mismos nombres y tipos.
     * @param entidad
     * @param modelo
     * @param reemplazarANulos
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public static EntidadBase convertirModeloToEntidad(EntidadBase entidad, Object modelo, boolean reemplazarANulos) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method[] methods = modelo.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                Class[] cArg = new Class[1];
                cArg[0] = method.getReturnType();

                try {
                    Method methodset = entidad.getClass().getMethod(method.getName().replace("get", "set"), cArg);
                    //si los metodos son del mismo tipo los llamo
                    if (method.getReturnType().getName().equals(method.getReturnType().getName())) {
                        Object getValue = method.invoke(modelo);
                        if (reemplazarANulos || getValue != null) {
                            methodset.invoke(entidad, getValue);
                        }
                    }
                } catch (NoSuchMethodException ex) {
                    log.info(ex.getMessage()); 
                }
            }
        }

        return entidad;
    }
    
//    /**
//     * Para convertir un modelo en su entidad usando Java Reflexivo; asumiendo que los métodos van a tener los mismos nombres y tipos.
//     * @param entidad
//     * @param modelo
//     * @param reemplazarANulos
//     * @return
//     * @throws IllegalAccessException
//     * @throws IllegalArgumentException
//     * @throws InvocationTargetException 
//     */
//    public static Usuarios convertirModeloToEntidadUsuario(Usuarios entidad, UsuarioModel modelo, boolean reemplazarANulos) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//        entidad = (Usuarios)ModeloConverter.convertirModeloToEntidad(entidad, modelo, reemplazarANulos);
//        if (reemplazarANulos || modelo.getRol() != null) {
//            entidad.setRol(modelo.getRol() != null ? new Rol(modelo.getRol()) : null);
//        }
//        if (reemplazarANulos || modelo.getEmpresa() != null) {
//            entidad.setEmpresa(modelo.getEmpresa() !=null ? new Empresa(modelo.getEmpresa()) : null );
//        }
//        if (reemplazarANulos || modelo.getPais() != null) {
//            entidad.setPais(modelo.getPais() != null ? new Pais(modelo.getPais()) : null);
//        }
//        
//        return entidad;
//    }

    /**
     * Para convertir un modelo en su entidad usando Java Reflexivo; asumiendo que los métodos van a tener los mismos nombres y tipos.
     * @param entidad
     * @param modelo
     * @param reemplazarANulos
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public static Empresa convertirModeloToEntidadEmpresa(Empresa entidad, EmpresaModel modelo, boolean reemplazarANulos) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        entidad = (Empresa)ModeloConverter.convertirModeloToEntidad(entidad, modelo, reemplazarANulos);
        
//        if (reemplazarANulos || modelo.getCiudad()!= null) {
//            entidad.setCiudad(new Ciudad(modelo.getCiudad().intValue())); 
//        }
        return entidad;
    }

    
}
