package com.example.finalproject3.Utility;

import com.example.finalproject3.Entity.Route;
import com.example.finalproject3.Entity.Station;

public class RouteUtility {
    public static String getStringOfRoute(Route route){
        StringBuilder stationStringBuilder = new StringBuilder();
        for(Station s : route.getStations())
            stationStringBuilder.append(s.getName()+"-");
        return stationStringBuilder.substring(0,stationStringBuilder.length()-1);
    }
}
