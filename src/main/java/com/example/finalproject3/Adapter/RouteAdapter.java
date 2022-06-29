package com.example.finalproject3.Adapter;

import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.StationDAO;
import com.example.finalproject3.Entity.Route;
import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Utility.Utility;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class RouteAdapter {
    public static Route getRouteFromRequest(HttpServletRequest request) throws DAOException {
        Utility.setString(request);
        int number = Integer.parseInt(request.getParameter("stationNumber"));
        int cost = Integer.parseInt(request.getParameter("cost"));
        boolean notFound = false;
        List<Station> routeStations = new ArrayList<>();
        List<Integer> delays = new ArrayList<>();
        List<Station> allStations = new StationDAO().getAllStations();
        for (int i = 1; i <= number; i++) {
            Station station = new Station(request.getParameter("station" + i));
            if (!allStations.contains(station)) {
                notFound = true;
                break;
            }
            routeStations.add(station);
            if (i < number)
                delays.add(Integer.valueOf(request.getParameter("delay" + i)));
        }
        if (notFound) {
            return null;
        }
        Route route = new Route();
        route.setCost(cost);
        route.setDelays(delays);
        route.setStations(routeStations);
        return route;
    }
}
