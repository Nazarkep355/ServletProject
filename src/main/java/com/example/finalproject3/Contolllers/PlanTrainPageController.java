package com.example.finalproject3.Contolllers;

import com.example.finalproject3.DAO.RouteDAO;
import com.example.finalproject3.Entity.Route;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.FrontController.ICommand;
import com.example.finalproject3.Services.UserService;
import com.example.finalproject3.Utility.Utility;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PlanTrainPageController implements ICommand {
    static private Logger logger = Logger.getLogger(PlanTrainPageController.class);
    RouteDAO routeDAO = new RouteDAO();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            if(user==null||!user.isAdmin()){
                request.getSession().setAttribute("error","NoPermission");
                request.setAttribute("redirect",true);
                return "/";
            }
            Utility.updateUser(request);
            List<Route> routes= routeDAO.getAllRoutes();
            request.setAttribute("routes",routes);
        } catch (Exception e) {
            logger.info(e);
            throw new RuntimeException(e);
        }
        return "PlanTrain.jsp";
    }
}
