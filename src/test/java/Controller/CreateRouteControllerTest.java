package Controller;

import com.example.finalproject3.Adapter.RouteAdapter;
import com.example.finalproject3.Adapter.TrainAdapter;
import com.example.finalproject3.Contolllers.AddStationController;
import com.example.finalproject3.Contolllers.CreateRouteController;
import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.RouteDAO;
import com.example.finalproject3.Entity.Route;
import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.Services.RouteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class CreateRouteControllerTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    RouteDAO routeDAO = mock(RouteDAO.class);
    RouteService routeService;
    public CreateRouteControllerTest(){
    routeService = new RouteService(routeDAO);

    }
    @Test
    void executeTest() throws DAOException {
        CreateRouteController controller = new CreateRouteController(routeService);
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



        //AT LEAST ONE Station is NOT in DATABASE
        user = new User.UserBuilder().userType(1).build();
        when(session.getAttribute("user")).thenReturn(user);
        try(MockedStatic<RouteAdapter> mockedStatic = Mockito.mockStatic(RouteAdapter.class)) {
            mockedStatic.when(()->RouteAdapter.getRouteFromRequest(request)).thenReturn(null);
        result = controller.execute(request,response);
        Assertions.assertEquals("/?command=createRoutePage",result);
        verify(session,atLeastOnce()).setAttribute("error","station not found");
            }




        //ALL STATIONS in DATABASE
        Route route = new Route();
        try(MockedStatic<RouteAdapter> mockedStatic = Mockito.mockStatic(RouteAdapter.class)) {
            mockedStatic.when(()->RouteAdapter.getRouteFromRequest(request)).thenReturn(route);
            result = controller.execute(request,response);
            Assertions.assertEquals("/?command=operDone&title=RouteCreated",result);
            verify(routeDAO,atLeastOnce()).insertRouteInDataBase(route);
        }


    }


}
