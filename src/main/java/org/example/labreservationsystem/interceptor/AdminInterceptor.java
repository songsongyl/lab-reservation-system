package org.example.labreservationsystem.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.labreservationsystem.dox.User;
import org.example.labreservationsystem.exception.Code;
import org.example.labreservationsystem.exception.XException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(User.ADMIN.equals(request.getAttribute("role"))){
            return true;
        }
        throw XException.builder().code(Code.FORBIDDEN).build();
    }
}
