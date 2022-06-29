package com.example.finalproject3.Utility;

import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.UserDAO;
import com.example.finalproject3.Entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static java.lang.Math.abs;

public class Utility {

    public static void updateUser(HttpServletRequest request) throws DAOException {
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null){
            user = new UserDAO().getUserById(user.getId());
            request.getSession().setAttribute("user",user);
        }
    }
    static public boolean validateEmail(String email){
        Pattern pattern = Pattern.compile("[A-Za-z._\\d]{2,25}@[A-Za-z\\d.]{2,15}");
        return pattern.matcher(email).matches();
    }
    static public boolean validatePassword(String password){
        Pattern pattern = Pattern.compile("[A-Za-z._@!#$%^&*()\\d]{8,25}");
        return pattern.matcher(password).matches();
    }
    /* checks locale in request and sets attributes that contain strings to add them into html(jsp)
    * */
    static public void setString(HttpServletRequest request){
       ResourceBundle bundle = getBundle(request);
        for(String k : bundle.keySet())
            request.setAttribute(k,bundle.getString(k));
    }
    /* returns bundle with current locale
    * */
    static public ResourceBundle getBundle(HttpServletRequest request){
        String str="locale_";
        if((String)request.getSession().getAttribute("lang")==null){
            str+="en";
        } else
            str="locale_"+(String)request.getSession().getAttribute("lang");
        ResourceBundle bundle= ResourceBundle.getBundle(str);
        return bundle;
    }
    static public String dateToString(Date date){
        return new StringBuilder().append(date.getDate()+"/").append((date.getMonth()+1)+"/")
                .append(date.getYear()+" ").append(date.getHours()+":"+((date.getMinutes()<10)
                        ?"0"+date.getMinutes():date.getMinutes())).toString();
    }
//    static public String dateToStringWithLines(Date date){
//        return new StringBuilder().append(date.getDate()+"-")
//                .append((date.getMonth()+1)+"-").append(date.getYear()+"-")
//                .append(date.getHours()+":"+date.getMinutes()).toString();
//    }
    public static String timeFromSeconds(long seconds){
        int h=0;
        int mc =0;
        String m= "";
        while (seconds>=3600){
            seconds-=3600;
            h++;
        }
        while (seconds>=60){
            seconds-=60;
            mc++;
        }
        if(mc<10)
            m = new String("0");
        return h+":"+m+mc;

    }
    static public String timeBetweenDates(Date first, Date second){
        long secs= abs((second.getTime()-first.getTime())/1000);
        return timeFromSeconds(secs);

    }
}