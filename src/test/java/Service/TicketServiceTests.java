package Service;

import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.TicketDAO;
import com.example.finalproject3.DAO.TrainDAO;
import com.example.finalproject3.DAO.UserDAO;
import com.example.finalproject3.Entity.Route;
import com.example.finalproject3.Entity.Ticket;
import com.example.finalproject3.Entity.Train;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.Services.TicketService;
import com.example.finalproject3.Services.UserService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class TicketServiceTests {
    @Mock
    UserDAO userDAO;
    TicketService ticketService;
    @Mock
    TrainDAO trainDAO;
    @Mock
    TicketDAO ticketDAO;


    ArrayList<Ticket> ticketDataBase = new ArrayList<>();

    ArrayList<Train> trainDataBase = new ArrayList<>();

    ArrayList<User> userDataBase = new ArrayList<>();
     public TicketServiceTests(){
         MockitoAnnotations.initMocks(this);
         userDataBase.add(new User.UserBuilder().id(1).money(200).build());
         userDataBase.add(new User.UserBuilder().id(2).money(450).build());
         trainDataBase.add(new Train.TrainBuilder().route(new Route()).id(1).booked(2).seats(4).cost(100).build());
         trainDataBase.add(new Train.TrainBuilder().route(new Route()).id(2).booked(0).seats(4).cost(50).build());
         ticketDataBase.add(new Ticket.TicketBuilder().train(trainDataBase.get(0)).cost(100).id(1).owner(userDataBase.get(0)).build());
         ticketDataBase.add(new Ticket.TicketBuilder().train(trainDataBase.get(0)).cost(100).id(2).owner(userDataBase.get(1)).build());
     }
     @Test
     public void addTicketToWithChangesTest() throws DAOException {
         Ticket ticket1 = new Ticket(trainDataBase.get(0));
         ticket1.setOwner(userDataBase.get(0));
         Ticket ticket2 = new Ticket(trainDataBase.get(0));
         ticket2.setOwner(userDataBase.get(1));
         Ticket ticket3 = new Ticket(trainDataBase.get(0));
         ticket3.setOwner(userDataBase.get(1));
        ticketService = new TicketService(userDAO,ticketDAO,trainDAO);
        when(userDAO.substractMoneyOfUser(userDataBase.get(0),100))
                .thenReturn(userDataBase.get(0).setMoney(100));
        when(userDAO.substractMoneyOfUser(userDataBase.get(0),50))
                .thenReturn(userDataBase.get(0)
                        .setMoney(userDataBase.get(0).getMoney()-50));
        when(trainDAO.updateBookedById(trainDataBase.get(0),trainDataBase.get(0)
                .getBooked()+1))
                .thenReturn(trainDataBase.get(0)
                        .setBooked(trainDataBase.get(0).getBooked()+1));
        when(ticketDAO.insertTicketInDataBase(ticket1))
                .thenReturn(ticketDataBase.add(ticket1));
         when(ticketDAO.insertTicketInDataBase(ticket2))
                 .thenReturn(ticketDataBase.add(ticket2));

         Assertions.assertThrows(IllegalArgumentException.class,()->ticketService.addTicketToWithChanges(ticket1),"notEnoughFunds");
            trainDataBase.get(0).setBooked(4);
         Assertions.assertThrows(IllegalArgumentException.class,()->ticketService.addTicketToWithChanges(ticket3),"NoFreeSeats");



     }
  }


