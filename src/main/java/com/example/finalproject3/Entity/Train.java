package com.example.finalproject3.Entity;



import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class Train {
    private Route route;
    private boolean enabled;
    private int cost;
    private int id;
    private int seats;
    private int booked;
    private List<Station> stations = new ArrayList<>();
    private HashMap<Station, Date> agenda = new HashMap<>();
    public Train(Route rt){
        route =rt;
        cost = rt.getCost();
        stations=rt.getStations();

    }
    public Train(Route rt, HashMap<Station,Date> dates){
        stations=rt.getStations();
        route =rt;
        cost = rt.getCost();
        agenda =dates;

    }
    public Train(Route rt,Date date){
        Date d=date;
        HashMap<Station,Date> dates=new HashMap<>();
        dates.put(rt.getStations().get(0),date);
        for(int i=1;i<rt.getStations().size();i++)
        {   d=new Date(d.getTime());
            d.setMinutes(d.getMinutes()+rt.getDelays().get(i-1));
            dates.put(rt.getStations().get(i),d);
        }
        this.stations=rt.getStations();
        this.cost=rt.getCost();
        this.route=rt;
        this.agenda=dates;
    }
    public Train(TrainBuilder builder){
        this.stations=builder.stations;
        this.id=builder.id;
        this.booked=builder.booked;
        this.seats=builder.seats;
        this.cost=builder.cost;
        this.enabled=builder.enabled;
        this.agenda=builder.agenda;
        this.route=builder.route;
    }
    public Route getRoute(){
        return route;
    }
    public int getCost(){
        return cost;
    }
    public void setRoute(Route rt){
        route=rt;
    }
    public void setAgenda(HashMap<Station,Date> agenda){
        this.agenda =agenda;
    }
    public HashMap<Station,Date> getAgenda(){
        return this.agenda;
    }
    public Boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean bool) {
        this.enabled = bool;
    }
    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().getName().equals(this.getClass().getName()))return false;
        Train t =(Train) obj;
        if(this.route!=null&&t.route!=null){
        if(!this.getRoute().equals(t.getRoute()))return false;}
        if(t.stations!=null&&this.stations!=null){
            if(t.stations.size()==this.stations.size()){
                for(int i =0;i<stations.size();i++){
                    if(!t.stations.get(i).equals(stations.get(i)))return false;}}
            else return false;
        }
        if(this.agenda!=null&&t.agenda!=null){
        if(t.getAgenda().size()!=this.getAgenda().size())return false;
        for(Station s : getAgenda().keySet()){
            if(getAgenda().get(s).compareTo(t.getAgenda().get(s))!=0)return false;
        }}
        if(id!=t.id)return false;
        if(booked!=t.getBooked())return false;
        if(seats!=t.seats)return false;
        if(this.getCost()!=t.getCost())return false;
        if(!this.isEnabled().equals(t.isEnabled()))return false;
        return true;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getSeats() {
        return seats;
    }
    public void setSeats(int seats) {
        this.seats = seats;
    }
    public int getBooked() {
        return booked;
    }
    public boolean setBooked(int booked) {
        this.booked = booked;
        return true;
    }
    public List<Station> getStations() {
        return stations;
    }
    public void setStations(LinkedList<Station> stations) {
        this.stations = stations;
    }
    public static class TrainBuilder{
        private List<Station> stations;
        private Route route;
        private boolean enabled;
        private int cost;
        private int id;
        private int seats;
        private int booked;
        private HashMap<Station,Date> agenda;
        public TrainBuilder route(Route r){
            this.route=r;
            this.cost=r.getCost();
            return this;
        }
        public TrainBuilder cost(int c){
            this.cost=c;
            return this;
        }
        public TrainBuilder enabled(boolean bool) {
            this.enabled=bool;
            return this;
        }
        public TrainBuilder id(int i){
            this.id=i;
            return this;
        }
        public TrainBuilder seats(int s){
            this.seats=s;
            return this;
        }
        public TrainBuilder booked(int b){
            this.booked=b;
            return this;
        }
        public TrainBuilder agenda(HashMap<Station,Date> hashMap){
            this.agenda=hashMap;
            return this;
        }
        public Train build(){
            Train t = new Train(this);
            return t;
        }

        public void stations(List<Station> stations) {
            this.stations = stations;
        }

    }
}
