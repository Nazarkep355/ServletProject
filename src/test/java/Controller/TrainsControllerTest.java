package Controller;

import com.example.finalproject3.Contolllers.TrainsController;
import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.TicketDAO;
import com.example.finalproject3.DAO.TrainDAO;
import com.example.finalproject3.DAO.UserDAO;
import com.example.finalproject3.Entity.Train;
import com.example.finalproject3.Services.TrainService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TrainsControllerTest {

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    List<Train> trainList;
    TrainDAO trainDAO;
    TicketDAO ticketDAO;
    UserDAO userDAO;
    TrainService trainService;
    public TrainsControllerTest(){
        trainDAO = mock(TrainDAO.class);
        ticketDAO = mock(TicketDAO.class);
        userDAO =mock(UserDAO.class);
        trainService = new TrainService(userDAO,ticketDAO,trainDAO);
        trainList = new ArrayList<>();
        for(int i=0;i<5;i++){
            Train train = new Train.TrainBuilder().id(i+1).cost(150+i*20).build();
            trainList.add(train);
        }
    }
    @Test
    void executeTest() throws DAOException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("page")).thenReturn("1");
        when(trainDAO.paginatedListOfEnableTrains(1,5)).thenReturn(trainList);
        TrainsController controller = new TrainsController(trainService);
        String result = controller.execute(request,response);
        Assertions.assertEquals("Trains.jsp",result);
        verify(request,times(1)).setAttribute("trains",trainList);
    }

}
