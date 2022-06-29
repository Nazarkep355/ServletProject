package com.example.finalproject3.Contolllers;

import com.example.finalproject3.DAO.UserDAO;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.FrontController.ICommand;
import com.example.finalproject3.Services.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeMoneyController implements ICommand {
    static private Logger logger = Logger.getLogger(ChangeMoneyController.class);
    UserService userService;
    public ChangeMoneyController(){
        userService = new UserService();
    }

    public ChangeMoneyController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try{User user = (User) request.getSession().getAttribute("user");
            if(user==null){
                request.getSession().setAttribute("error","NoPermission");
                request.setAttribute("redirect",true);
                return "/";
            }
            int money= Integer.parseInt(request.getParameter("money"));
            userService.addUserMoney(user,money);
            return "/";
        } catch (Exception e) {
            logger.info(e);
            throw new RuntimeException(e);
        }

    }
}
