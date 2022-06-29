package com.example.finalproject3.Contolllers;

import com.example.finalproject3.FrontController.ICommand;
import com.example.finalproject3.Services.UserService;
import com.example.finalproject3.Utility.Utility;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EnglishLocaleController implements ICommand {
    static private Logger logger = Logger.getLogger(EnglishLocaleController.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            Utility.updateUser(request);
        }catch (Throwable e ){
            logger.info(e);
            throw new RuntimeException(e);
        }
        HttpSession session = request.getSession();
        session.setAttribute("lang","en");
        Utility.setString(request);
        return request.getContextPath()+request.getParameter("prev");
         }
    }

