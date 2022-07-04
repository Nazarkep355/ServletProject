package com.example.finalproject3.Contolllers;

import com.example.finalproject3.DAO.StationDAO;
import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.FrontController.ICommand;
import com.example.finalproject3.Services.StationService;
import com.example.finalproject3.Utility.Utility;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StationsController implements ICommand {
    static private Logger logger= Logger.getLogger(StationsController.class);
    StationService stationService;
    public StationsController(){
        stationService = new StationService();
    }

    public StationsController(StationService stationService) {
        this.stationService = stationService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {int page = Integer.parseInt(request.getParameter("page"));
            User user = (User)request.getSession().getAttribute("user");
            boolean isLogged= user != null;
            List<Station> stations = stationService.getPaginatedStationList(page);
            request.setAttribute("currentPage",page);
            request.setAttribute("stations",stations);
            request.setAttribute("isLogged",isLogged);
           return "Stations.jsp";
        } catch (Throwable e) {
            logger.info(e);
            throw new RuntimeException(e);
        }
    }
}
