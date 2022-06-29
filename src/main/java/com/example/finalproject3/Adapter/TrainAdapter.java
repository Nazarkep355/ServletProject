package com.example.finalproject3.Adapter;

import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.RouteDAO;
import com.example.finalproject3.DAO.TrainDAO;
import com.example.finalproject3.Entity.Route;
import com.example.finalproject3.Entity.Train;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class TrainAdapter {

    public static Train getTrainFromRequest(HttpServletRequest request) throws DAOException {
        String trainId = request.getParameter("trainId");
        if(trainId!=null){
            return new TrainDAO().getTrainById(Integer.parseInt(trainId));
        }
        String formDate = request.getParameter("date");
        int routeId = Integer.parseInt(request.getParameter("route"));
        int seats = Integer.parseInt(request.getParameter("seats"));

        Route route = new RouteDAO().getRouteById(routeId);
        Date date =getDateFromForm(formDate);
        Train train = new Train(route,date);
        train.setSeats(seats);
        return train;
    }
    public static Date getDateFromForm(String dateStr){
        Date date= new Date();
        date.setTime(0);
        if(!dateStr.contains("T")){
            String[]segments = dateStr.split("-");
            date.setYear(Integer.parseInt(segments[0]));
            date.setMonth(Integer.parseInt(segments[1])-1);
            date.setDate(Integer.parseInt(segments[2]));
            date.setHours(0);
            date.setMinutes(0);
            date.setSeconds(0);
            return date;
        }else {
            String[] segments= dateStr.split("T");
            String[] hoursMins=segments[1].split(":");
            segments = segments[0].split("-");
            date.setYear(Integer.parseInt(segments[0]));
            date.setMonth(Integer.parseInt(segments[1])-1);
            date.setDate(Integer.parseInt(segments[2]));
            date.setHours(Integer.parseInt(hoursMins[0]));
            date.setMinutes(Integer.parseInt(hoursMins[1]));
            date.setSeconds(0);
            return date;}
    }
    public static Train getTrainInfoFromRequest(HttpServletRequest request) throws DAOException {
        String trainId = request.getParameter("trainId");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        Train train = new TrainDAO().getTrainById(Integer.parseInt(trainId));
        return train;
    }

}
