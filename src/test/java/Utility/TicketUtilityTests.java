package Utility;

import com.example.finalproject3.Entity.*;
import com.example.finalproject3.Utility.TicketUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

public class TicketUtilityTests {

    @Test
    void timeInJourneyTest(){
        Route route = new Route();
        route.addStation(new Station("First"));
        route.addStation(new Station("Second"));
        route.addStation(new Station("Third"));
        route.addDelay(50);
        route.addDelay(50);
        Train train = new Train(route, Date.from(Instant.now()));
        Ticket ticket = new Ticket.TicketBuilder().startStation(new Station("First")).
                endStation(new Station("Second")).train(train).build();
        Assertions.assertEquals("0:50", TicketUtility.timeInJourney(ticket));
    }
}
