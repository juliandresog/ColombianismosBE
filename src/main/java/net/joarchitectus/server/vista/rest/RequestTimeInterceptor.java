/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.vista.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//@Component("requestTimeInterceptor")
public class RequestTimeInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOG = Logger.getLogger(RequestTimeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // TODO Auto-generated method stub
        //return super.preHandle(request, response, handler);
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        try {
            // TODO Auto-generated method stub
            //super.afterCompletion(request, response, handler, ex);
            long starttime = (Long) request.getAttribute("startTime");
            HttpSession session = request.getSession();

            LOG.info("--REST_REQUEST URL: '" + request.getRequestURL().toString()
                    + "' -- total time " + (System.currentTimeMillis() - starttime + "' ms "
                    + " from " + request.getRemoteAddr() + " >> session "
                    + ":" + session.getAttribute("userName") + ":" + session.getAttribute("scopedTarget.usuario")));
            if ((System.currentTimeMillis() - starttime) > 10000) {
                LOG.warn("--REST_REQUEST URL: '" + request.getRequestURL().toString()
                        + "' -- total time " + (System.currentTimeMillis() - starttime + "' ms "
                        + " from " + request.getRemoteAddr() + " >> session " + session.getId()
                        + ":" + session.getAttribute("userName") + ":" + session.getAttribute("scopedTarget.usuario")));
            }
        } catch (java.lang.IllegalStateException exi) {
            LOG.warn("Sesió finalizada? "+exi.getMessage());
        }
    }

}
