package com.example.finalproject3.Contolllers;

import com.example.finalproject3.Entity.Train;
import com.example.finalproject3.FrontController.ICommand;
import com.example.finalproject3.Services.TicketService;
import com.example.finalproject3.Services.TrainService;
import com.example.finalproject3.Utility.Utility;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ResourceBundle;

public class TrainsController implements ICommand {
        static private Logger logger = Logger.getLogger(TrainsController.class);
        TrainService trainService;
        public TrainsController(){
            trainService = new TrainService();
        }

         public TrainsController(TrainService trainService) {
            this.trainService = trainService;
        }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ResourceBundle bundle = Utility.getBundle(request);
        try {
            int page = Integer.parseInt(request.getParameter("page"));
            List<Train> trains = trainService.getPaginatedListOfTrainsFromRequest(page);
            request.setAttribute("trains",trains);
            request.setAttribute("currentPage",page);
            request.setAttribute("station1",bundle.getString("FirstStation"));
            request.setAttribute("station2",bundle.getString("LastStation"));
            return "Trains.jsp";
        } catch (Throwable e) {
            logger.info(e);
            throw new RuntimeException(e);
        }
    }
}
