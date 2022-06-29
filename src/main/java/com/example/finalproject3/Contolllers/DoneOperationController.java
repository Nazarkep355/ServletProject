package com.example.finalproject3.Contolllers;

import com.example.finalproject3.FrontController.ICommand;
import com.example.finalproject3.Services.UserService;
import com.example.finalproject3.Utility.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class DoneOperationController implements ICommand {
    static private Logger logger =Logger.getLogger(DoneOperationController.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try{

        Utility.updateUser(request);
            String title =request.getParameter("title");
            title=Utility.getBundle(request).getString(title);
            request.setAttribute("title",title);
            return"OperationDone.jsp";
        }catch (Throwable e){
            logger.info(e);
            throw new RuntimeException(e);
        }
    }
}
