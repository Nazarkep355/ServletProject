package com.example.finalproject3.Filters;

import com.example.finalproject3.Utility.Utility;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@WebFilter("/")
public class ValidateLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request =(HttpServletRequest)servletRequest;
        String command = servletRequest.getParameter("command");
        if(command!=null&&command.equals("login")){
        String email =servletRequest.getParameter("email");
        String password = servletRequest.getParameter("password");
        if(Utility.validateEmail(email)&&Utility.validatePassword(password)){
        filterChain.doFilter(servletRequest,servletResponse);}
        else {request.getSession().setAttribute("error","EnterWrongFormat");
        servletRequest.getRequestDispatcher("/").forward(servletRequest,servletResponse);}}
        else filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
