package com.example.finalproject3.Contolllers;

import com.example.finalproject3.Entity.User;
import com.example.finalproject3.FrontController.ICommand;
import com.example.finalproject3.Services.UserService;
import com.example.finalproject3.Utility.Utility;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeMoneyPageController implements ICommand {
    static private Logger logger = Logger.getLogger(ChangeMoneyPageController.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            if(user==null){
                request.getSession().setAttribute("error","NoPermission");
                request.setAttribute("redirect",true);
                return "/";
            }
            return "ChangeMoney.jsp";
        } catch (Throwable e) {
            logger.info(e);
            throw new RuntimeException(e);
        }


    }
}
