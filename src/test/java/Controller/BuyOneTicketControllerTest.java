package Controller;

import com.example.finalproject3.Adapter.TicketAdapter;
import com.example.finalproject3.Adapter.TrainAdapter;
import com.example.finalproject3.Contolllers.BuyOneTicketController;
import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.TicketDAO;
import com.example.finalproject3.DAO.TrainDAO;
import com.example.finalproject3.DAO.UserDAO;
import com.example.finalproject3.Entity.Ticket;
import com.example.finalproject3.Entity.Train;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.Services.TicketService;
import com.example.finalproject3.Services.UserService;
import com.example.finalproject3.Utility.EmailSessionBean;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;


public class BuyOneTicketControllerTest {

    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    HttpSession session = Mockito.mock(HttpSession.class);
    TicketService ticketService;
    UserService userService;
    UserDAO userDAO;
    TicketDAO ticketDAO;
    TrainDAO trainDAO;
    EmailSessionBean emailSessionBean;
    public BuyOneTicketControllerTest(){
        userDAO = Mockito.mock(UserDAO.class);
        ticketDAO = Mockito.mock(TicketDAO.class);
        trainDAO = Mockito.mock(TrainDAO.class);
        ticketService= new TicketService(userDAO,ticketDAO,trainDAO);
        userService = new UserService(userDAO);
        emailSessionBean = Mockito.mock(EmailSessionBean.class);
    }
    @Test
   public void executeTest() throws DAOException, MessagingException {

        //USER is NULL
        BuyOneTicketController controller = new BuyOneTicketController(ticketService,userService,emailSessionBean);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        String result = controller.execute(request,response);
        verify(session,times(1)).setAttribute("error","BeforeBuyingYou");
        Assertions.assertEquals("/",result);

        //USER IS NOT NULL AND NOT HAVE ENOUGH FUNDS
        User user = new User.UserBuilder().userType(0).money(100).build();
        Train train = new Train.TrainBuilder().cost(150).booked(2).seats(3).enabled(true).build();
        Ticket ticket = new Ticket(train);
        ticket.setOwner(user);
        when(request.getHeader("referer")).thenReturn("buyPage");
        try(MockedStatic<TicketAdapter> mockedStatic = Mockito.mockStatic(TicketAdapter.class)){
            mockedStatic.when(()->TicketAdapter.getTicketFromRequest(request)).thenReturn(ticket);
        when(session.getAttribute("user")).thenReturn(user);
        result = controller.execute(request,response);
        verify(session,times(1)).setAttribute("error","notEnoughFunds");
        Assertions.assertEquals("buyPage",result);
 }

        //USER IS NOT NULL AND SEATS IS BUSY
        train = new Train.TrainBuilder().cost(50).booked(3).seats(3).enabled(true).build();
        ticket = new Ticket(train);
        ticket.setOwner(user);
        try(MockedStatic<TicketAdapter> mockedStatic = Mockito.mockStatic(TicketAdapter.class)) {
            mockedStatic.when(() -> TicketAdapter.getTicketFromRequest(request)).thenReturn(ticket);
            result = controller.execute(request, response);
            verify(session, times(1)).setAttribute("error", "NoFreeSeats");
            Assertions.assertEquals("buyPage", result);
        }




        //USER IS NOT NULL AND TICKET IS BOUGHT
        train = new Train.TrainBuilder().cost(50).booked(2).seats(3).enabled(true).build();
        ticket = new Ticket(train);
        ticket.setOwner(user);
        try(MockedStatic<TicketAdapter> mockedStatic = Mockito.mockStatic(TicketAdapter.class)) {
            mockedStatic.when(() -> TicketAdapter.getTicketFromRequest(request)).thenReturn(ticket);
            result = controller.execute(request, response);
            verify(emailSessionBean, times(1)).sendEmailAboutTicketBuying(ticket,request);
            Assertions.assertEquals("/?command=tickets&page=1", result);
        }
    }
}
