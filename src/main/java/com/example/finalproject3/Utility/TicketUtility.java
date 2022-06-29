package com.example.finalproject3.Utility;

import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Entity.Ticket;
import com.example.finalproject3.Entity.Train;

import java.util.Date;

public class TicketUtility {
    /**
     * returns time of journey
     * between first station of ticket
     * and last station in
     * string in format
     * hh:mm:ss
     * */
    public static String timeInJourney(Ticket ticket){
        Station station1 = ticket.getTrain().getStations().get(0);
        Station station2 = ticket.getTrain().getStations().get(ticket.getTrain().getStations().size()-1);
        if(ticket.getEndStation()!=null&&ticket.getStartStation()!=null){
            station1=ticket.getEndStation();
            station2=ticket.getStartStation();
        }
        Date second= ticket.getTrain().getAgenda().get(station1);
        Date first=ticket.getTrain().getAgenda().get(station2);
        return Utility.timeBetweenDates(first,second);

    }
}
