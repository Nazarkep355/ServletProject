package com.example.finalproject3.Contolllers;


import com.example.finalproject3.Entity.User;
import com.example.finalproject3.FrontController.ICommand;
import com.example.finalproject3.Services.UserService;
import com.example.finalproject3.Utility.Utility;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController implements ICommand {
    static private Logger logger =Logger.getLogger(LoginController.class);
    private UserService userService;
    public LoginController(){
        userService = new UserService();
    }

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String email =request.getParameter("email");
        String password = request.getParameter("password");
        User user = new User();
        try {
            if(!userService.isEmailExists(email))
            {request.getSession().setAttribute("error","Wrong password");
                return "/";}
            else
            if(!userService.isPasswordCorrect(email,password))
            {request.getSession().setAttribute("error","Wrong password");
                return "/";}
            else {user = userService.getUserByEmail(email);
                Utility.updateUser(request);
                request.getSession().setAttribute("user",user);
                return "/";}
        } catch (Throwable e) {
            logger.info(e);
            throw new RuntimeException(e); }
    }
}
