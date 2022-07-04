package Controller;

import com.example.finalproject3.Adapter.TrainAdapter;
import com.example.finalproject3.Contolllers.PlanTrainController;
import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.TicketDAO;
import com.example.finalproject3.DAO.TrainDAO;
import com.example.finalproject3.DAO.UserDAO;
import com.example.finalproject3.Entity.Train;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.Services.TrainService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class PlanTrainControllerTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    UserDAO userDAO = mock(UserDAO.class);
    TrainDAO trainDAO =mock(TrainDAO.class);
    TicketDAO ticketDAO =mock(TicketDAO.class);
    TrainService trainService;
    public PlanTrainControllerTest(){
        trainService = new TrainService(userDAO,ticketDAO,trainDAO);

    }
    @Test
    void executeTest() throws DAOException {
        Train train = new Train.TrainBuilder().id(1).cost(150).enabled(true).build();
        when(request.getSession()).thenReturn(session);
        PlanTrainController controller = new PlanTrainController(trainService);

        //USER is NULL
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        String result = controller.execute(request,response);
        verify(session,times(1)).setAttribute("error","NoPermission");
        Assertions.assertEquals("/",result);


        //USER is NOT NULL AND NOT ADMIN
        User user = new User.UserBuilder().userType(0).build();
        when(session.getAttribute("user")).thenReturn(user);
        result = controller.execute(request,response);
        verify(session,times(2)).setAttribute("error","NoPermission");
        Assertions.assertEquals("/",result);

        //USER is ADMIN
        user = new User.UserBuilder().userType(1).build();
        when(session.getAttribute("user")).thenReturn(user);
        try(MockedStatic<TrainAdapter> mockedStatic = mockStatic(TrainAdapter.class)){
            mockedStatic.when(()->TrainAdapter.getTrainFromRequest(request)).thenReturn(train);
            result = controller.execute(request,response);
            Assertions.assertEquals("?command=operDone&title=TrainPlanned",result);
            verify(trainDAO,atLeastOnce()).insertTrainInDataBase(train);

        }


    }

}
