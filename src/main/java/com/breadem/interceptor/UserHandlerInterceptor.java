package com.breadem.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class UserHandlerInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String thislD = null;
		if(request.getCookies() != null){
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("test")){
					thislD = cookies[i].getValue();
				}else{
					thislD = null;
				}
				
			}
		}
		if(thislD != null){
			return true;
		}else{
			response.sendRedirect("/login");
			return false;
		}
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
    }

}
