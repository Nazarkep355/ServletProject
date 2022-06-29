package com.example.finalproject3.Classes;
import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.List;

import com.example.finalproject3.DAO.*;
import com.example.finalproject3.Entity.*;
import com.example.finalproject3.Utility.DBHikariManager;
import org.apache.log4j.Logger;


public class Main {
    public static void main(String[] args) throws SQLException {
        DBHikariManager.getConnection().close();
    }


    }
//
