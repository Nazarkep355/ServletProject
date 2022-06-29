package com.example.finalproject3.Services;

import com.example.finalproject3.DAO.*;
import com.example.finalproject3.Entity.User;


import javax.servlet.http.HttpServletRequest;

public class UserService {
    UserDAO userDAO;

    public UserService(){
        userDAO=new UserDAO();
    }

    public UserService(UserDAO uDAO){
        this.userDAO= uDAO;
    }
    public  boolean isPasswordCorrect(String email,String password) throws DAOException {
        User user = userDAO.getUserByEmail(email);
        return user.getPassword().equals(password);

    }

    public  boolean isEmailExists(String email) throws DAOException {
        return userDAO.isUserWithEmailExists(email);
    }
    public  User getUserByEmail(String email) throws DAOException {
        return userDAO.getUserByEmail(email);
    }
    public  User registerUser(String email, String password, String name)
            throws DAOException {
        User user = new User.UserBuilder().email(email)
                .password(password).name(name).userType(0).money(0).build();
        userDAO.insertUserInDataBase(user);
        user = userDAO.getUserByEmail(email);
        return user;
    }
    public  boolean addUserMoney(User user, int money) throws DAOException {
     if(!userDAO.isUserWithIdExists(user.getId()))
         return false;
     else
        return userDAO.addMoneyOfUser(user,money);
    }
}
