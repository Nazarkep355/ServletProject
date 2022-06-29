package Utility;

import com.example.finalproject3.Entity.Route;
import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Utility.RouteUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RouteUtilityTest {
    @Test
    void getStringOfRouteTest(){
        Route route = new Route();
        route.addStation(new Station("Amikiri"));
        route.addStation(new Station("Jinx"));
        route.addStation(new Station("Rockie"));
        Assertions.assertEquals("Amikiri-Jinx-Rockie", RouteUtility.getStringOfRoute(route));
    }

}
