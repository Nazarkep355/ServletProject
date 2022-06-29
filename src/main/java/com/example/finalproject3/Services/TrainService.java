package com.example.finalproject3.Services;

import com.example.finalproject3.DAO.*;
import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Entity.Ticket;
import com.example.finalproject3.Entity.Train;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TrainService {
        UserDAO userDAO;
        TicketDAO ticketDAO;
        TrainDAO trainDAO;

    public TrainService(){
        userDAO=new UserDAO();
        ticketDAO= new TicketDAO();
        trainDAO = new TrainDAO();
    }
    public TrainService(UserDAO userDAO,TicketDAO ticketDAO, TrainDAO trainDAO){
        this.userDAO=userDAO;
        this.ticketDAO= ticketDAO;
        this.trainDAO =  trainDAO;
    }
    public void CancelTrainWithAllChanges(Train train) throws DAOException {
        List<Ticket> ticketList =ticketDAO.getAllEnableTicketsOnTrain(train);
        for(Ticket ticket : ticketList){
            userDAO.addMoneyOfUser(ticket.getOwner(),ticket.getCost());
            ticketDAO.updateTicketEnabled(ticket);}
        trainDAO.updateEnableById(train);
    }
    public List<String> getRecipientsEmailsOfTrain(Train train) throws DAOException {
        Set<String> emails = new HashSet<>();
        List<Ticket> tickets = ticketDAO.getAllEnableTicketsOnTrain(train);
        tickets.stream().forEach((a)->{ emails.add(a.getOwner().getEmail());});
        return emails.stream().collect(Collectors.toList());
    }
    public boolean addTrainToDataBase(Train train) throws DAOException {
        return trainDAO.insertTrainInDataBase(train);
    }
    public  List<Train> getPaginatedListOfTrainsFromRequest(int page) throws DAOException {
        List<Train> trains = trainDAO.paginatedListOfEnableTrains(page,5);
        return trains;
    }

    public  List<Train> getPaginatedTrainsByOneStationFromRequest(Station station, int page) throws DAOException {
        List<Train> trains = trainDAO.paginatedListOfEnabledTrainsByOneStation(station,page,5);
        return trains;
    }
    public  List<Train> getPaginatedTrainsByTwoStationFromRequest(Station station1, Station station2, int page) throws DAOException {
          List<Train> trains = trainDAO.paginatedListOfEnabledTrainsByTwoStations(station1,station2,page,5);
        return trains;

    }


}
