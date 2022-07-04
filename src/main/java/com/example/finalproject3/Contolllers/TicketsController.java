package com.example.finalproject3.Contolllers;

import com.example.finalproject3.Entity.Ticket;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.FrontController.ICommand;
import com.example.finalproject3.Services.TicketService;
import com.example.finalproject3.Services.UserService;
import com.example.finalproject3.Utility.Utility;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

public class TicketsController implements ICommand {
    static private Logger logger = Logger.getLogger(TicketService.class);
    TicketService ticketService;
    public TicketsController(){
        ticketService = new TicketService();
    }

    public TicketsController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {User user = (User) request.getSession().getAttribute("user");
            if(user==null){
                request.getSession().setAttribute("error","BeforeBuyingYou");
                request.setAttribute("redirect",true);
                return "/";}
            int page = Integer.parseInt(request.getParameter("page"));
            Utility.updateUser(request);
            List<Ticket> tickets = ticketService.getPaginatedTicketsOfUser(user, page);
            request.setAttribute("currentPage",page);
            request.setAttribute("tickets", tickets.stream().sorted((a,b)->b.getDate()
                    .compareTo(a.getDate())).collect(Collectors.toList()));
            return "Tickets.jsp";
        } catch (Throwable e) {
            logger.info(e);
            throw new RuntimeException(e);
        }

    }
}
