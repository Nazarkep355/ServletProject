package com.example.finalproject3.DAO;

import com.example.finalproject3.Entity.User;
import com.example.finalproject3.Utility.DBHikariManager;
import com.example.finalproject3.Utility.Message;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
         private static Logger logger = Logger.getLogger(UserDAO.class);
         static private String SELECT_ALL_USERS_QUERY = "SELECT * FROM users ORDER BY id";
         static private String SELECT_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
         static private String SELECT_COUNT_USER_BY_EMAIL_QUERY = "SELECT COUNT(email) FROM users WHERE email = ?";
         static private String SELECT_COUNT_USER_BY_ID_QUERY = "SELECT COUNT(email) FROM users WHERE id = ?";
         static private String SELECT_USER_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email = ?";
         static private String INSERT_USER_QUERY = "INSERT INTO users VALUES(default,?,?,?,?,?,null)";
         static private String UPDATE_USER_MONEY_BY_ID_QUERY = "UPDATE users SET money=? WHERE id = ?";
         static private String UPDATE_USER_MONEY_BY_ADDING = "UPDATE users SET money = money + ? WHERE id =?";
         static private String UPDATE_USER_MONEY_BY_SUBSTRACT = "UPDATE users SET money = money - ? WHERE id =?";
         static public List<User> getAllUsers() throws DAOException {
            List<User> users = new ArrayList<>();
            try(Connection connection = DBHikariManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS_QUERY)){
                    ResultSet set = statement.executeQuery();
                    while (set.next()){
                        users.add(getUserFromResulSet(set));
                    }
            }catch  (SQLException e){
                String message ="Couldn't get all users list";
                logger.info(message,e);
                throw new DAOException(message,e);
            }
            return users;
        }

         public boolean isUserWithEmailExists(String email) throws DAOException {
            try(Connection connection = DBHikariManager.getConnection();
                PreparedStatement statement =connection.prepareStatement(SELECT_COUNT_USER_BY_EMAIL_QUERY)){
                statement.setString(1,email);
                ResultSet set = statement.executeQuery();
                set.next();
                int number = set.getInt(1);
                if(number>0)
                    return true;
                else
                return false;
            }catch  (SQLException e){
                String message ="Couldn't check if user by email exists";
                logger.info(message,e);
                throw new DAOException(message,e);
            }
        }
         public boolean isUserWithIdExists(int id) throws DAOException {
        try(Connection connection = DBHikariManager.getConnection();
            PreparedStatement statement =connection.prepareStatement(SELECT_COUNT_USER_BY_ID_QUERY)){
            statement.setInt(1,id);
            ResultSet set = statement.executeQuery();
            set.next();
            int number = set.getInt(1);
            if(number>0)
                return true;
            else
                return false;
        }catch  (SQLException e){
            String message ="Couldn't check if user by id exists";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
    }
          public User getUserByEmail(String email) throws DAOException {
            if(!isUserWithEmailExists(email))
                return null;
            try(Connection connection = DBHikariManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_EMAIL_QUERY)){
                statement.setString(1,email);
                ResultSet set = statement.executeQuery();
                set.next();
                return getUserFromResulSet(set);
            }catch  (SQLException e){
                String message ="Couldn't get user by id";
                logger.info(message,e);
                throw new DAOException(message,e);
            }
        }
         public User getUserById(int id) throws DAOException {
        if(!isUserWithIdExists(id))
            return null;
        try(Connection connection = DBHikariManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID_QUERY)){
            statement.setInt(1,id);
            ResultSet set = statement.executeQuery();
            set.next();
            return getUserFromResulSet(set);
        }catch  (SQLException e){
            String message ="Couldn't get user by id";
            logger.info(message,e);
            throw new DAOException(message,e);
        }
    }
          public boolean insertUserInDataBase(User user) throws DAOException {
             if(isUserWithEmailExists(user.getEmail()))
                 return false;
             try(Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER_QUERY)){
                 statement.setString(1,user.getName());
                 statement.setString(2,user.getEmail());
                 statement.setString(3,user.getPassword());
                 statement.setInt(4,user.getUserType().ordinal());
                 statement.setInt(5,user.getMoney());
                 return statement.execute();
             }catch  (SQLException e){
                 String message ="Couldn't insert user in DB";
                 logger.info(message,e);
                 throw new DAOException(message,e);
             }
         }
         public boolean updateUserMoney(User user, int money) throws DAOException {
             if(!isUserWithIdExists(user.getId()))
                 return false;
             try(Connection connection = DBHikariManager.getConnection();
                   PreparedStatement statement = connection.prepareStatement(UPDATE_USER_MONEY_BY_ID_QUERY)){
                 statement.setInt(1,money);
                 statement.setInt(2,user.getId());
                return statement.execute();
             }catch  (SQLException e){
                 String message ="Couldn't update user money";
                 logger.info(message,e);
                 throw new DAOException(message,e);
             }
         }
        public boolean substractMoneyOfUser(User user, int substraction) throws  DAOException{
            try(Connection connection = DBHikariManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER_MONEY_BY_SUBSTRACT)) {
            statement.setInt(1,substraction);
            statement.setInt(2,user.getId());
            return statement.execute();
             }catch  (SQLException e){
            String message ="Couldn't substract user money";
            logger.info(message,e);
            throw new DAOException(message,e);
             }}
            public boolean addMoneyOfUser(User user, int adding) throws  DAOException{
                try(Connection connection = DBHikariManager.getConnection();
                    PreparedStatement statement = connection.prepareStatement(UPDATE_USER_MONEY_BY_ADDING)) {
                    statement.setInt(1,adding);
                    statement.setInt(2,user.getId());
                    return statement.execute();
                }catch  (SQLException e){
                    String message ="Couldn't add user money";
                    logger.info(message,e);
                    throw new DAOException(message,e);
                }
         }
         private static User getUserFromResulSet(ResultSet set) throws SQLException {
             String email = set.getString("email");
             String password = set.getString("password");
             int ut=Integer.parseInt(set.getString("usertype"));
             String name=set.getString("name");
             int id=Integer.parseInt(set.getString("id"));
             int money=set.getInt("money");
             ArrayList<Message> messages=new ArrayList<>();
             Array array=set.getArray("messages");
             if(array!=null){Object ob=array.getArray();
                 Object[] objects= (Object[]) ob;
                 for(Object obj: objects){
                     String str=(String)obj;
                     messages.add(Message.readMessage(str));}}
             User u=new User.UserBuilder()
                     .userType(ut)
                     .id(id)
                     .messages(messages)
                     .money(money)
                     .email(email)
                     .password(password)
                     .name(name)
                     .build();
             return u;
         }

         }

