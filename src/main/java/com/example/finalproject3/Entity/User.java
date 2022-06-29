package com.example.finalproject3.Entity;


import com.example.finalproject3.Utility.Message;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private UserType userType;
    private int money;
    private ArrayList<Message> messages;
    public ArrayList<Message> getMessages() {
        return messages;
    }
    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
    public enum UserType{USER,ADMIN}
    public User(){}
    public boolean isAdmin(){
        if(userType==UserType.ADMIN)return true;
        else return false;
    }
    public User(UserBuilder builder){
        this.id= builder.id;
        this.email=builder.email;
        this.money=builder.money;
        this.password=builder.password;
        this.userType=builder.userType;
        this.name=builder.name;
        this.messages=builder.messages;
    }
    public String getName() {return name;}
    public void setName(String _n){name=_n;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public void setUserType(int ord) {
        if(ord>1||ord<0)throw new IllegalArgumentException("Must be entered '0' or '1'!");
        if(ord==1)userType = UserType.ADMIN;
        else if(ord==0)userType = UserType.USER;
    }
    public void setUserType(UserType userType){this.userType = userType;}
    public UserType getUserType() {return userType;}
    public void setId(int id) {this.id = id;}
    public int getId() {return id;}
    public int getMoney() {return money;}
    public Boolean setMoney(int money) {this.money = money;
        return true;
    }
    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().getName().equals(this.getClass().getName()))return false;
        User obj1 = (User) obj;
        if(obj1.getId()==this.getId())return true;
        return false;

    }
    @Override
    public int hashCode() {
        return id;
    }

    static public class UserBuilder{
        private int id;
        private String name;
        private String email;
        private String password;
        private UserType userType;
        private int money;
        private ArrayList<Message> messages;
        public UserBuilder id(int i){
            this.id=i;
            return this;
        }
        public UserBuilder name(String n){
            this.name=n;
            return this;
        }
        public UserBuilder email(String e){
            this.email=e;
            return this;
        }
        public UserBuilder password(String p){
            this.password =p;
            return this;
        }
        public UserBuilder userType(int ord){
            if(ord>1||ord<0)throw new IllegalArgumentException("Must be entered '0' or '1'!");
            if(ord==1)this.userType = UserType.ADMIN;
            else if(ord==0)this.userType = UserType.USER;
            return this;
        }
        public UserBuilder money(int m){
            this.money=m;
            return this;
        }
        public UserBuilder messages(ArrayList<Message> m){
            this.messages=m;
            return this;
        }
        public User build(){
            User u=new User(this);
            return u;
        }



    }

}
