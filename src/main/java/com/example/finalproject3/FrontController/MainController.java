package com.example.finalproject3.FrontController;

import com.example.finalproject3.Contolllers.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
@WebServlet("/")
public class MainController extends HttpServlet {
    HashMap<String,ICommand> urlMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        urlMap.put("login",new LoginController());
        urlMap.put(null, new HelloController());
        urlMap.put("signOut",new SignOutController());
        urlMap.put("trains",new TrainsController());
        urlMap.put("trainInfo",new TrainInfoController());
        urlMap.put("changeMoneyPage",new ChangeMoneyPageController());
        urlMap.put("changeMoney",new ChangeMoneyController());
        urlMap.put("cancelTrain",new CancelTrainController());
        urlMap.put("buyOne", new BuyOneTicketController());
        urlMap.put("changeToEn", new EnglishLocaleController());
        urlMap.put("changeToUA", new UkrainianLocaleController());
        urlMap.put("operDone",new DoneOperationController());
        urlMap.put("stations",new StationsController());
        urlMap.put("register", new RegisterUserController());
        urlMap.put("registerPage",new RegisterPageController());
        urlMap.put("tickets", new TicketsController());
        urlMap.put("planTrainPage",new PlanTrainPageController());
        urlMap.put("planTrain", new PlanTrainController());
        urlMap.put("findTrainByStation", new FindTrainByOneStationController());
        urlMap.put("findTrainByTwoStations", new FindTrainsByTwoStationsController());
        urlMap.put("createRoutePage",new CreateRoutePageController());
        urlMap.put("createRoute", new CreateRouteController());
        urlMap.put("addStationPage", new AddStationPageController());
        urlMap.put("addStation",new AddStationController());
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command =req.getParameter("command");
        ICommand controller =urlMap.get(command);
//        System.out.println(controller.getClass());
        String forward = controller.execute(req,resp);
        if(isNeedToRedirect(req))
            resp.sendRedirect(forward);
        else
        req.getRequestDispatcher(forward).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        ICommand controller = urlMap.get(command);
        String redirect = controller.execute(req,resp);
        resp.sendRedirect(redirect);
    }
    boolean isNeedToRedirect(HttpServletRequest request){
        if(request.getAttribute("redirect")!=null)
            return (boolean) request.getAttribute("redirect");
        return false;
    }
}
