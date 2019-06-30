///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package server.vista.restfull;
//
//
//import static org.junit.Assert.*;
//import java.util.List;
//import javax.servlet.ServletContext;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mock.web.MockServletContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.context.WebApplicationContext;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import general.SessionHolder;
//import general.SessionWrapper;
//import java.util.Date;
//import java.util.UUID;
//import net.joarchitectus.client.datos.dominio.Empresa;
//import net.joarchitectus.server.servicios.maestro.ServicioAdminEmpresa;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.mockito.Mockito.*;
//import static org.hamcrest.Matchers.*;
//import org.junit.After;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultHandler;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * https://www.baeldung.com/integration-testing-in-spring
// * https://memorynotfound.com/unit-test-spring-mvc-rest-service-junit-mockito/
// *
// * @author julianosorio
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {
//    "file:src/main/webapp/WEB-INF/applicationContext.xml",
//    "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml",})
//@WebAppConfiguration
//public class TestEmpresaRestController {
//
//    protected static org.slf4j.Logger log = LoggerFactory.getLogger(TestEmpresaRestController.class);
//    private static Log logger = LogFactory.getLog("TestEmpresaRestController");
//    
//    static {
//        System.setProperty("JDBC_DATABASE_URL", "jdbc:postgresql://localhost:5432/jo_colombianismos");
//        System.setProperty("JDBC_DATABASE_USERNAME", "jo_colombianismos");
//        System.setProperty("JDBC_DATABASE_PASSWORD", "5DhPEvUjrfJYKqa4RvN5");
//    }
//
//    @Autowired
//    private WebApplicationContext wac;
//    @Autowired
//    private ServicioAdminEmpresa servicioAdminEmpresa;
//
//    private MockMvc mockMvc;
//    //private MockHttpSession mockSession;
//    private SessionHolder sessionHolder;
//
//    @Before
//    public void setup() throws Exception {
//        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//        //mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());
//        sessionHolder = new SessionHolder();
//
//        //creo una sesion con un usuario autenticado
//        this.mockMvc.perform(post("/login/loginMail.json")
//                //.session(mockSession)    
//                .param("email", "desarrollo@joarchitectus.net")
//                .param("clave", "123"))
//                .andExpect(status().isOk())
//                .andDo(new ResultHandler() {
//                    @Override
//                    public void handle(MvcResult result) throws Exception {
//                        sessionHolder.setSession(new SessionWrapper(result.getRequest().getSession()));
//                        //mockSession = sessionHolder.getSession();
//                    }
//                });
//    }
//
//    @After
//    public void tearDown() {
//    }
//
//    @Test
//    public void givenWac_whenServletContext_thenItProvidesGreetController() {
//        log.warn("Validando application context. slf4j");
//        logger.warn("Validando application context. apache log");
//        System.out.println();
//        System.out.println("application context");
//        ServletContext servletContext = wac.getServletContext();
//
//        Assert.assertNotNull(servletContext);
//        Assert.assertTrue(servletContext instanceof MockServletContext);
////        logger.warn("Beans: "+wac.getBeanDefinitionNames()); 
////        for (int i = 0; i < wac.getBeanDefinitionNames().length; i++) {
////            String bean = wac.getBeanDefinitionNames()[i];
////            System.out.println("Bean: "+bean);
////        }
//        Assert.assertNotNull(wac.getBean("empresaRestController"));
//    }
//
//    /**
//     * Testenado http..../html/api/transversal/empresa/get.json
//     */
//    @Test
//    public void givenHomePageURI_whenMockMVC_thenReturnsIndexJSPViewName() throws Exception {
//        this.mockMvc.perform(get("/api/transversal/empresa/get.json").session(sessionHolder.getSession()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                //.andExpect(view().name("empresa/details"))
//                .andExpect(jsonPath("$.message").value("OK"));
//    }
//
//    /**
//     * Testenado http..../html/api/transversal/empresa/get.json Verify Response
//     * Body
//     */
//    @Test
//    public void givenGreetURI_whenMockMVC_thenVerifyResponse() throws Exception {
//        MvcResult mvcResult = this.mockMvc.perform(get("/api/transversal/empresa/get.json").session(sessionHolder.getSession()))
//                .andDo(print()).andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(jsonPath("$.success").value(true))
//                .andReturn();
//
//        Assert.assertEquals("application/json;charset=UTF-8",
//                mvcResult.getResponse().getContentType());
//    }
//
//    /**
//     * Send GET Request with Path Variable
//     *
//     * @throws Exception
//     */
//    @Test
//    public void givenGreetURIWithPathVariable_whenMockMVC_thenResponseOK() throws Exception {
//        this.mockMvc
//                .perform(get("/api/transversal/empresa/get/{id}.json", 1l).session(sessionHolder.getSession()))
//                .andDo(print()).andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andExpect(jsonPath("$.message").value("OK"));
//    }
//
//    /**
//     * Send GET Request with Query Parameters
//     *
//     * @throws Exception
//     */
//    @Test
//    public void givenGreetURIWithQueryParameter_whenMockMVC_thenResponseOK() throws Exception {
//        this.mockMvc.perform(get("/api/transversal/empresa/getPagin.json").session(sessionHolder.getSession())
//                .param("SortField", "nombre"))
//                .andDo(print()).andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(jsonPath("$.message").value("OK"));
//
//    }
//
//    /**
//     * Send POST Request con Formdata
//     *
//     * @throws Exception
//     */
//    @Test
//    public void givenGreetURIWithPostAndFormData_whenMockMVC_thenResponseOK() throws Exception {
//        String nombre = "Zrii restfull " + new Date().getTime();
//
//        Empresa empresa = new Empresa();
//        empresa.setId(1l);
//        empresa.setNombre(nombre);
//
//        //pruebo servicio rest
//        this.mockMvc.perform(post("/api/transversal/empresa/save.json").session(sessionHolder.getSession())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(empresa)))
////                                .param("id", "1")
////                                .param("nombre", nombre)
////                                .param("web", "www.zrii.com"))
//                .andDo(print()).andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.datos.id").value(1))
//                .andExpect(jsonPath("$.datos.nombre").value(nombre));
//
//        //comparo contra lo que tengo en bd usando servicio spring
//        Empresa empresa2 = servicioAdminEmpresa.cargarEntidad(1l).getObjetoRespuesta();
//        assertEquals(nombre, empresa2.getNombre());
//    }
//    
//    /**
//     * converts a Java object into JSON representation
//     */
//    public static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
