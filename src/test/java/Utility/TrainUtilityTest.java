package Utility;

import com.example.finalproject3.Entity.Route;
import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Entity.Train;
import com.example.finalproject3.Utility.TrainUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TrainUtilityTest {
    @Test
    void fromToTest(){
        Route route = new Route();
        route.addStation(new Station("First"));
        route.addStation(new Station("Second"));
        route.addStation(new Station("Third"));
        Train train = new Train(route);
        Assertions.assertEquals(" First - Third", TrainUtility.fromTo(train));
    }
}
