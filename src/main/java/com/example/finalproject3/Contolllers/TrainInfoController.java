package com.example.finalproject3.Contolllers;

import com.example.finalproject3.Adapter.TrainAdapter;
import com.example.finalproject3.Entity.Train;
import com.example.finalproject3.FrontController.ICommand;

import com.example.finalproject3.Utility.Utility;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public class TrainInfoController implements ICommand {
    static private Logger logger=Logger.getLogger(TrainsController.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if(request.getSession().getAttribute("error")!=null){
            System.out.println(request.getSession().getAttribute("error"));
            request.setAttribute("error",(String)request.getSession().getAttribute("error"));
            request.getSession().setAttribute("error",null);
        }
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        try {
            Train train = TrainAdapter.getTrainInfoFromRequest(request);
            request.setAttribute("train",train);
        if(from!=null&&to!=null) {
            request.setAttribute("station1",Integer.parseInt(from));
            request.setAttribute("station2",Integer.parseInt(to));}
        return "InfoAboutTrain.jsp";
     } catch (Throwable e) {
        logger.info(e);
        throw new RuntimeException(e);
    }
}
}
