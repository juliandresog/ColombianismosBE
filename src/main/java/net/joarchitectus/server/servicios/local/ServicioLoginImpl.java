/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.servicios.local;

import java.util.Calendar;
import java.util.Date;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.client.entidades.RespuestaRPC;
import net.joarchitectus.server.datos.dao.DaoUsuario;
import net.joarchitectus.server.servicios.ServicioLogin;
import net.joarchitectus.server.util.Cifrado;
import net.joarchitectus.server.util.Formatos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author josorio
 */
@Service(value = "servicioLogin")
public class ServicioLoginImpl implements ServicioLogin {

    //protected static Logger log = Logger.getLogger(ServicioLoginImpl.class);
    protected static Logger log = LoggerFactory.getLogger(ServicioLoginImpl.class);

    @Autowired
    private DaoUsuario daoUsuarios;
    @Autowired
    private Usuarios usuarioSession;

    /**
     * Verifica si un usuario puede loguearse en el sistema o no, en caso de ser
     * los datos válidos se creará una sesión en el servidor
     *
     * @param email
     * @param clave Clave del usuario a loguear sin encriptar (se cifra usando
     * Cifrado.SHA256)
     * @return Nulo si el usuario no existe, en caso de existir, un objeto de la
     * clase Usuario con los datos del mismo
     */
    @Override
    public Usuarios loginMail(String email, String clave) {

        email = email.toLowerCase();
        Usuarios usuarioHib = daoUsuarios.getActivoByEmailAndClave(email, Cifrado.getStringMessageDigest(clave, Cifrado.SHA256));

        if (usuarioHib != null) {
//            if (usuarioHib.getVerificado() != null && !usuarioHib.getVerificado()) {
//                //El usuario no ha verificado el correo.
//                return null;
//            }

            //actualizo fecha login
            //usuarioHib.setFechaUltimoLoginWeb(new Date());
            daoUsuarios.guardar(usuarioHib);
            //usuarioHib.setPrimerLogin(primerLogin);
            //guaro información en sesion.
            usuarioSession.copiarDatos(usuarioHib);
        }

        return usuarioHib;
    }

    /**
     * Retorna el objeto usuario que se tiene en la sesión
     *
     * @return
     */
    public Usuarios obtenerDatosUsuario() {
        Usuarios usuario = new Usuarios();
        usuario.copiarDatos(usuarioSession);

        //me aseguro de tener la version mas reciente del usuario con sus estados actualizados.
        usuario = daoUsuarios.getById(usuarioSession.getId());
        usuarioSession.copiarDatos(usuario);

        return usuario;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Usuarios validarHashRecuperar(String hashRecu) {
        Usuarios usuario = null;
        usuario = daoUsuarios.getByLlaveCambioClave(hashRecu);
        if (usuario == null) {
            usuario = new Usuarios();
            //usuario.setValorRecuperarCont(2);
            return usuario; // Hash no válido
        } else {
            Calendar fechaActual = Calendar.getInstance();
            Calendar fechaLlave = Calendar.getInstance();
            //fechaLlave.setTime(usuario.getFechaEnvioLlave());
            //Sumo una hora para validar la fecha de la llave
            fechaLlave.add(Calendar.HOUR_OF_DAY, 1);
            //Valido que no haya pasado una hora desde que envie la llave
            if (fechaActual.getTime().before(fechaLlave.getTime())) {
                //usuario.setValorRecuperarCont(0);
                //Borro la llave para que quede inactiva
                //usuario.setFechaEnvioLlave(null);
                usuario.setLlaveCambioClave(null);
                daoUsuarios.guardar(usuario);
                return usuario; //Hash ok
            } else {
                //usuario.setValorRecuperarCont(3);
                return usuario; //Paso el tiempo válido
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Integer cambiarContrasenaUsuario(String contrasena, Long idUsuario) {
        Usuarios usuario = daoUsuarios.getById(idUsuario);
        if (usuario != null) {
            usuario.setClave(Cifrado.getStringMessageDigest(contrasena, Cifrado.SHA256));
            //usuario.setClavicordio(Formatos.toAES(contrasena, Formatos.llaveJuli));
            daoUsuarios.guardar(usuario);            

            return 0; //Ok
        } else {
            return 2; //No se encontro el usuario
        }
    }

    @Override
    public RespuestaRPC<Boolean> sessionActiva() {
        RespuestaRPC<Boolean> retorno = new RespuestaRPC<Boolean>();
        if (usuarioSession == null || usuarioSession.getId() == null || usuarioSession.getId() < 1) {
            retorno.setResultado(RespuestaRPC.RESULTADO_FALLO_NO_SESSION);
            return retorno;
        } else {
            retorno.setResultado(RespuestaRPC.RESULTADO_OK);
            retorno.setObjetoRespuesta(Boolean.TRUE);
            return retorno;
        }
    }
}
