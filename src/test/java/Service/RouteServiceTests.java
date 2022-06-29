package Service;

import com.example.finalproject3.Entity.Route;
import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Services.RouteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RouteServiceTests {

    @Test
    void deleteInnerStationTest(){
        Route route = new Route();
        route.addStation(new Station("Amikiri"));
        route.addDelay(50);
        route.addStation(new Station("Jinx"));
        route.addDelay(70);
        route.addStation(new Station("Rockie"));
        route.setCost(50);
        RouteService.deleteInnerStation(route,new Station("Jinx"),80,40);
        Route route2 = new Route();
        route2.addStation(new Station("Amikiri"));
        route2.addStation(new Station("Rockie"));
        route2.addDelay(80);
        route2.setCost(40);
        Assertions.assertEquals(route2,route);

    }
    @Test
    void deleteOuterStationTest(){
        Route route = new Route();
        route.addStation(new Station("Amikiri"));
        route.addDelay(50);
        route.addStation(new Station("Jinx"));
        route.addDelay(70);
        route.addStation(new Station("Rockie"));
        route.setCost(50);
        RouteService.deleteOuterStation(route,new Station("Amikiri"),40);
        Route route2 = new Route();
        route2.addStation(new Station("Jinx"));
        route2.addDelay(70);
        route2.addStation(new Station("Rockie"));
        route2.setCost(40);
        Assertions.assertEquals(route2,route);
    }

}
