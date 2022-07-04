package Controller;

import com.example.finalproject3.Contolllers.EnglishLocaleController;
import com.example.finalproject3.Contolllers.UkrainianLocaleController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

public class UkrainianLocaleControllerTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    @Test
    void executeTest(){
        when(request.getSession()).thenReturn(session);
        UkrainianLocaleController controller = new UkrainianLocaleController();
        when(request.getParameter("prev")).thenReturn("/");
        when(request.getContextPath()).thenReturn("");
        String result = controller.execute(request,response);
        Assertions.assertEquals("/",result);
        verify(session,atLeastOnce()).setAttribute("lang","ua");

    }
}
