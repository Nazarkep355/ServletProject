package Controller;

import com.example.finalproject3.Contolllers.HelloController;
import com.example.finalproject3.Entity.User;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelloControllerTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);

    @Test
    void executeTest(){
        when(request.getSession()).thenReturn(session);
        User user =new User();
        HelloController controller = new HelloController();

        //USER is NULL
        when(session.getAttribute("user")).thenReturn(null);
        String result = controller.execute(request,response);
        Assertions.assertEquals("home.jsp",result);

        //USER is not NULL
        when(session.getAttribute("user")).thenReturn(user);
        result = controller.execute(request,response);
        Assertions.assertEquals("AccountPage.jsp",result);



    }
}
