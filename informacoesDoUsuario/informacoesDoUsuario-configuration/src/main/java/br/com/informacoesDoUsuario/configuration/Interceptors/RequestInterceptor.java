package br.com.informacoesDoUsuario.configuration.Interceptors;

import io.gumga.core.GumgaThreadScope;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        GumgaThreadScope.gumgaToken.set(request.getHeader("token"));
        GumgaThreadScope.organizationCode.set(request.getHeader("oi"));
        GumgaThreadScope.login.set(request.getHeader("user"));

        System.out.println(GumgaThreadScope.gumgaToken.get());
        System.out.println(GumgaThreadScope.organizationCode.get());
        System.out.println(GumgaThreadScope.login.get());

        if (GumgaThreadScope.gumgaToken.get() == null) {
            response.sendError(403);
            System.out.println("");
            return false;
        }

        return super.preHandle(request, response, handler);
    }
}
