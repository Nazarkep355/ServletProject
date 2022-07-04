package Controller;

import com.example.finalproject3.Contolllers.TicketsController;
import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.TicketDAO;
import com.example.finalproject3.DAO.TrainDAO;
import com.example.finalproject3.DAO.UserDAO;
import com.example.finalproject3.Entity.Ticket;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.Services.TicketService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

public class TicketsControllerTest {

    UserDAO userDAO = mock(UserDAO.class);
    TicketDAO ticketDAO =mock(TicketDAO.class);
    TrainDAO trainDAO =mock(TrainDAO.class);
    TicketService ticketService;
    List<Ticket> ticketList;
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    public TicketsControllerTest(){
        ticketService =new TicketService(userDAO,ticketDAO,trainDAO);
        ticketList = new ArrayList<>();
        for(int i=0;i<5;i++){
            Ticket ticket = new Ticket.TicketBuilder().id(i+1).cost(15+i*10).orderDate(Date.from(Instant.now())).build();
        ticketList.add(ticket);
        }
    }
    @Test
    void executeTest() throws DAOException {
        User user = new User.UserBuilder().id(1).build();
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("page")).thenReturn("1");
        when(ticketDAO.getPaginatedEnableTicketsOfUser(user,1,5)).thenReturn(ticketList);
        TicketsController controller = new TicketsController(ticketService);
        //USER is NULL
        String result = controller.execute(request,response);
        Assertions.assertEquals("/",result);
        verify(session,times(1)).setAttribute("error","BeforeBuyingYou");


        //USER is NOT NULL
        when(session.getAttribute("user")).thenReturn(user);
        result = controller.execute(request,response);
        Assertions.assertEquals("Tickets.jsp",result);
        verify(request,times(1)).setAttribute("currentPage",1);

    }

}
