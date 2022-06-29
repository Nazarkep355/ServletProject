package com.example.finalproject3.Services;

import com.example.finalproject3.DAO.*;

import com.example.finalproject3.Entity.Ticket;

import com.example.finalproject3.Entity.User;
import com.example.finalproject3.Utility.EmailSessionBean;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.NotSupportedException;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TicketService {
    Logger logger =Logger.getLogger(TicketService.class);
    @EJB
    private EmailSessionBean emailSessionBean;
    UserDAO userDAO;
    TicketDAO ticketDAO;
    TrainDAO trainDAO;
    public TicketService(){
        userDAO=new UserDAO();
        ticketDAO= new TicketDAO();
        trainDAO = new TrainDAO();

    }
    public TicketService(UserDAO userDAO,TicketDAO ticketDAO, TrainDAO trainDAO){
        this.userDAO = userDAO;
        this.trainDAO = trainDAO;
        this.ticketDAO = ticketDAO;
    }
    public void addTicketToWithChanges(Ticket ticket) throws DAOException {
        if(ticket.getTrain().getSeats()<=ticket.getTrain().getBooked())
            throw new IllegalArgumentException("NoFreeSeats");
        if(ticket.getOwner().getMoney()<ticket.getCost())
        throw new IllegalArgumentException("notEnoughFunds");
        trainDAO.updateBookedById(ticket.getTrain(),ticket.getTrain().getBooked()+1);
        userDAO.substractMoneyOfUser(ticket.getOwner(),ticket.getCost());
        ticketDAO.insertTicketInDataBase(ticket);
    }
    public List<Ticket> getPaginatedTicketsOfUser(User user, int page) throws DAOException {
        return ticketDAO.getPaginatedEnableTicketsOfUser(user,page,5);

    }
    }

