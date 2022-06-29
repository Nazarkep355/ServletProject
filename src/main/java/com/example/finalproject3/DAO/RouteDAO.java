package com.example.finalproject3.DAO;

import com.example.finalproject3.Entity.Route;
import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Utility.DBHikariManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteDAO {
        private static Logger logger = Logger.getLogger(RouteDAO.class);
        private static String SELECT_ALL_ROUTES_QUERY = "SELECT * FROM routes ORDER BY id";
        private static String SELECT_COUNT_ROUTE_BY_ID_QUERY = "SELECT COUNT(id) FROM routes WHERE id =?";
        private static String SELECT_ROUTE_BY_ID_QUERY = "SELECT * FROM routes WHERE id = ?";
        private static String INSERT_INTO_ROUTES_QUERY = "INSERT INTO routes VALUES(default,?,?,?) ";
        public  List<Route> getAllRoutes() throws DAOException {
            List<Route> routes = new ArrayList<>();
            try(Connection connection = DBHikariManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ROUTES_QUERY)){
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                   routes.add(getRouteFromResultSet(set));
                }
            }catch  (SQLException e){
                String message ="Couldn't get list of all routes";
                logger.info(message,e);
                throw new DAOException(message,e);
            }
            return routes;
        }
        public  boolean isRouteExistById(int id) throws DAOException {
            try(Connection connection = DBHikariManager.getConnection();
                   PreparedStatement statement = connection.prepareStatement(SELECT_COUNT_ROUTE_BY_ID_QUERY)) {
                statement.setInt(1,id);
                ResultSet set = statement.executeQuery();
                set.next();
                int number = set.getInt(1);
                return number > 0;
            }catch  (SQLException e){
                String message ="Couldn't check if route is exists";
                logger.info(message,e);
                throw new DAOException(message,e);
            }
        }
        public  Route getRouteById(int id) throws DAOException {
            if(!isRouteExistById(id))
                return null;
            try(Connection connection = DBHikariManager.getConnection();
                   PreparedStatement statement = connection.prepareStatement(SELECT_ROUTE_BY_ID_QUERY) ){
                statement.setInt(1,id);
                ResultSet set = statement.executeQuery();
                set.next();
                return getRouteFromResultSet(set);
            }catch  (SQLException e){
                String message ="Couldn't get route by id";
                logger.info(message,e);
                throw new DAOException(message,e);
            }
        }
        public  boolean insertRouteInDataBase(Route route) throws DAOException {
            Object[] arrayStations=new Object[route.getStations().size()];
            for(int i =0;i<route.getStations().size();i++){
                arrayStations[i]=route.getStations().get(i).getName();
            }
            try(Connection connection = DBHikariManager.getConnection();
                   PreparedStatement statement =connection.prepareStatement(INSERT_INTO_ROUTES_QUERY)){
                Object[] arrayDelays= route.getDelays().toArray();
                statement.setArray(1, connection.createArrayOf("VARCHAR",arrayStations));
                statement.setInt(2,route.getCost());
                statement.setArray(3,connection.createArrayOf("integer",arrayDelays));
                return statement.execute();
            }catch  (SQLException e){
                String message ="Couldn't insert route in DB";
                logger.info(message,e);
                throw new DAOException(message,e);
            }
        }
        static Route getRouteFromResultSet(ResultSet set) throws SQLException {
            Route route = new Route();
            route.setId(set.getInt("id"));
            Array stations = set.getArray("stations");
            int cost = set.getInt("cost");
            Object obj = stations.getArray();
            Object[] arr = (Object[]) obj;
            for (Object ob : arr) {
                route.addStation(new Station(String.valueOf(ob)));
            }
            Array delaysArray = set.getArray("delays");
            obj= delaysArray.getArray();
            Object[] delays = (Object[]) obj;
            for(Object ob : delays){
                route.addDelay((Integer) ob);
            }
            route.setCost(cost);
            return route;
        }
}
