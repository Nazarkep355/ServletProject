package Controller;

import com.example.finalproject3.Contolllers.StationsController;
import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.StationDAO;
import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Services.StationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class StationsControllerTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    List<Station> stationList;
    StationDAO stationDAO;
    StationService stationService;
    public StationsControllerTest(){
        stationList = new ArrayList<>();
        for(int i=0;i<5;i++){
            stationList.add(new Station("Station"+(i+1)));
        }
        stationDAO = mock(StationDAO.class);
        stationService = new StationService(stationDAO);
    }

    @Test
    void executeTest() throws DAOException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("page")).thenReturn("1");
        when(session.getAttribute("user")).thenReturn(null);
        when(stationDAO.getPaginatedStations(1)).thenReturn(stationList);
        StationsController controller = new StationsController(stationService);
        String result = controller.execute(request,response);
        Assertions.assertEquals("Stations.jsp",result);
        verify(request,times(1)).setAttribute("isLogged",false);


    }
}
