package Service;

import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.UserDAO;
import com.example.finalproject3.Entity.Train;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.Services.UserService;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import org.mockito.*;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.when;


public class UserServiceTest {
    @Mock
    UserDAO userDAO;
    UserService userService;
    List<User> userDB = new ArrayList<>();
    public UserServiceTest() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userDAO);
        userDB.add(new User.UserBuilder()
                .email("user@gmail.com")
                .password("password")
                .build());
        userDB.add(new User.UserBuilder()
                .email("user2@gmail.com")
                .password("12345678")
                .build());
    }


    @Test
    public void isPasswordCorrectTest() throws DAOException {

            BDDMockito.given(userDAO.getUserByEmail("user@gmail.com"))
                    .willReturn(new User.UserBuilder()
                            .email("user@gmail.com")
                            .password("password")
                            .build());
            BDDMockito.given(userDAO.getUserByEmail("user2@gmail.com"))
                    .willReturn(new User.UserBuilder()
                            .email("user2@gmail.com")
                            .password("12345678")
                            .build());
            User user = userDAO.getUserByEmail("user@gmail.com");
            Assertions.assertEquals("user@gmail.com", user.getEmail());
            Assertions.assertEquals("password",user.getPassword());
            Assertions.assertTrue(userService.isPasswordCorrect("user@gmail.com","password"));
            Assertions.assertFalse(userService.isPasswordCorrect("user@gmail.com","12345678"));
            Assertions.assertTrue(userService.isPasswordCorrect("user2@gmail.com","12345678"));
            Assertions.assertFalse(userService.isPasswordCorrect("user2@gmail.com","password"));

    }
    @Test
    public void isEmailExistsTest() throws DAOException {
        when(userDAO.isUserWithEmailExists("user@gmail.com"))
                .thenReturn(userDB.stream()
                        .anyMatch((a)->a.getEmail().equals("user@gmail.com")));
        when(userDAO.isUserWithEmailExists("user2@gmail.com"))
                .thenReturn(userDB.stream()
                        .anyMatch((a)->a.getEmail().equals("user2@gmail.com")));
        Assertions.assertTrue(userService.isEmailExists("user@gmail.com"));
        Assertions.assertTrue(userService.isEmailExists("user2@gmail.com"));
        Assertions.assertFalse(userService.isEmailExists("user3@gmail.com"));
    }
    @Test
    public void getUserByEmailTest() throws DAOException {
        when(userDAO.getUserByEmail("user@gmail.com"))
                .thenReturn(userDB.stream()
                        .filter(a->a.getEmail().equals("user@gmail.com"))
                        .findFirst().get());
        when(userDAO.getUserByEmail("user2@gmail.com"))
                .thenReturn(userDB.stream()
                        .filter(a->a.getEmail().equals("user2@gmail.com"))
                        .findFirst().get());
        User user = userService.getUserByEmail("user@gmail.com");
        Assertions.assertTrue(user.getEmail().equals("user@gmail.com"));
        Assertions.assertTrue(user.getPassword().equals("password"));
        user = userService.getUserByEmail("user2@gmail.com");
        Assertions.assertTrue(user.getEmail().equals("user2@gmail.com"));
        Assertions.assertTrue(user.getPassword().equals("12345678"));
        Assertions.assertEquals(null,userService.getUserByEmail("email@emai.com"));
    }
    @Test
    public void registerUserTest() throws DAOException {
        String email = "email@gmail.com";
        String password = "12345678";
        String name = "User256";

        User expected = new User.UserBuilder().email(email)
                .password(password).name(name).userType(0).money(0).build();
        when(userDAO.getUserByEmail(email))
                .thenReturn(expected);
        User actual = userService.registerUser(email,password,name);
        Assertions.assertEquals(expected,actual);
        Mockito.verify(userDAO,atLeastOnce()).insertUserInDataBase(expected);
    }

}
