package com.example.finalproject3.Contolllers;

import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.FrontController.ICommand;
import com.example.finalproject3.Services.UserService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class RegisterUserController implements ICommand {
    static private Logger logger = Logger.getLogger(RegisterUserController.class);
    UserService userService;
    public RegisterUserController(){
        userService = new UserService();
    }

    public RegisterUserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String email=request.getParameter("email");
        try {
            if(userService.isEmailExists(email)){
                request.getSession().setAttribute("error","EmailInUse");
                return "/?command=registerPage";
            }else {
                String password = request.getParameter("password");
                String name = request.getParameter("name");
                User user = userService.registerUser(email, password, name);
                request.getSession().setAttribute("user", user);
                return "/";
            }
        } catch (Throwable e) {
            logger.info(e);
            throw new RuntimeException(e);
        }
    }
}
