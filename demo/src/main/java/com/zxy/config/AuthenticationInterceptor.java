package com.zxy.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zxy.annotation.LoginRequired;
import com.zxy.service.mappers.userMapper;
import com.zxy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    userMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = "";
        Cookie[] cks = httpServletRequest.getCookies();
        if(cks != null) {
            for (Cookie ck : cks) {
                if (ck.getName().equals("token"))
                    token = ck.getValue();
            }
        }
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(LoginRequired.class)) {
            if (token == null) {
                System.out.println("token == null");
                httpServletResponse.sendRedirect("/roglogin");
                return false;
            }
            String idString="";
            try {
                idString = JWT.decode(token).getAudience().get(0);
            } catch (JWTDecodeException e) {
                System.out.println("JWT Exception");
                httpServletResponse.sendRedirect("/reglogin");
                return false;
            }
            if(idString==null||idString==""){
                System.out.println("idString == null");
                httpServletResponse.sendRedirect("/reglogin");
                return false;
            }
            User user = userMapper.findById(Integer.parseInt(idString));
            if (user == null) {
                System.out.println("user == null");
                httpServletResponse.sendRedirect("/reglogin");
                return false;
            }
            try {
                JWTVerifier ver = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    ver.verify(token);
                } catch (JWTVerificationException e) {
                    httpServletResponse.sendRedirect("/reglogin");
                    return false;
                }
            } catch (Exception e) {
            }
            httpServletRequest.setAttribute("currentUser", user);
            return true;
        }
            return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}