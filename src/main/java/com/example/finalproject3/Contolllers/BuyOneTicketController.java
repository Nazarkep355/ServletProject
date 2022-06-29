package com.example.finalproject3.Contolllers;

import com.example.finalproject3.Adapter.TicketAdapter;
import com.example.finalproject3.Entity.Ticket;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.FrontController.ICommand;
import com.example.finalproject3.Services.TicketService;
import com.example.finalproject3.Services.UserService;
import com.example.finalproject3.Utility.EmailSessionBean;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BuyOneTicketController implements ICommand {
    static private Logger logger =Logger.getLogger(BuyOneTicketController.class);
    private TicketService ticketService;
    private UserService userService;
    EmailSessionBean emailSessionBean ;
    public BuyOneTicketController(){
        userService= new UserService();
        ticketService = new TicketService();
        emailSessionBean = new EmailSessionBean();
    }

    public BuyOneTicketController(TicketService ticketService, UserService userService, EmailSessionBean emailSessionBean) {
        this.ticketService = ticketService;
        this.userService = userService;
        this.emailSessionBean = emailSessionBean;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try{
            User user = (User) request.getSession().getAttribute("user");
            if (user==null){
             request.getSession().setAttribute("error","BeforeBuyingYou");
             request.setAttribute("redirect",true);
             return "/";
            }
            Ticket ticket = TicketAdapter.getTicketFromRequest(request);
           try{ ticketService.addTicketToWithChanges(ticket);}
           catch (IllegalArgumentException e){
               request.getSession().setAttribute("error",e.getMessage());
               return request.getHeader("referer");
           }

            emailSessionBean.sendEmailAboutTicketBuying(ticket,request);
            return "/?command=tickets&page=1";
        } catch (Throwable e) {
            logger.info(e);
            throw new RuntimeException(e);
        }
    }
}
