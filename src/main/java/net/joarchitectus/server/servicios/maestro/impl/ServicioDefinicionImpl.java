/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.servicios.maestro.impl;

import java.util.List;
import net.joarchitectus.client.datos.dominio.Definicion;
import net.joarchitectus.client.datos.dominio.Lema;
import net.joarchitectus.client.datos.dominio.LemaDefinicion;
import net.joarchitectus.client.datos.dominio.LemaDefinicionKey;
import net.joarchitectus.client.datos.dominio.Tipificador;
import net.joarchitectus.client.datos.dominio.TipificadorDefinicion;
import net.joarchitectus.client.datos.dominio.TipificadorDefinicionKey;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.client.datos.model.DefinicionModel;
import net.joarchitectus.client.entidades.RespuestaRPC;
import net.joarchitectus.server.datos.dao.DaoDefinicion;
import net.joarchitectus.server.datos.dao.DaoLema;
import net.joarchitectus.server.datos.dao.DaoTipificador;
import net.joarchitectus.server.servicios.maestro.ServicioDefinicion;
import net.joarchitectus.server.util.ModeloConverter;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONArray;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author josorio
 */
@Service(value = "servicioDefinicion")
public class ServicioDefinicionImpl extends ServicioMaestroImpl<Definicion> implements ServicioDefinicion {

    /* Un comentario sobre la implementacion de esta clase podria ir aqui. */
    private Usuarios usuarioSession;// NOPMD - variable usada en la implementacion
    private DaoDefinicion daoDefinicion;// NOPMD - dao
    protected static org.slf4j.Logger log2 = LoggerFactory.getLogger(ServicioDefinicionImpl.class);
    @Autowired
    private DaoTipificador daoTipificador;
    @Autowired
    private DaoLema daoLema;// NOPMD - dao

    @Autowired
    @Override
    public void setUsuarioSession(Usuarios usuarioSession) {
        this.usuarioSession = usuarioSession;
        super.setUsuarioSession(usuarioSession);
    }

    @Autowired
    public void setDaoDefinicion(DaoDefinicion daoDefinicion) {
        this.daoDefinicion = daoDefinicion;
        super.setDaoGenerico(daoDefinicion);
    }

