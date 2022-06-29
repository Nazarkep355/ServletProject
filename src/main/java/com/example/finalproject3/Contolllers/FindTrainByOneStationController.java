package com.example.finalproject3.Contolllers;

import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Entity.Train;
import com.example.finalproject3.FrontController.ICommand;
import com.example.finalproject3.Services.TrainService;
import com.example.finalproject3.Utility.Utility;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ResourceBundle;

public class FindTrainByOneStationController implements ICommand {
    static private Logger logger =Logger.getLogger(FindTrainByOneStationController.class);
    TrainService trainService = new TrainService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {ResourceBundle bundle =Utility.getBundle(request);

            int page = Integer.parseInt(request.getParameter("page"));
            Station station = new Station(request.getParameter("station"));
            List<Train> trains = trainService.getPaginatedTrainsByOneStationFromRequest(station, page);
            request.setAttribute("noLastStation",true);
            request.setAttribute("trains",trains);
            request.setAttribute("currentPage",page);
            request.setAttribute("filteredByOne",true);
            request.setAttribute("station1",station);
            request.setAttribute("station2",bundle.getString("LastStation"));
            return "Trains.jsp";
        } catch (Throwable e) {
            logger.info(e);
            throw new RuntimeException(e);
        }
    }
}
