package Controller;

import com.example.finalproject3.Adapter.TrainAdapter;
import com.example.finalproject3.Contolllers.TrainInfoController;
import com.example.finalproject3.Entity.Train;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

public class TrainInfoControllerTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);

    @Test
    void executeTest(){
        TrainInfoController controller = new TrainInfoController();
        Train train = new Train.TrainBuilder().id(1).build();
        when(session.getAttribute("error")).thenReturn(null);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("from")).thenReturn(null);
        when(request.getParameter("to")).thenReturn(null);
        try(MockedStatic<TrainAdapter> mockedStatic = mockStatic(TrainAdapter.class)){
            mockedStatic.when(()->TrainAdapter.getTrainInfoFromRequest(request)).thenReturn(train);
            String result = controller.execute(request,response);
            verify(request,times(1)).setAttribute("train",train);
            Assertions.assertEquals("InfoAboutTrain.jsp",result);

        }
    }
}