    @Transactional
    @Override
    public RespuestaRPC<Definicion> guardarDefinicion(DefinicionModel model) {
        RespuestaRPC<Definicion> retorno = new RespuestaRPC<Definicion>();
        try {
            Definicion entidad;
            if (model.getId() != null) {
                entidad = this.cargarEntidad(model.getId()).getObjetoRespuesta();
            } else {
                entidad = new Definicion();
            }

            //creo entidad a partir del modelo buscando no reemplazar datos con nulos
            entidad = (Definicion) ModeloConverter.convertirModeloToEntidad(entidad, model, false);

            daoDefinicion.guardar(entidad);

            //borro lo que habia antes si es que estoy editando
            daoLema.ejecutarUpdateDelete("delete from relacion_lema_definicion where definicion_id = " + entidad.getId());
            daoDefinicion.ejecutarUpdateDelete("delete from relacion_tipificador_definicion where definicion_id = " + entidad.getId());            
            
            if (model.getLemas() != null && !model.getLemas().isEmpty()) {                

                String regiones = null;
                //Creo un vector de regiones para guardarlo en el lema y su definicion
                if (model.getMarcaRegional() != null && !model.getMarcaRegional().isEmpty()) {
                    List<Tipificador> tiposReg = daoTipificador.buscarRegistros(model.getMarcaRegional());
                    JSONArray regArray = new JSONArray();
                    for (Tipificador reg : tiposReg) {
                        regArray.put(reg.getNombre());
                    }
                    regiones = regArray.toString();
                }

                List<Long> ids = model.getLemas();
                for (Long id : ids) {
                    LemaDefinicionKey idFK = new LemaDefinicionKey();
                    idFK.setDefinicionId(entidad.getId());
                    idFK.setLemaId(id);
                            
                    LemaDefinicion lemaDef = new LemaDefinicion();
                    lemaDef.setDefinicion(entidad);
                    lemaDef.setLema(new Lema(id));
                    lemaDef.setId(idFK);
                    lemaDef.setEjemplo(model.getEjemplo());
                    lemaDef.setRegiones(regiones);
                    daoDefinicion.guardar(lemaDef);
                }
            }

            if (model.getMarcaGramatical()!= null && !model.getMarcaGramatical().isEmpty()) {
                List<Long> ids = model.getMarcaGramatical();
                for (Long id : ids) {
                    TipificadorDefinicionKey idFK = new TipificadorDefinicionKey();
                    idFK.setDefinicionId(entidad.getId());
                    idFK.setTipificadorId(id);
                    
                    TipificadorDefinicion tipDef = new TipificadorDefinicion();
                    tipDef.setDefinicion(entidad);
                    tipDef.setTipificador(new Tipificador(id));
                    tipDef.setId(idFK);
                    daoDefinicion.guardar(tipDef);
                }
            }
            
            if (model.getMarcaUso()!= null && !model.getMarcaUso().isEmpty()) {
                List<Long> ids = model.getMarcaUso();
                for (Long id : ids) {
                    TipificadorDefinicionKey idFK = new TipificadorDefinicionKey();
                    idFK.setDefinicionId(entidad.getId());
                    idFK.setTipificadorId(id);
                    
                    TipificadorDefinicion tipDef = new TipificadorDefinicion();
                    tipDef.setDefinicion(entidad);
                    tipDef.setTipificador(new Tipificador(id));
                    tipDef.setId(idFK);
                    daoDefinicion.guardar(tipDef);
                }
            }
            
            if (model.getMarcaRegional()!= null && !model.getMarcaRegional().isEmpty()) {
                List<Long> ids = model.getMarcaRegional();
                for (Long id : ids) {
                    TipificadorDefinicionKey idFK = new TipificadorDefinicionKey();
                    idFK.setDefinicionId(entidad.getId());
                    idFK.setTipificadorId(id);
                    
                    TipificadorDefinicion tipDef = new TipificadorDefinicion();
                    tipDef.setDefinicion(entidad);
                    tipDef.setTipificador(new Tipificador(id));
                    tipDef.setId(idFK);
                    daoDefinicion.guardar(tipDef);
                }
            }

            retorno.setObjetoRespuesta(entidad);
            retorno.setResultado(RespuestaRPC.RESULTADO_OK);

        } catch (ConstraintViolationException e) {
            log2.error("Error", e);
            retorno.setResultado(RespuestaRPC.RESULTADO_EXCEPCION_SERVER);
            retorno.setDescripcionError("Error de integridad de datos, por favor comunicarse con el área de soporte");
        } catch (DataIntegrityViolationException e) {
            log2.error("Error", e);
            retorno.setResultado(RespuestaRPC.RESULTADO_EXCEPCION_SERVER);
            retorno.setDescripcionError("Error de integridad de datos, por favor comunicarse con el área de soporte");
        } catch (Exception e) {
            log2.error("Error", e);
            //e.printStackTrace();
            retorno.setResultado(RespuestaRPC.RESULTADO_EXCEPCION_SERVER);
        }
        return retorno;
    }

    @Override
    public DefinicionModel obtenerDefinicion(Long id) throws Exception {
        Definicion definicion = daoDefinicion.getById(id);
        if(definicion==null){
            return null;
        }else{
           DefinicionModel modelo = (DefinicionModel)ModeloConverter.convertirEntidadToModelo(definicion, new DefinicionModel(), false);
           List<Long> lemas = daoDefinicion.getIDsLemas(id);
           modelo.setLemas(lemas);
           List<Long> marcasGramaticales =  daoDefinicion.getIDsMarcasGramaticales(id);
           modelo.setMarcaGramatical(marcasGramaticales);
           List<Long> marcasUso =  daoDefinicion.getIDsMarcasUso(id);
           modelo.setMarcaUso(marcasUso);
           List<Long> marcasRegionales =  daoDefinicion.getIDsMarcasRegionales(id);
           modelo.setMarcaRegional(marcasRegionales);
           
           return modelo;
        }
    }

}
