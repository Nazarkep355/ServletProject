package com.example.finalproject3.Adapter;

import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.TrainDAO;
import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Entity.Ticket;
import com.example.finalproject3.Entity.Train;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.Utility.Utility;

import javax.servlet.http.HttpServletRequest;

public class TicketAdapter {
    public static Ticket getTicketFromRequest(HttpServletRequest request) throws DAOException {
        Utility.updateUser(request);
        int trainId = Integer.parseInt(request.getParameter("trainId"));
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        Train train = new TrainDAO().getTrainById(trainId);
        Ticket ticket = new Ticket(train);
        ticket.setOwner((User) request.getSession().getAttribute("user"));
        if(from!=null&&to!=null) {
            Station station1 = train.getStations().get(Integer.parseInt(from));
            Station station2 = train.getStations().get(Integer.parseInt(to));
            ticket.setStartStation(station1);
            ticket.setEndStation(station2);
        }
        return ticket;
    }
}
