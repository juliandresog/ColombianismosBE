package net.joarchitectus.server.vista.rest;

import net.joarchitectus.client.datos.dominio.Empresa;
import net.joarchitectus.client.datos.dominio.Rol;
import net.joarchitectus.client.datos.dominio.Usuarios;
import net.joarchitectus.server.datos.dao.DaoEmpresa;
import net.joarchitectus.server.util.Cifrado;
import net.joarchitectus.server.datos.dao.DaoRol;
import net.joarchitectus.server.datos.dao.DaoUsuario;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA. User: hansospina Date: 6/20/11 Time: 10:48 AM To
 * change this template use File | Settings | File Templates.
 */
@Component
public class Bootstrap implements InitializingBean {

    private static Logger log = Logger.getLogger(Bootstrap.class);
    private DaoUsuario daoUsuario;
    private DaoRol daoRol;
    @Autowired
    private DaoEmpresa daoEmpresa;
    


    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware). <p>This
     * method allows the bean instance to perform initialization only possible
     * when all bean properties have been set and to throw an exception in the
     * event of misconfiguration.
     *
     * @throws Exception in the event of misconfiguration (such as failure to
     * set an essential property) or if initialization fails.
     */
    @Override
    @Transactional
    public void afterPropertiesSet() throws Exception {
        inicializarUsuriosGrupos();
    }

    private void inicializarUsuriosGrupos() {
        try {
            if(daoEmpresa.cantidadRegistros()<1){
                Empresa emp = new Empresa();
                emp.setNombre("Primera");
                daoEmpresa.guardar(emp);
            }
            
            Rol g = daoRol.getById(Rol.ROL_ADMINISTRADOR_SISTEMA);
            // if there is data already created
            if (g != null) {
                return;
            }

            Rol admin = new Rol();
//            admin.setId(Rol.ROL_ADMINISTRADOR_SISTEMA);
            admin.setAutorizadoLogin(Boolean.TRUE);
            admin.setNombre("Administrador sistema");
            daoRol.guardar(admin);

            Rol adminNegocio = new Rol();
//            adminNegocio.setId(Rol.ROL_ADMINISTRADOR_NEGOCIO); 
            adminNegocio.setAutorizadoLogin(Boolean.TRUE);
            adminNegocio.setNombre("Administrador empresa");
            daoRol.guardar(adminNegocio);

            //Se ingresa un usuario admin de prueba
            Usuarios usuarioAdmin = new Usuarios();
            usuarioAdmin.setDocumento("admin");
            usuarioAdmin.setClave(Cifrado.getStringMessageDigest("123", Cifrado.SHA256));
            usuarioAdmin.setCorreoElectronico("admin@boosit.com");
            usuarioAdmin.setNombres("Administrador");
            usuarioAdmin.setApellidos("Boos");
            usuarioAdmin.setInactivo(Boolean.FALSE);
            //Rol administrador
            usuarioAdmin.setRol(admin);
            daoUsuario.guardar(usuarioAdmin);            
                       
//            //crear indices
//            daoUsuario.ejecutarUpdateDelete(""
//                    + "CREATE INDEX idx_calificacioncliente_parqueadero ON calificacion_cliente (id_parqueadero);"
//                    + "");


            log.info("Se inicilizó los usuarios del sistema.");
        } catch (Exception e) {
            log.error("Erro " + e.getMessage(), e);

        }
    }
    
    @Autowired
    public void setDaoUsuario(DaoUsuario daoUsuario) {
        this.daoUsuario = daoUsuario;
    }

    @Autowired
    public void setDaoRol(DaoRol daoRol) {
        this.daoRol = daoRol;
    }
    
}
