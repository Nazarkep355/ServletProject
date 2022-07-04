package com.example.finalproject3.Services;

import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.RouteDAO;
import com.example.finalproject3.DAO.StationDAO;
import com.example.finalproject3.Entity.Route;
import com.example.finalproject3.Entity.Station;

import com.example.finalproject3.Utility.Utility;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class RouteService {
    RouteDAO routeDAO;
    public RouteService(){
        routeDAO = new RouteDAO();
    }

    public RouteService(RouteDAO routeDAO) {
        this.routeDAO = routeDAO;
    }

    public static void deleteInnerStation(Route route, Station station, int newDelay, int newCost)  {
        for(int i =0;i<route.getStations().size();i++){
            if(route.getStations().get(i).equals(station)){
                if(i>0){route.getDelays().remove(i - 1);
                    i--;
                    route.getDelays().remove(i);
                    route.getDelays().add(i,newDelay);
                    route.getStations().remove(station);
                }
                else {
                    route.getStations().remove(station);
                    route.getDelays().remove(0);

                }
                route.setCost(newCost);
                break;
            }

        }

    }
    public static void deleteOuterStation(Route route,Station station, int newCost){
        if(route.getStations().get(0).equals(station)) {
            route.getStations().remove(0);
            route.getDelays().remove(0);
        }else {route.getStations().remove(route.getStations().size()-1);
            route.getDelays().remove(route.getDelays().size()-1);
        }
        route.setCost(newCost);

    }

    public boolean addRouteToDataBase(Route route) throws DAOException {
        return routeDAO.insertRouteInDataBase(route);
    }
    public List<Route> getAllRoutes() throws DAOException {
        return routeDAO.getAllRoutes();
    }

}
