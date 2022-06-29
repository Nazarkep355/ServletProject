package com.example.finalproject3.Entity;



import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Ticket {

    private User owner;
    private long id;
    private Train train;
    private int cost;
    private Date orderDate;

    private Station startStation;
    private Station endStation;

    public Ticket(){}
    public Ticket(Train t){
        this.train=t;
        this.cost=t.getCost();
    }


    public void setOwner(User owner) {this.owner = owner;}

    public void setCost(int cost) {this.cost = cost;}

    public int getCost() {return cost;}

    public long getId() {return id;}

    public Train getTrain() {return train;}

    public User getOwner() {return owner;}

    public void setId(long id) {this.id = id;}

    public void setTrain(Train train) {this.train = train;}

    public Date getDate() {
        return orderDate;
    }

    public void setDate(Date date) {
        this.orderDate = date;
    }



    public Station getEndStation() {
        return endStation;
    }

    public void setEndStation(Station endStation) {
        this.endStation = endStation;
    }

    public Station getStartStation() {
        return startStation;
    }

    public void setStartStation(Station startStation) {
        this.startStation = startStation;
    }
//

   /** Pattern Builder class*/
   public static class TicketBuilder{
        private User owner;
        private long id;
        private Train train;
        private int cost;
        private Date orderDate;
        private Station startStation;
        private Station endStation;
        public TicketBuilder owner(User user){
            this.owner=user;
            return this;
        }
        public TicketBuilder id(long id){
            this.id=id;
            return this;
        }
        public TicketBuilder cost(int cost){
            this.cost=cost;
            return this;
        }
        public TicketBuilder orderDate(Date date){
            this.orderDate=date;
            return this;
        }
        public TicketBuilder startStation(Station station){
            this.startStation=station;
            return this;
        }
        public TicketBuilder train(Train train){
            this.train=train;
            return this;
        }
        public TicketBuilder endStation(Station station){
            this.endStation=station;
            return this;
        }
        public Ticket build(){
            Ticket ticket = new Ticket();
            ticket.cost=this.cost;
            ticket.id=this.id;
            ticket.owner= this.owner;
            ticket.orderDate=this.orderDate;
            ticket.endStation=this.endStation;
            ticket.startStation=this.startStation;
            ticket.train=this.train;
            return ticket;
        }
    }



}