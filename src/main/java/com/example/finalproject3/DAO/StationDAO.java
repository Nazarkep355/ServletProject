package com.example.finalproject3.DAO;

import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Utility.DBHikariManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationDAO {
    private static Logger logger = Logger.getLogger(StationDAO.class);
    static private String SELECT_ALL_STATIONS_QUERY = "SELECT * FROM stations ORDER BY name";
    static private String SELECT_COUNT_STATION_BY_NAME_QUERY= "SELECT COUNT(name) FROM stations where name =?";
    static private String INSERT_STATION_QUERY = "INSERT INTO stations VALUES(?)";
    static private String SELECT_STATION_WITH_LIMIT_AND_OFFSET = "SELECT * FROM stations ORDER BY name LIMIT ? OFFSET ?";
    public List<Station> getAllStations() throws DAOException{
        List<Station> stations = new ArrayList<>();
        try(Connection connection = DBHikariManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_STATIONS_QUERY)){
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Station station = new Station(set.getString("name"));
                stations.add(station);
            }
            return stations;
        }catch  (SQLException e){
            String message ="Couldn't get all stations list";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
    }
    public boolean isStationExist(Station station) throws DAOException{
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_COUNT_STATION_BY_NAME_QUERY)){
                statement.setString(1,station.getName());
            ResultSet set = statement.executeQuery();
            set.next();
            int number=set.getInt(1);
            if(number==1)
                return true;
            return false;
        }catch  (SQLException e){
            String message ="Couldn't check if station exists in DB";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
    }
    public boolean insertStationToDataBase(Station station) throws DAOException{
        if(isStationExist(station))
            return false;
        try (Connection connection = DBHikariManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_STATION_QUERY)){
            statement.setString(1,station.getName());
          return statement.execute();
        }catch  (SQLException e){
            String message ="Couldn't insert station in DB";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
    }
    public List<Station> getPaginatedStations(int page) throws DAOException {
        List<Station> stations = new ArrayList<>();
        try(Connection connection = DBHikariManager.getConnection();
               PreparedStatement statement = connection.prepareStatement(SELECT_STATION_WITH_LIMIT_AND_OFFSET)){
                --page;
                statement.setInt(1,5);
                statement.setInt(2,5*page);
                ResultSet set = statement.executeQuery();
                while(set.next()){
                    stations.add(new Station(set.getString("name")));
                }
                return stations;
        }catch (SQLException e){
            String message = "Couldn't paginate stations list";
            logger.info(message);
            throw new DAOException(message,e);
        }
    }
}
