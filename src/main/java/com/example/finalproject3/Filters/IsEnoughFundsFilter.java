//package com.example.finalproject3.Filters;
//
//
//import com.example.finalproject3.Entity.Train;
//import com.example.finalproject3.Entity.User;
//import com.example.finalproject3.Utility.Utility;
//import org.apache.log4j.Logger;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.List;
//
//@WebFilter("/BuyOperation")
//public class IsEnoughFundsFilter implements Filter {
//    static final Logger logger=Logger.getLogger(IsEnoughFundsFilter.class);
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        javax.servlet.Filter.super.init(filterConfig);
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        Utility.setString(request);
//        User user  = (User) request.getSession().getAttribute("user");
//        if (user == null) {servletRequest.setAttribute("error","Buy without login");
//            servletRequest.getRequestDispatcher("home.jsp").forward(servletRequest,servletResponse);
//        }else {
//        String from = servletRequest.getParameter("from");
//       String to =servletRequest.getParameter("to");
//       String trainId=servletRequest.getParameter("trainId");
//       String url = "/TrainInfo?trainId="+trainId;
//       if(from!=null&&to!=null)
//           url+="&from="+from+"&to="+to;
//       try{
//           List<Train> trains = DBManager.getInstance().getAllEnableTrains();
//           for(Train t : trains)
//               if(t.getId()==Integer.parseInt(trainId)){
//                   if(user.getMoney()<t.getCost())
//                   {servletRequest.setAttribute("notEnough",true);
//                        servletRequest.getRequestDispatcher(url).forward(servletRequest,servletResponse);}else
//                            if(t.getSeats()<=t.getBooked())
//                            {servletRequest.setAttribute("noFreeSeats",true);
//                                servletRequest.getRequestDispatcher(url).forward(servletRequest,servletResponse);}else
//           filterChain.doFilter(servletRequest,servletResponse);}
//       } catch (SQLException e) {
//           logger.info(e);
//           throw new RuntimeException(e);
//       } catch (ClassNotFoundException e) {
//           logger.info(e);
//           throw new RuntimeException(e);
//       }
//
//    }
//    }
//
//    @Override
//    public void destroy() {
//        javax.servlet.Filter.super.destroy();
//    }
//}
