package com.example.finalproject3.Entity;



import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Station {
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String n){
        name=n;
    }
    public Station(){}

    public Station(String s){
        name =s;
    }

    @Override
    public int hashCode() {
        int res=0;
        for(char c : getName().toCharArray()){
            res+=c*17;
        }
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass()!=this.getClass())return false;
        return ((Station) obj).hashCode()==this.hashCode();
    }
}
