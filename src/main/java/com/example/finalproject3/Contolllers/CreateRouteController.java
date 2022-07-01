package com.example.finalproject3.Contolllers;

import com.example.finalproject3.Adapter.RouteAdapter;
import com.example.finalproject3.DAO.RouteDAO;
import com.example.finalproject3.Entity.Route;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.FrontController.ICommand;
import com.example.finalproject3.Services.RouteService;
import com.example.finalproject3.Services.UserService;
import com.example.finalproject3.Utility.Utility;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateRouteController implements ICommand {
    static private Logger logger = Logger.getLogger(CreateRouteController.class);
    RouteService routeService;
    public CreateRouteController(){
        routeService = new RouteService();
    }

    public CreateRouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try{User user = (User) request.getSession().getAttribute("user");
            if(user==null||!user.isAdmin()){
                request.getSession().setAttribute("error","NoPermission");
                request.setAttribute("redirect",true);
                return "/";
            }
            Utility.updateUser(request);
            Route route = RouteAdapter.getRouteFromRequest(request);
            if(route==null){
                request.getSession().setAttribute("error","station not found");
                return "/?command=createRoutePage";
            }
            else
            {
                routeService.addRouteToDataBase(route);
                return "/?command=operDone&title=RouteCreated";
            }
        } catch (Exception e) {
            logger.info(e);
            throw new RuntimeException(e);
        }
    }
}
