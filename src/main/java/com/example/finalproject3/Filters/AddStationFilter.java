//package com.example.finalproject3.Filters;
//
//
//import com.example.finalproject3.Entity.Station;
//import com.example.finalproject3.Utility.Utility;
//import org.apache.log4j.Logger;
//
//import javax.servlet.*;
//import javax.servlet.annotation.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.List;
//
//@WebFilter("/AddStation")
//public class AddStationFilter implements Filter {
//    static final Logger logger= Logger.getLogger(AddStationFilter.class);
//    public void init(FilterConfig config) throws ServletException {
//    }
//
//    public void destroy() {
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
//        if(request.getParameter("station")==null)
//        chain.doFilter(request, response);
//        else {
//            Station station = new Station(request.getParameter("station"));
//            List<Station> stations= null;
//            try {
////                stations = DBManager.getInstance().getAllStations();
//            } catch (SQLException e) {
//                logger.info(e);
//                throw new RuntimeException(e);
//            } catch (ClassNotFoundException e) {
//                logger.info(e);
//                throw new RuntimeException(e);
//            }
//            if(stations.contains(station)){
//               request.setAttribute("error","StationInDB");
//                Utility.setString((HttpServletRequest)request);
//                request.getRequestDispatcher("AddStation.jsp").forward(request,response);
//           }
//            else chain.doFilter(request,response);
//
//        }
//    }
//}
