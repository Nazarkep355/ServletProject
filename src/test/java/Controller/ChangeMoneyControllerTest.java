package Controller;

import com.example.finalproject3.Contolllers.ChangeMoneyController;
import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.UserDAO;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.Services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ChangeMoneyControllerTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    UserDAO userDAO;
    UserService userService;
    ChangeMoneyControllerTest(){
        userDAO = mock(UserDAO.class);
        userService = new UserService(userDAO);
    }
    @Test
    void executeTest() throws DAOException {
        ChangeMoneyController controller = new ChangeMoneyController(userService);
        when(request.getSession()).thenReturn(session);


        //USER is NULL
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);//USER is NULL
        String result = controller.execute(request,response);
        verify(session,times(1)).setAttribute("error","NoPermission");
        Assertions.assertEquals("/",result);

        //USER is not NULL
        User user = new User.UserBuilder().userType(0).build();

        when(request.getParameter("money")).thenReturn("150");
        when(session.getAttribute("user")).thenReturn(user);
        when(userDAO.isUserWithIdExists(0)).thenReturn(true);
        result = controller.execute(request,response);
        Assertions.assertEquals("/",result);
        verify(userDAO,atLeastOnce()).addMoneyOfUser(user,150);

    }
}
