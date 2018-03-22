package br.com.registrosCompartilhados.configuration.web;

import io.gumga.core.GumgaThreadScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InterceptorSample extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String oi = request.getHeader("oi");

        System.out.println("oi = "+request.getHeader("oi"));
        if((oi == null)) {

            response.setStatus(401);
            response.getWriter().write("'oi' não informado!" );
            response.getWriter().flush();
            return false;
        }
        GumgaThreadScope.organizationCode.set(oi);
        return super.preHandle(request, response, handler);
    }


}
