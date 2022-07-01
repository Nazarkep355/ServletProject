package Controller;

import com.example.finalproject3.Contolllers.DoneOperationController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

public class DoneOperationControllerTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    @Test
    void executeTest(){
        when(request.getSession()).thenReturn(session);
        DoneOperationController controller = new DoneOperationController();
        when(request.getParameter("title")).thenReturn("StationAdded");
        String result =controller.execute(request,response);
        Assertions.assertEquals("OperationDone.jsp",result);
        verify(request,atLeastOnce()).setAttribute("title","Station added");

    }
}
