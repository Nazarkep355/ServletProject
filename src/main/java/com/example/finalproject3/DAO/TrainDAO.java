package com.example.finalproject3.DAO;

import com.example.finalproject3.Entity.Route;
import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Entity.Train;
import com.example.finalproject3.Utility.DBHikariManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class TrainDAO {
    private RouteDAO routeDAO;
    private static Logger logger = Logger.getLogger(TicketDAO.class);
    private static String SELECT_ALL_ENABLE_TRAINS_QUERY = "SELECT * FROM trains where enabled = true ORDER BY id";
    private static String SELECT_ALL_TRAINS_QUERY = "SELECT * FROM trains ORDER BY id ";
    private static String SELECT_COUNT_TRAIN_BY_ID_QUERY = "SELECT COUNT(id) FROM trains WHERE id = ?";
    private static String SELECT_TRAIN_BY_ID_QUERY = "SELECT * FROM trains WHERE id = ?";
    private static String INSERT_INTO_TRAINS_QUERY = "INSERT INTO trains VALUES(default,?,?,?,?,?,0,true)";
    private static String UPDATE_ENABLE_TO_FALSE_QUERY= "UPDATE trains SET enabled = false WHERE id = ?";
    private static String UPDATE_BOOKED_BY_ID_QUERY = "UPDATE trains SET booked = ? WHERE id =?";
    private static String SELECT_PAGINATED_TRAINS_BY_ONE_STATION_QUERY = "SELECT * FROM trains WHERE ?=ANY(stations) AND enabled = true ORDER BY ID LIMIT ? OFFSET ?";
    private static String SELECT_PAGINATED_TRAINS_BY_TWO_STATIONS_IN_NEEDED_ORDER_QUERY = "SELECT * FROM trains WHERE enabled = true AND array_position(stations, ?) <array_position(stations, ?) ORDER BY ID LIMIT ? OFFSET ? ";
    private static String PAGINATION_SELECT_TRAINS_QUERY = "SELECT * FROM trains WHERE enabled = true ORDER BY id LIMIT ? OFFSET ?";
    public TrainDAO(){
        routeDAO=new RouteDAO();
    }
    public  List<Train> getAllEnableTrains() throws DAOException {
        List<Train> trains = new ArrayList<>();
        try(Connection connection = DBHikariManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ENABLE_TRAINS_QUERY)){
            ResultSet set=statement.executeQuery();
            while(set.next()){
                trains.add(getTrainFromResultSet(set));
            }
            return trains;
        }catch  (SQLException e){
            String message ="Couldn't get list of enable tickets";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
    }
    public  List<Train> getAllTrains() throws DAOException {
        try(Connection connection = DBHikariManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TRAINS_QUERY)){
            ResultSet set=statement.executeQuery();
            List<Train> trains = new ArrayList<>();
            while(set.next()){
                trains.add(getTrainFromResultSet(set));
            }
            return trains;
        }catch  (SQLException e){
            String message ="Couldn't get list of all trains";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
    }
    public  boolean isTrainExistsBy(int id) throws DAOException {
        try(Connection connection = DBHikariManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_COUNT_TRAIN_BY_ID_QUERY)){
            statement.setInt(1,id);
            ResultSet set = statement.executeQuery();
            set.next();
            int number = set.getInt(1);
            return number>0;
        }catch  (SQLException e){
            String message ="Couldn't check existing of train by id";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
    }
    public  List<Train> paginatedListOfEnableTrains(int page, int maxNumber) throws  DAOException{
        List<Train> trains = new ArrayList<>();
        try(Connection connection = DBHikariManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(PAGINATION_SELECT_TRAINS_QUERY)){
            page = --page*maxNumber;
            statement.setInt(1,maxNumber);
            statement.setInt(2, page);
            ResultSet set = statement.executeQuery();
            while (set.next())
                trains.add(getTrainFromResultSet(set));
            return trains;
        }catch  (SQLException e){
            String message ="Couldn't make pagination";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
    }
    public  List<Train> paginatedListOfEnabledTrainsByOneStation(Station station, int page, int maxNumber) throws DAOException {
        page--;
        List<Train> trainList = new ArrayList<>();
        try(Connection connection = DBHikariManager.getConnection();
               PreparedStatement statement = connection.prepareStatement(SELECT_PAGINATED_TRAINS_BY_ONE_STATION_QUERY)){
            statement.setString(1,station.getName());
            statement.setInt(2,maxNumber);
            statement.setInt(3,page*maxNumber);

            ResultSet set = statement.executeQuery();
            while (set.next())
                trainList.add(getTrainFromResultSet(set));
            return trainList;
        }catch (SQLException e ){
            String message ="Couldn't paginate trains by one station";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
    }
    public  List<Train> paginatedListOfEnabledTrainsByTwoStations(Station firstStation,Station secondStation, int page, int maxNumber) throws DAOException {
        page--;
        List<Train> trainList = new ArrayList<>();
        try(Connection connection = DBHikariManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_PAGINATED_TRAINS_BY_TWO_STATIONS_IN_NEEDED_ORDER_QUERY)){
            statement.setString(1,firstStation.getName());
            statement.setString(2,secondStation.getName());
            statement.setInt(3,maxNumber);
            statement.setInt(4,page*maxNumber);
            ResultSet set = statement.executeQuery();
            while (set.next())
                trainList.add(getTrainFromResultSet(set));
            return trainList;
        }catch (SQLException e ){
            String message ="Couldn't paginate trains by two stations";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
    }
    public Train getTrainById(int id) throws DAOException{
        if(!isTrainExistsBy(id))
            return null;
        try (Connection connection = DBHikariManager.getConnection();
               PreparedStatement statement = connection.prepareStatement(SELECT_TRAIN_BY_ID_QUERY)){
            statement.setInt(1,id);
            ResultSet set = statement.executeQuery();
            set.next();
            return getTrainFromResultSet(set);

        }catch  (SQLException e){
            String message ="Couldn't get train by id";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
    }
    public  boolean insertTrainInDataBase(Train train) throws DAOException {
        try(Connection connection = DBHikariManager.getConnection();
            PreparedStatement statement =connection.prepareStatement(INSERT_INTO_TRAINS_QUERY)){
            if(!routeDAO.isRouteExistById(train.getRoute().getId()))
                routeDAO.insertRouteInDataBase(train.getRoute());
            List<String> stationStringList= new ArrayList<>();
            for(Station station :train.getStations())
                stationStringList.add(station.getName());
            statement.setArray(1, connection.createArrayOf("varchar",stationStringList.toArray()));
            List<Timestamp> timestamps = new ArrayList<>();
            for(Station station: train.getStations()) {
                Date date = train.getAgenda().get(station);
                Timestamp timestamp = new Timestamp(date.getTime());
                timestamps.add(timestamp);
            }
            statement.setArray(2,connection.createArrayOf("timestamp",timestamps.toArray()));
            statement.setInt(3,train.getCost());
            statement.setInt(4,train.getRoute().getId());
            statement.setInt(5,train.getSeats());
            return statement.execute();}catch  (SQLException e){
            String message ="Couldn't insert train in DB";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
    }
    public boolean updateBookedById(Train train, int newBooked) throws DAOException {
        try(Connection connection = DBHikariManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_BOOKED_BY_ID_QUERY)){
            statement.setInt(1,newBooked);
            statement.setInt(2,train.getId());
            return statement.execute();
        }catch  (SQLException e){
            String message ="Couldn't update booked by id";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
    }
    public boolean updateEnableById(Train train) throws DAOException {
        try(Connection connection = DBHikariManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_ENABLE_TO_FALSE_QUERY)){
            statement.setInt(1,train.getId());
            return statement.execute();
        }catch  (SQLException e){
            String message ="Couldn't update enable by id";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
    }
     private Train getTrainFromResultSet(ResultSet set) throws SQLException,DAOException {
        LinkedList<Station> TrainStations=new LinkedList<>();
        Array stats = set.getArray("stations");
        Object object = stats.getArray();
        Object[] arrayofStations = (Object[]) object;
        for (Object ob : arrayofStations ) {
            TrainStations.add(new Station(String.valueOf(ob)));
        }
        int idRoute =Integer.parseInt(set.getString("route"));
        Route route = routeDAO.getRouteById(idRoute);
        Train t=new Train(route);
        t.setStations(TrainStations);
        Array arr =set.getArray("agenda");
        Object obj = arr.getArray();
        Object[] array =(Object[]) obj;
        HashMap<Station, Date> agenda= new HashMap<>();
        for(int i =0;i<array.length;i++){
            Timestamp timestamp = (Timestamp) array[i];
            agenda.put(t.getStations().get(i),new Date(timestamp.getTime()));
        }

        t.setAgenda(agenda);
        t.setEnabled(set.getBoolean("enabled"));
        t.setId(set.getInt("id"));
        t.setSeats(set.getInt("seats"));
        t.setBooked(set.getInt("booked"));
        return t;
    }

}
