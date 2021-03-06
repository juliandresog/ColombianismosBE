/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;


import static org.junit.Assert.*;
import java.util.List;
import javax.servlet.ServletContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.UUID;
import net.joarchitectus.client.datos.dominio.Empresa;
import net.joarchitectus.server.servicios.maestro.ServicioAdminEmpresa;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import org.junit.After;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * https://www.baeldung.com/integration-testing-in-spring
 * https://memorynotfound.com/unit-test-spring-mvc-rest-service-junit-mockito/
 *
 * @author julianosorio
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml",})
@WebAppConfiguration
public class TestEmpresaController {

    protected static org.slf4j.Logger log = LoggerFactory.getLogger(TestEmpresaController.class);
    private static Log logger = LogFactory.getLog("TestEmpresaController");

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private ServicioAdminEmpresa servicioAdminEmpresa;

    private MockMvc mockMvc;
    //private MockHttpSession mockSession;
    private SessionHolder sessionHolder;
    
    static {
        System.setProperty("JDBC_DATABASE_URL", "jdbc:postgresql://localhost:5432/jo_colombianismos");
        System.setProperty("JDBC_DATABASE_USERNAME", "jo_colombianismos");
        System.setProperty("JDBC_DATABASE_PASSWORD", "5DhPEvUjrfJYKqa4RvN5");
    }

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        //mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());
        sessionHolder = new SessionHolder();
        
        //creo una sesion con un usuario autenticado
        this.mockMvc.perform(post("/login/loginMail.json")
            //.session(mockSession)    
            .param("email", "admin@colombianismos.net")
            .param("clave", "123"))
            .andExpect(status().isOk())
            .andDo(new ResultHandler() {
                @Override
                public void handle(MvcResult result) throws Exception {
                    sessionHolder.setSession(new SessionWrapper(result.getRequest().getSession()));
                    //mockSession = sessionHolder.getSession();
                }
            });
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        log.warn("Validando application context. slf4j");
        logger.warn("Validando application context. apache log");
        System.out.println();
        System.out.println("application context");
        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
//        logger.warn("Beans: "+wac.getBeanDefinitionNames()); 
//        for (int i = 0; i < wac.getBeanDefinitionNames().length; i++) {
//            String bean = wac.getBeanDefinitionNames()[i];
//            System.out.println("Bean: "+bean);
//        }
        Assert.assertNotNull(wac.getBean("empresaController"));
    }

    /**
     * Testenado http..../html/empresa/details.json
     */
    @Test
    public void givenHomePageURI_whenMockMVC_thenReturnsIndexJSPViewName() throws Exception {
            this.mockMvc.perform(get("/empresa/details.json").session(sessionHolder.getSession()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("empresa/details"));
            
//            this.mockMvc.perform(get("/empresa/details.json").session(mockSession))
//                    .andDo(print())
//                    .andExpect(view().name("empresa/details"));
    }

    /**
     * Testenado http..../html/empresa/details.json
     * Verify Response Body
     */
    @Test
    public void givenGreetURI_whenMockMVC_thenVerifyResponse() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/empresa/details.json").session(sessionHolder.getSession()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("OK hijo"))
                .andReturn();

        Assert.assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    /**
     * Send GET Request with Path Variable
     * @throws Exception 
     */
    @Test
    public void givenGreetURIWithPathVariable_whenMockMVC_thenResponseOK() throws Exception {
        this.mockMvc
                .perform(get("/empresa/get/{id}.json", 1l).session(sessionHolder.getSession()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    /**
     * Send GET Request with Query Parameters
     * @throws Exception 
     */
    @Test
    public void givenGreetURIWithQueryParameter_whenMockMVC_thenResponseOK() throws Exception {
        this.mockMvc.perform(get("/empresa/getPagin.json").session(sessionHolder.getSession())
                .param("SortField", "nombre"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

//    /**
//     * Send POST Request
//     * @throws Exception 
//     */
//    @Test
//    public void givenGreetURIWithPost_whenMockMVC_thenVerifyResponse() throws Exception {
//        this.mockMvc.perform(post("/empresa/save.json").session(sessionHolder.getSession()))
//                .andDo(print())
//                .andExpect(status().isOk()).andExpect(content()
//                .contentType("application/json"))//application/json;charset=UTF-8
//                .andExpect(jsonPath("$.success").value(false));
//    }

    /**
     * Send POST Request con Formdata
     * @throws Exception 
     */
    @Test
    public void givenGreetURIWithPostAndFormData_whenMockMVC_thenResponseOK() throws Exception {
        String nombre = "Zrii "+new Date().getTime();
        
        //pruebo servicio rest
        this.mockMvc.perform(post("/empresa/save.json").session(sessionHolder.getSession())
                .param("id", "1")
                .param("nombre", nombre)
//                .param("web", "www.zrii.com")
                )
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.datos.id").value(1))
                .andExpect(jsonPath("$.datos.nombre").value(nombre));
        
        //comparo contra lo que tengo en bd usando servicio spring
        Empresa empresa = servicioAdminEmpresa.cargarEntidad(1l).getObjetoRespuesta();
        assertEquals(nombre, empresa.getNombre());
    }
}
