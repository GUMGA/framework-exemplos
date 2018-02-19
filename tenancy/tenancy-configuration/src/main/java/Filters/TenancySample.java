package Filters;

import io.gumga.core.GumgaThreadScope;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TenancySample extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        GumgaThreadScope.organizationCode.set(request.getHeader("oi"));
        System.out.println(request.getHeader("oi"));

        return super.preHandle(request, response, handler);
    }
}
