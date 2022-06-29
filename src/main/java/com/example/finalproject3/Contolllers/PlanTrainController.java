package com.example.finalproject3.Contolllers;

import com.example.finalproject3.Adapter.TrainAdapter;
import com.example.finalproject3.DAO.TrainDAO;
import com.example.finalproject3.Entity.Train;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.FrontController.ICommand;
import com.example.finalproject3.Services.TrainService;
import com.example.finalproject3.Services.UserService;
import com.example.finalproject3.Utility.Utility;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PlanTrainController implements ICommand {
    static private Logger logger= Logger.getLogger(PlanTrainController.class);
    TrainService trainService = new TrainService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try{
            User user = (User) request.getSession().getAttribute("user");
            if(user==null||!user.isAdmin()){
                request.getSession().setAttribute("error","NoPermission");
                request.setAttribute("redirect",true);
                return "/";
            }
            Utility.updateUser(request);
            Train train = TrainAdapter.getTrainFromRequest(request);
            trainService.addTrainToDataBase(train);
        } catch (Exception e) {
            logger.info(e);
            throw new RuntimeException(e);
        }
        return "?command=operDone&title=TrainPlanned";
    }
}
