package Controller;

import com.example.finalproject3.Contolllers.AddStationPageController;
import com.example.finalproject3.Entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class AddStationPageControllerTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    @Test
    void executeTest(){
        //USER is NULL
        AddStationPageController controller = new AddStationPageController();
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


        //USER is ADMIN
        user = new User.UserBuilder().userType(1).build();
        when(session.getAttribute("user")).thenReturn(user);
        result = controller.execute(request,response);
        Assertions.assertEquals("AddStation.jsp",result);

    }


}
