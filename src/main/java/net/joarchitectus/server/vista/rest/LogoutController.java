package net.joarchitectus.server.vista.rest;

import net.joarchitectus.client.datos.dominio.Usuarios;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author jdbotero
 */
@Controller
public class LogoutController
{
    @Autowired
    private Usuarios usuarioSession;

    /**
     * Crea la url de logout y resetea los objetos de session
     * @return
     */
    @RequestMapping(value = "logout.html", method = RequestMethod.GET)
    public String logout(HttpServletRequest request)
    {
        usuarioSession.resetear();
        
        HttpSession session = request.getSession();
        //invalidamos la sesion http.
        session.invalidate();
        
        // Direcciono a la url en sessión
        String url = "redirect:/";
        return url;
    }
    
    /**
     * Provisional
     * @param request
     * @return 
     */
    @Deprecated
    @RequestMapping(value = "/login/loginMail", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView login(HttpServletRequest request)
    {
        usuarioSession.resetear();
        usuarioSession.setId(10000l);
        usuarioSession.setNombres("Algo");
        usuarioSession.setCorreoElectronico(request.getParameter("email"));
        
        ModelAndView retorno = new ModelAndView();
        retorno.addObject("success", true);
        return retorno;
    }
}
