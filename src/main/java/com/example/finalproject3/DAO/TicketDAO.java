package com.example.finalproject3.DAO;

import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Entity.Ticket;
import com.example.finalproject3.Entity.Train;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.Utility.DBHikariManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketDAO {
    UserDAO userDAO;
    TrainDAO trainDAO;
    private static Logger logger =Logger.getLogger(TicketDAO.class);
    private static String SELECT_ALL_ENABLE_TICKETS_QUERY= "SELECT * FROM tickets WHERE enabled = true ORDER BY id";
    private static String SELECT_ALL_TICKETS_QUERY = "SELECT * FROM tickets ORDER BY id";
    private static String SELECT_COUNT_TICKET_BY_ID_QUERY = "SELECT COUNT(id) FROM tickets WHERE id= ? ORDER BY id";
    private static String SELECT_TICKET_BY_ID_QUERY = "SELECT * FROM tickets WHERE id = ?";
    private static String SELECT_TICKETS_BY_TRAIN_QUERY= "SELECT * FROM tickets WHERE trainid = ? AND enabled = true";
    private static String SELECT_TICKETS_BY_USERID_QUERY = "SELECT * FROM tickets WHERE userid = ? ORDER BY id";
    private static String SELECT_ENABLE_TICKETS_BY_USERID_QUERY = "SELECT * FROM tickets WHERE userid = ? AND enabled = true ORDER BY id";
    private static String UPDATE_ENABLED_BY_ID_QUERY = "UPDATE tickets set enabled = false WHERE id =?";
    private static String INSERT_TICKET_QUERY = "INSERT INTO tickets VALUES(default,?,?,?,?,?,?,true)";
    private static String SELECT_PAGINATED_TICKETS_QUERY = "SELECT * FROM tickets WHERE enabled = true AND userid =? ORDER BY id LIMIT ? OFFSET ?";
    public TicketDAO(){
        userDAO = new UserDAO();
        trainDAO = new TrainDAO();
    }
    public  List<Ticket> getAllTickets() throws DAOException {
        return getAllTicketsFromQuery(SELECT_ALL_TICKETS_QUERY);
    }
    public  List<Ticket> getAllEnableTickets() throws DAOException {
        return getAllTicketsFromQuery(SELECT_ALL_ENABLE_TICKETS_QUERY);
    }
    public  List<Ticket> getAllTicketsOfUser(User user) throws DAOException {
     return getAllUsersTicketsFromQuery(user,SELECT_TICKETS_BY_USERID_QUERY);
    }
    public  List<Ticket> getPaginatedEnableTicketsOfUser(User user, int page, int maxNumber) throws DAOException {
        List<Ticket> tickets = new ArrayList<>();
        try(Connection connection = DBHikariManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(SELECT_PAGINATED_TICKETS_QUERY)){
            page--;
            statement.setInt(1, user.getId());
            statement.setInt(2,maxNumber);
            statement.setInt(3,page*maxNumber);
            ResultSet set = statement.executeQuery();
            while (set.next())
                tickets.add(getTicketFromResultSet(set));
        return tickets;
        }catch (SQLException e){
            String message = "Couldn't paginate tickets";
            logger.info(e);
            throw new DAOException(message,e);
        }
    }
    public  List<Ticket> getAllEnableTicketsOfUser(User user) throws DAOException {
        return getAllUsersTicketsFromQuery(user,SELECT_ENABLE_TICKETS_BY_USERID_QUERY);
    }
    public  boolean insertTicketInDataBase(Ticket ticket) throws DAOException {
        try(Connection connection =DBHikariManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(INSERT_TICKET_QUERY)){
            statement.setInt(1,ticket.getOwner().getId());
            statement.setInt(2,ticket.getTrain().getId());
            statement.setInt(3,ticket.getCost());
            statement.setTimestamp(4,Timestamp.from(Instant.now()));
            statement.setString(5,null);
            statement.setString(6,null);
            if(ticket.getEndStation()!=null&&ticket.getStartStation()!=null){
                statement.setString(5,ticket.getStartStation().getName());
                statement.setString(6,ticket.getEndStation().getName());
            }
          return statement.execute();
        }catch  (SQLException e){
            String message ="Couldn't insert ticket to database";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
    }
    public  boolean updateTicketEnabled(Ticket ticket) throws DAOException {
        try(Connection connection=DBHikariManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_ENABLED_BY_ID_QUERY)){
            statement.setLong(1,ticket.getId());
            return statement.execute();
        }catch  (SQLException e){
            String message ="Couldn't update ticket to enabled";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
    }
    public  boolean isTicketByIdExists(int id) throws DAOException {
        try(Connection connection = DBHikariManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_TICKET_BY_ID_QUERY)){
            statement.setInt(1,id);
            ResultSet set = statement.executeQuery();
            return set.next();
        }catch (SQLException e){
            String message ="Couldn't check existing of ticket";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
    }
    public  List<Ticket> getAllEnableTicketsOnTrain(Train train) throws DAOException {
        List<Ticket> allTickets = new ArrayList<>();
        try(Connection connection = DBHikariManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_TICKETS_BY_TRAIN_QUERY)){
            statement.setInt(1,train.getId());
            ResultSet set = statement.executeQuery();
            while (set.next())
                allTickets.add(getTicketFromResultSet(set));
        }catch  (SQLException e){
            String message ="Couldn't get list of this train tickets";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
        return allTickets;
    }
    private  Ticket getTicketFromResultSet(ResultSet set) throws SQLException,DAOException {
        int trainId = set.getInt("trainid");
        Train train = trainDAO.getTrainById(trainId);
        Ticket ticket = new Ticket(train);
        Date date = DBHikariManager.TimestampToDate(set.getTimestamp("date"));
        ticket.setDate(date);

        User user = userDAO.getUserById(set.getInt("userid"));
        ticket.setOwner(user);
        ticket.setId(set.getInt("id"));
        if(set.getString("startstation")!=null&&set.getString("endstation")!=null){
        ticket.setStartStation(new Station(set.getString("startstation")));
        ticket.setEndStation(new Station(set.getString("endstation")));}
        return ticket;
    }
    private  List<Ticket> getAllTicketsFromQuery(String query) throws DAOException {
        List<Ticket> allTickets = new ArrayList<>();
        try(Connection connection = DBHikariManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            ResultSet set = statement.executeQuery();
            while (set.next())
                allTickets.add(getTicketFromResultSet(set));
        }catch  (SQLException e){
            String message ="Couldn't get list of tickets";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
        return allTickets;
    }
    private List<Ticket> getAllUsersTicketsFromQuery(User user, String query) throws  DAOException {
        List<Ticket> allTickets = new ArrayList<>();
        try(Connection connection = DBHikariManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,user.getId());
            ResultSet set = statement.executeQuery();
            while (set.next())
                allTickets.add(getTicketFromResultSet(set));
        }catch  (SQLException e){
            String message ="Couldn't get list of user's tickets";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
        return allTickets;
    }

}
