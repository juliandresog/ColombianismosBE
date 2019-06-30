/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

import static org.junit.Assert.*;
import java.util.List;
import net.joarchitectus.server.datos.dao.DaoUsuario;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author julianosorio
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml"})
public class TestUsuariosDao {
    
    protected static org.slf4j.Logger log = LoggerFactory.getLogger(TestUsuariosDao.class);
    
    static {
        System.setProperty("JDBC_DATABASE_URL", "jdbc:postgresql://localhost:5432/jo_colombianismos");
        System.setProperty("JDBC_DATABASE_USERNAME", "jo_colombianismos");
        System.setProperty("JDBC_DATABASE_PASSWORD", "5DhPEvUjrfJYKqa4RvN5");
    }
    

    @Autowired
    private DaoUsuario daoUsuario;

    @Test
    @Transactional
    public void cantidadUsuarios() {
        try {
            log.warn("Revisando si existe algun usuario en la BD"); 
            assertTrue(daoUsuario.cantidadRegistros()>=0);
        } catch (Exception e) {
            log.error("Error JBDC", e);
        }
    }

}
