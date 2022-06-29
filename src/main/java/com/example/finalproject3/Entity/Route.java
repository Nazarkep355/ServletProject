package com.example.finalproject3.Entity;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Route {
    private List<Station> stations = new ArrayList<>();
    private int cost;
    private int id;
    private List<Integer> delays = new ArrayList<>();

    public List<Station> getStations() {return stations;}
    public void addStation(Station station){stations.add(station);}


    /* delete first or last
     * station and sets new cost
    * */
    public void setCost(int cost) {this.cost = cost;}
    public int getCost(){return cost;}

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().getName().equals(this.getClass().getName()))return false;
        Route r =(Route)obj;
        if(r.getCost()!=this.getCost())return false;
        if(this.getStations().size()!=r.getStations().size())return false;
        for(int i=0;i<r.getStations().size();i++){
            if(!this.getStations().get(i).equals(r.getStations().get(i)))return false;
        }
        for(int i=0;i<r.getDelays().size();i++){
            if(this.getDelays().get(i)!=r.getDelays().get(i))return false;
        }
        return true;
    }
    public List<Integer> getDelays() {
        return delays;
    }
    public void addDelay(int delay){
        delays.add(delay);
    }
    public void setDelays(List<Integer> delays) {
        this.delays = delays;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }
}

