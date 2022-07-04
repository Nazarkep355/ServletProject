package com.example.finalproject3.Contolllers;

import com.example.finalproject3.FrontController.ICommand;
import com.example.finalproject3.Utility.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterPageController implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String error= (String) request.getSession().getAttribute("error");
        if(error!=null){
            request.setAttribute("error",error);
            request.getSession().setAttribute("error",null);
        }
        return "Register.jsp";
    }
}
