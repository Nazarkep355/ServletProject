package Controller;

import com.example.finalproject3.Contolllers.RegisterUserController;
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

public class RegisterUserControllerTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    UserDAO userDAO;
    UserService userService;
    public RegisterUserControllerTest(){
        userDAO = mock(UserDAO.class);
        userService = new UserService(userDAO);
    }
    @Test
    void executeTest() throws DAOException {
        String registeredEmail = "email@gmail.com";
        String unregisteredEmail = "user@gmail.com";
        String unregisteredPassword = "password";
        String unregisteredName = "user1";
        when(request.getSession()).thenReturn(session);
        User unregisteredUser = new User.UserBuilder().userType(0)
                .id(1).email(unregisteredEmail)
                .password(unregisteredPassword)
                .name(unregisteredName).build();
        RegisterUserController controller = new RegisterUserController(userService);
        when(userDAO.isUserWithEmailExists(registeredEmail)).thenReturn(true);
        when(userDAO.isUserWithEmailExists(unregisteredEmail)).thenReturn(false);

        //Email is already in DataBase
        when(request.getParameter("email")).thenReturn(registeredEmail);
        String result = controller.execute(request,response);
        Assertions.assertEquals("/?command=registerPage",result);
        verify(session,times(1)).setAttribute("error","EmailInUse");

        //Email is not in database
        when(request.getParameter("email")).thenReturn(unregisteredEmail);
        when(request.getParameter("password")).thenReturn(unregisteredPassword);
        when(request.getParameter("name")).thenReturn(unregisteredName);
        when(userDAO.getUserByEmail(unregisteredEmail)).thenReturn(unregisteredUser);
        result = controller.execute(request,response);
        Assertions.assertEquals("/",result);
        verify(session,times(1)).setAttribute("user",unregisteredUser);

    }

}
