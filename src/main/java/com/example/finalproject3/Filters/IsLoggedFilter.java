//package com.example.finalproject3.Filters;
//
//import com.example.finalproject3.Utility.Utility;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
////@WebFilter("/BuyOperation")
//public class IsLoggedFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        Utility.setString(request);
//        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
//        if(session.getAttribute("user")!=null){
//        filterChain.doFilter(servletRequest,servletResponse);}
//        else {servletRequest.setAttribute("error","Buy without login");
//            servletRequest.getRequestDispatcher("home.jsp").forward(servletRequest,servletResponse);
//        }}
//
//    @Override
//    public void destroy() {
//        Filter.super.destroy();
//    }
//}
