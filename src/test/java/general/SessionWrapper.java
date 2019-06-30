/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

import javax.servlet.http.HttpSession;
import org.springframework.mock.web.MockHttpSession;

/**
 *
 * @author julianosorio
 */
public class SessionWrapper extends MockHttpSession{
    private final HttpSession httpSession;

    public SessionWrapper(HttpSession httpSession){
        this.httpSession = httpSession;
    }

    @Override
    public Object getAttribute(String name) {
        return this.httpSession.getAttribute(name);
    }

}