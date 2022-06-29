package Controller;

import com.example.finalproject3.Contolllers.AddStationController;
import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.StationDAO;
import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.Services.StationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoSession;
import org.mockito.session.MockitoSessionBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

public class AddStationControllerTest {
    StationDAO stationDAO;
    StationService stationService;
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    public AddStationControllerTest(){
       stationDAO =mock(StationDAO.class);
       stationService = new StationService(stationDAO);

    }
    @Test
    void AddStationExecuteTest() throws DAOException {


        //USER is NULL
        Station addedStation = new Station("False");
        Station notAddedStation = new Station("True");
        AddStationController controller = new AddStationController(stationService);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);//USER is NULL
        String result = controller.execute(request,response);
        verify(session,times(1)).setAttribute("error","NoPermission");
        Assertions.assertEquals("/",result);
        when(stationDAO.isStationExist(addedStation)).thenReturn(true);
        when(stationDAO.isStationExist(notAddedStation)).thenReturn(false);


        //USER is NOT NULL AND NOT ADMIN
        User user = new User.UserBuilder().userType(0).build();
        when(session.getAttribute("user")).thenReturn(user);
        result = controller.execute(request,response);
        verify(session,times(2)).setAttribute("error","NoPermission");
        Assertions.assertEquals("/",result);




        //USER is ADMIN and STATION IN DB
        user = new User.UserBuilder().userType(1).build();
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("station")).thenReturn("False");
        result = controller.execute(request,response);
        Assertions.assertEquals("/?command=addStationPage",result);


        //USER is ADMIN and STATION NOT IN DB
        when(request.getParameter("station")).thenReturn("True");
        result = controller.execute(request,response);
        Assertions.assertEquals("/?command=operDone&title=StationAdded",result);

    }
}
