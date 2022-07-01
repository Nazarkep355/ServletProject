package Controller;

import com.example.finalproject3.Contolllers.FindTrainByOneStationController;
import com.example.finalproject3.Contolllers.FindTrainsByTwoStationsController;
import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.TicketDAO;
import com.example.finalproject3.DAO.TrainDAO;
import com.example.finalproject3.DAO.UserDAO;
import com.example.finalproject3.Entity.Station;
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
import static org.mockito.Mockito.atLeastOnce;

public class FindTrainsByTwoStationsControllerTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    UserDAO userDAO = mock(UserDAO.class);
    TicketDAO ticketDAO= mock(TicketDAO.class);
    TrainDAO trainDAO = mock(TrainDAO.class);
    List<Train> trains;
    TrainService trainService;
    public FindTrainsByTwoStationsControllerTest(){
        trains = new ArrayList<>();
        trainService = new TrainService(userDAO,ticketDAO,trainDAO);
        for(int i =0;i<3;i++){
            Train train = new Train.TrainBuilder()
                    .id(i+1)
                    .enabled(true)
                    .cost(50*(i+1))
                    .build();
            trains.add(train);
        }
    }
    @Test
    void executeTest() throws DAOException {
        when(request.getSession()).thenReturn(session);
        Station station1 = new Station("Station1");
        Station station2 = new Station("Station2");
        FindTrainsByTwoStationsController controller = new FindTrainsByTwoStationsController(trainService);
        when(request.getParameter("page")).thenReturn("1");
        when(request.getParameter("station1")).thenReturn("Station1");
        when(request.getParameter("station2")).thenReturn("Station2");
        when(trainDAO.paginatedListOfEnabledTrainsByTwoStations(station1,station2,1,5)).thenReturn(trains);
        String result = controller.execute(request,response);
        Assertions.assertEquals("Trains.jsp",result);
        verify(request,atLeastOnce()).setAttribute("trains",trains);

    }
}
