package Controller;

import com.example.finalproject3.Contolllers.SignOutController;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

public class SignOutControllerTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    @Test
    void executeTest(){
        when(request.getSession()).thenReturn(session);
        SignOutController controller = new SignOutController();
        String result = controller.execute(request,response);
        verify(session,times(1)).setAttribute("user",null);
    }
}
