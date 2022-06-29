package com.example.finalproject3.Services;

import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.StationDAO;
import com.example.finalproject3.DAO.TrainDAO;
import com.example.finalproject3.Entity.Station;

import java.util.List;

public class StationService {
    StationDAO stationDAO;
    public StationService(){
        stationDAO= new StationDAO();
    }
    public StationService(StationDAO stationDAO){
        this.stationDAO= stationDAO;
    }
    public  boolean AddStationInDataBase(Station station) throws DAOException {
        if(stationDAO.isStationExist(station))
            return false;
        else stationDAO.insertStationToDataBase(station);
        return true;
    }
    public List<Station> getPaginatedStationList(int page) throws DAOException {
        return stationDAO.getPaginatedStations(page);
    }

}
