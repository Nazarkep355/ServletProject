package Controller;

import com.example.finalproject3.Contolllers.PlanTrainPageController;
import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.RouteDAO;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.Services.RouteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class PlanTrainPageControllerTest {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RouteService routeService;
        RouteDAO routeDAO;
        public PlanTrainPageControllerTest(){
            routeDAO = mock(RouteDAO.class);
            routeService = new RouteService(routeDAO);
        }
        @Test
        void executeTest() throws DAOException {
            PlanTrainPageController controller = new PlanTrainPageController(routeService);
            when(request.getSession()).thenReturn(session);

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
            result = controller.execute(request,response);
            Assertions.assertEquals("PlanTrain.jsp",result);
            verify(routeDAO,times(1)).getAllRoutes();


        }


}
