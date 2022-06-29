package com.example.finalproject3.Filters;

import com.example.finalproject3.Entity.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/CancelTrain")
public class isAdminFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        User user= (User) request1.getSession().getAttribute("user");
        HttpServletResponse response1 = (HttpServletResponse) response;
        if(user!=null&&user.isAdmin())
        chain.doFilter(request, response);
        else response1.sendRedirect("/");
    }
}
