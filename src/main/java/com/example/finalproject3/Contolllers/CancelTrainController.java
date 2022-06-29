package com.example.finalproject3.Contolllers;

import com.example.finalproject3.Adapter.TrainAdapter;
import com.example.finalproject3.Entity.Train;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.FrontController.ICommand;
import com.example.finalproject3.Services.TrainService;
import com.example.finalproject3.Utility.EmailSessionBean;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelTrainController implements ICommand {
    static private Logger logger = Logger.getLogger(CancelTrainController.class);
    TrainService trainService;
    EmailSessionBean emailSessionBean;
    public CancelTrainController(){
        trainService =new TrainService();
        emailSessionBean = new EmailSessionBean();
    }

    public CancelTrainController(TrainService trainService,EmailSessionBean emailBean) {
        this.trainService = trainService;
        emailSessionBean = emailBean;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Train train = null;
        try {
            User user = (User) request.getSession().getAttribute("user");
            if(user==null||!user.isAdmin()){
                request.getSession().setAttribute("error","NoPermission");
                request.setAttribute("redirect",true);
                return "/";
            }
            train = TrainAdapter.getTrainFromRequest(request);

            emailSessionBean.sendEmailAboutTrainCanceling(train,request);
            trainService.CancelTrainWithAllChanges(train);
            return "?command=operDone&title=TrainCanceled";
        }catch (Throwable e) {
            logger.info(e);
            throw new RuntimeException(e);
        }
    }
}
