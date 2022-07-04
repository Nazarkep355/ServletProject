package Controller;

import com.example.finalproject3.Contolllers.LoginController;
import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.UserDAO;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.Services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class LoginControllerTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    UserDAO userDAO;
    List<User> userList;
    UserService userService;
    public LoginControllerTest(){
        userDAO = mock(UserDAO.class);
        userService = new UserService(userDAO);
        userList = new ArrayList<>();
        User user = new User.UserBuilder().email("email@email.com").password("password").build();
        userList.add(user);
    }
    @Test
    void executeTest() throws DAOException {
        when(request.getSession()).thenReturn(session);
        when(userDAO.isUserWithEmailExists("email@email.com")).thenReturn(true);
        when(userDAO.getUserByEmail("email@email.com")).thenReturn(userList.get(0));
        LoginController controller = new LoginController(userService);


        //USER has email "email@email.com" and password "password"
        //NOW REQUEST HAS INCORRECT EMAIL
        when(request.getParameter("email")).thenReturn("wrongemail@gmail.com");
        when(request.getParameter("password")).thenReturn("wrongpass");
        String result = controller.execute(request,response);
        Assertions.assertEquals("/",result);
        verify(session,times(1)).setAttribute("error","Wrong password");
        verify(session,times(0)).setAttribute("user", userList.get(0));


        //NOW REQUEST HAS INCORRECT PASSWORD
        when(request.getParameter("email")).thenReturn("email@email.com");
        when(request.getParameter("password")).thenReturn("wrongpass");
        result = controller.execute(request,response);
        Assertions.assertEquals("/",result);
        verify(session,times(2)).setAttribute("error","Wrong password");
        verify(session,times(0)).setAttribute("user", userList.get(0));


        //NOW USER HAS CORRECT ENTERED DATA
        when(request.getParameter("email")).thenReturn("email@email.com");
        when(request.getParameter("password")).thenReturn("password");
        result = controller.execute(request,response);
        Assertions.assertEquals("/",result);
        verify(session,times(2)).setAttribute("error","Wrong password");
        verify(session,times(1)).setAttribute("user", userList.get(0));


    }
}
