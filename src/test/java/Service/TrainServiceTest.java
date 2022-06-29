package Service;

import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.DAO.TicketDAO;
import com.example.finalproject3.DAO.TrainDAO;
import com.example.finalproject3.DAO.UserDAO;
import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Entity.Ticket;
import com.example.finalproject3.Entity.Train;
import com.example.finalproject3.Entity.User;
import com.example.finalproject3.Services.TrainService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

public class TrainServiceTest {
    @Mock
    UserDAO userDAO;
    @Mock
    TrainDAO trainDAO;
    @Mock
    TicketDAO ticketDAO;
    TrainService trainService;
    List<User> userDB;
    List<Train> trainDB;
    List<Ticket> ticketDB;
    public TrainServiceTest(){
        userDB = new ArrayList<>();
        trainDB = new ArrayList<>();
        ticketDB = new ArrayList<>();
        MockitoAnnotations.initMocks(this);
        trainService = new TrainService(userDAO,ticketDAO,trainDAO);
        for(int i =0;i<5;i++){
            User user = new User.UserBuilder().id(i+1)
                    .password("passwor"+i).email("mail"+i+"@gmail.com")
                    .money(100*i).build();
            userDB.add(user);
        }
        for(int i =0;i<3;i++){
            Train train = new Train.TrainBuilder().id(i+1)
                    .booked(i+2).seats(5).cost(150).enabled(true).build();
            train.setStations(new LinkedList<>());
            train.getStations().add(new Station("Station1"));
            train.getStations().add(new Station("Station2"));
            train.getStations().add(new Station("Station3"));
            trainDB.add(train);
        }
        Ticket ticket1 = new Ticket(trainDB.get(2));
        ticket1.setOwner(userDB.get(2));
        ticketDB.add(ticket1);
        Ticket ticket2 = new Ticket(trainDB.get(2));
        ticket2.setOwner(userDB.get(3));
        ticketDB.add(ticket2);
        Ticket ticket3 = new Ticket(trainDB.get(2));
        ticket3.setOwner(userDB.get(1));
        ticketDB.add(ticket3);
        Ticket ticket4 = new Ticket(trainDB.get(2));
        ticket4.setOwner(userDB.get(3));
        ticketDB.add(ticket4);
    }
    @Test
    void getRecipientsEmailsOfTrainTest() throws DAOException {
        when(ticketDAO.getAllEnableTicketsOnTrain(trainDB.get(2))).thenReturn(ticketDB);
        HashSet<String> emails = new HashSet<>();
        ticketDB.forEach((a)->emails.add(a.getOwner().getEmail()));
        Assertions.assertEquals(3,trainService.getRecipientsEmailsOfTrain(trainDB.get(2)).size());
        Assertions.assertEquals(emails.stream().collect(Collectors.toList()),
                trainService.getRecipientsEmailsOfTrain(trainDB.get(2)));
    }
    @Test
    void addTrainToDataBaseTest() throws DAOException {

        Train train = new Train.TrainBuilder().id(4).enabled(true).cost(150).build();
        Assertions.assertFalse(trainDB.contains(train));
        when(trainDAO.insertTrainInDataBase(train)).thenReturn(trainDB.add(train));
        trainService.addTrainToDataBase(train);
        Assertions.assertTrue(trainDB.contains(train));
    }
    @Test
    void getPaginatedTrainsByTwoStationFromRequestTest() throws DAOException {
        Station station1 = new Station("Station2");
        Station station2 = new Station("Station4");
        Train train = new Train.TrainBuilder().id(4).enabled(true).cost(150).build();
        train.setStations(new LinkedList<>());
        train.getStations().add(station1);
        train.getStations().add(station2);
        trainDB.add(train);
        Train train2 = new Train.TrainBuilder().id(5).enabled(true).cost(150).build();
        train2.setStations(new LinkedList<>());
        train2.getStations().add(station1);
        train2.getStations().add(station2);
        trainDB.add(train2);
        when(trainDAO.paginatedListOfEnabledTrainsByTwoStations(station1,station2,1,5))
                .thenReturn(trainDB.subList(3,5));
        List<Train> getTrains = trainService.getPaginatedTrainsByTwoStationFromRequest(station1,station2,1);
        Assertions.assertEquals(2,getTrains.size());
        for(Train t : getTrains){
            Assertions.assertTrue(t.getStations().contains(station1));
            Assertions.assertTrue(t.getStations().contains(station2));
            Assertions.assertTrue(t.getStations().indexOf(station1)
                    <t.getStations().indexOf(station2));
        }
    }
    @Test
    void getPaginatedListOfTrainsFromRequestTest() throws DAOException {
        Station station1 = new Station("Station2");
        Station station2 = new Station("Station4");
        Train train = new Train.TrainBuilder().id(4).enabled(true).cost(150).build();
        train.setStations(new LinkedList<>());
        train.getStations().add(station1);
        train.getStations().add(station2);
        trainDB.add(train);
        Train train2 = new Train.TrainBuilder().id(5).enabled(true).cost(150).build();
        train2.setStations(new LinkedList<>());
        train2.getStations().add(station1);
        train2.getStations().add(station2);
        trainDB.add(train2);
        Train train3 = new Train.TrainBuilder().id(6).enabled(true).cost(150).build();
        train3.setStations(new LinkedList<>());
        train3.getStations().add(station1);
        train3.getStations().add(station2);
        trainDB.add(train3);
        when(trainDAO.paginatedListOfEnableTrains(1,5))
                .thenReturn(trainDB.subList(0,5));
        when(trainDAO.paginatedListOfEnableTrains(2,5))
                .thenReturn(trainDB.subList(5,6));
        Assertions.assertEquals(5,trainService.getPaginatedListOfTrainsFromRequest(1).size());
        Assertions.assertEquals(1,trainService.getPaginatedListOfTrainsFromRequest(2).size());

    }
    @Test
    void getPaginatedTrainsByOneStationFromRequest() throws DAOException {
        Station station1 = new Station("Station1");
        Station station2 = new Station("Station2");
        Station station4 = new Station("Station4");
        Train train = new Train.TrainBuilder().id(4).enabled(true).cost(150).build();
        train.setStations(new LinkedList<>());
        train.getStations().add(station2);
        train.getStations().add(station4);
        trainDB.add(train);
        when(trainDAO.paginatedListOfEnabledTrainsByOneStation(station4,1,5))
                .thenReturn(trainDB.subList(3,4));
        when(trainDAO.paginatedListOfEnabledTrainsByOneStation(station2,1,5))
                .thenReturn(trainDB);
        when(trainDAO.paginatedListOfEnabledTrainsByOneStation(station1,1,5))
                .thenReturn(trainDB.subList(0,3));
        Assertions.assertEquals(4,trainService.getPaginatedTrainsByOneStationFromRequest(station2,1).size());
        Assertions.assertEquals(3,trainService.getPaginatedTrainsByOneStationFromRequest(station1,1).size());
        Assertions.assertEquals(1,trainService.getPaginatedTrainsByOneStationFromRequest(station4,1).size());
       for(Train t : trainService.getPaginatedTrainsByOneStationFromRequest(station1,1))
        Assertions.assertTrue(t.getStations().contains(station1));
        for(Train t : trainService.getPaginatedTrainsByOneStationFromRequest(station2,1))
            Assertions.assertTrue(t.getStations().contains(station2));
        for(Train t : trainService.getPaginatedTrainsByOneStationFromRequest(station4,1))
            Assertions.assertTrue(t.getStations().contains(station4));
        for(Train t : trainService.getPaginatedTrainsByOneStationFromRequest(station1,1))
            Assertions.assertFalse(t.getStations().contains(station4));

    }

}
