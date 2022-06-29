package Controller;

import com.example.finalproject3.Adapter.TrainAdapter;
import com.example.finalproject3.Contolllers.AddStationPageController;
import com.example.finalproject3.Contolllers.CancelTrainController;
import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.TicketDAO;
import com.example.finalproject3.DAO.TrainDAO;
import com.example.finalproject3.DAO.UserDAO;
import com.example.finalproject3.Entity.Ticket;
import com.example.finalproject3.Entity.Train;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.Services.TrainService;
import com.example.finalproject3.Utility.EmailSessionBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class CancelTrainControllerTest {
    TrainService trainService;
    UserDAO userDAO;
    TicketDAO ticketDAO;
    TrainDAO trainDAO;
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    EmailSessionBean emailSessionBean;
    public CancelTrainControllerTest(){
        emailSessionBean = mock(EmailSessionBean.class);
        userDAO = mock(UserDAO.class);
        ticketDAO = mock(TicketDAO.class);
        trainDAO = mock(TrainDAO.class);
        trainService = new TrainService(userDAO,ticketDAO,trainDAO);
    }
    @Test
    void executeTest() throws DAOException, MessagingException {
        CancelTrainController controller = new CancelTrainController(trainService,emailSessionBean);
        when(request.getSession()).thenReturn(session);


        //USER is NULL
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);//USER is NULL
        String result = controller.execute(request,response);
        verify(session,times(1)).setAttribute("error","NoPermission");
        Assertions.assertEquals("/",result);


        //USER is NOT NULL AND NOT ADMIN
        User user = new User.UserBuilder().userType(0).build();
        when(session.getAttribute("user")).thenReturn(user);
        result = controller.execute(request,response);
        verify(session,times(2)).setAttribute("error","NoPermission");
        Assertions.assertEquals("/",result);


        //USER is ADMIN AND TRAIN is CANCELED
        user = new User.UserBuilder().userType(1).build();
        when(session.getAttribute("user")).thenReturn(user);
        try(MockedStatic<TrainAdapter> adapterMockedStatic = Mockito.mockStatic(TrainAdapter.class)){
        Train train = new Train.TrainBuilder().enabled(true).id(0).build();
        adapterMockedStatic.when(()->TrainAdapter.getTrainFromRequest(request)).thenReturn(train);
        result = controller.execute(request,response);
        verify(emailSessionBean,atLeastOnce()).sendEmailAboutTrainCanceling(train,request);
        verify(ticketDAO,atLeastOnce()).getAllEnableTicketsOnTrain(train);
        Assertions.assertEquals("?command=operDone&title=TrainCanceled",result);
        }
    }
}
