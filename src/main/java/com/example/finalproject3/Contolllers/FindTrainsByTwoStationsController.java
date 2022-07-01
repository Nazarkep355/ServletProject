package com.example.finalproject3.Contolllers;

import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Entity.Train;
import com.example.finalproject3.FrontController.ICommand;
import com.example.finalproject3.Services.TrainService;
import com.example.finalproject3.Utility.Utility;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class FindTrainsByTwoStationsController implements ICommand {
    static private Logger logger =Logger.getLogger(FindTrainsByTwoStationsController.class);
    TrainService trainService ;
    public FindTrainsByTwoStationsController(){
        trainService = new TrainService();
    }

    public FindTrainsByTwoStationsController(TrainService trainService) {
        this.trainService = trainService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
       try {int page = Integer.parseInt(request.getParameter("page"));
            Station station1 =new Station(request.getParameter("station1"));
            Station station2 =new Station(request.getParameter("station2"));
            List<Train> trains = trainService.getPaginatedTrainsByTwoStationFromRequest(station1,station2,page);
            request.setAttribute("trains",trains);
            request.setAttribute("currentPage",page);
            request.setAttribute("filteredByTwo",true);
            request.setAttribute("station1",station1);
            request.setAttribute("station2",station2);
            return "Trains.jsp";
    }
     catch (Throwable e) {
            logger.info(e);
        throw new RuntimeException(e);
    }
    }
}
