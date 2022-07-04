package Controller;

import com.example.finalproject3.Contolllers.RegisterPageController;
import com.example.finalproject3.Contolllers.RegisterUserController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

public class RegisterPageControllerTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);


   @Test
    void executeTest(){
       when(request.getSession()).thenReturn(session);
       when(session.getAttribute("user")).thenReturn(null);
       RegisterPageController controller = new RegisterPageController();
       String result = controller.execute(request,response);
       Assertions.assertEquals("Register.jsp",result);
   }


}
