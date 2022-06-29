package Service;

import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.StationDAO;
import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Services.StationService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.verification.VerificationMode;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class StationServiceTest {


    StationDAO stationDAO;
    List<Station> stations;
    StationService stationService;
    boolean work = false;
    StationServiceTest(){
    stationDAO = mock(StationDAO.class);
    stationService = new StationService(stationDAO);
    stations = new ArrayList<>();
    for(int i =1;i<7;i++)
        stations.add(new Station("Station"+i));
    }
    boolean addToDB(Station station){
        if(work)
            return stations.add(station);
        return false;
    }
    @Test
    void getPaginatedStationList() throws DAOException {
        when(stationDAO.getPaginatedStations(1))
                .thenReturn(stations.subList(0,5));
        when(stationDAO.getPaginatedStations(2))
                .thenReturn(stations.subList(5,6));
        List<Station> stationList = stationService.getPaginatedStationList(1);
        Assertions.assertEquals(5,stationList.size());
        Assertions.assertEquals("Station1",stationList.get(0).getName());
        Assertions.assertEquals("Station5",stationList.get(4).getName());
        stationList = stationService.getPaginatedStationList(2);
        Assertions.assertEquals(1,stationList.size());
        Assertions.assertEquals("Station6",stationList.get(0).getName());


    }
    @Test
    void AddStationInDataBase() throws DAOException {

        for(Station s : stations)
        when(stationDAO.isStationExist(s)).thenReturn(true);

       work =true;
        Assertions.assertEquals(false,stationService.AddStationInDataBase(new Station("Station6")));
        Assertions.assertFalse(verify(stationDAO).isStationExist(new Station("Station6")));
        Assertions.assertEquals(true,stationService.AddStationInDataBase(new Station("Station7")));
        Assertions.assertFalse(verify(stationDAO).insertStationToDataBase(new Station("Station7")));

    }

}
