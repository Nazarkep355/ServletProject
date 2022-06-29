package com.example.finalproject3.Contolllers;

import com.example.finalproject3.FrontController.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignOutController implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("user",null);
        return "/";
    }
}
