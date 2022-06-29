package com.example.finalproject3.Contolllers;

import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.FrontController.ICommand;
import com.example.finalproject3.Services.StationService;
import com.example.finalproject3.Services.UserService;
import com.example.finalproject3.Utility.Utility;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddStationController implements ICommand {
    StationService stationService;
    public AddStationController(){
        stationService = new StationService();
    }
    public AddStationController(StationService stationService)
    {this.stationService = stationService;}
    static Logger logger= Logger.getLogger(AddStationController.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {  User user = (User) request.getSession().getAttribute("user");
        if(user==null||!user.isAdmin()){
            request.getSession().setAttribute("error","NoPermission");
            request.setAttribute("redirect",true);
            return "/";
        }
        String name = request.getParameter("station");
        Station station = new Station(name);
        Utility.updateUser(request);
            if(!stationService.AddStationInDataBase(station)){
                request.getSession().setAttribute("error","StationInDB");
                return "/?command=addStationPage";
            }
            else
            return "/?command=operDone&title=StationAdded";
        } catch (Throwable e) {
            logger.info(e);
            throw new RuntimeException(e);
        }

    }

}
