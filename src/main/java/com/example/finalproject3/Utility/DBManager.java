//package com.example.finalproject3.Utility;
//
////import com.example.finalproject3.Classes.Entity.*;
//import com.example.finalproject3.Entity.*;
//
//import java.sql.*;
//import java.time.Instant;
//import java.util.*;
//import java.util.Date;
//
//public class DBManager {
//
//    private static final String INSERT_ROUTE_QUERY = "insert into routes values(default, ?, ?,?)";
//    private static final String INSERT_STATION_QUERY = "insert into stations values(?)";
//    private static final String SELECT_ALL_ENABLE_TRAINS_QUERY = "select * from trains where enabled = true";
//    private static final String SELECT_ALL_ROUTES_QUERY = "select * from routes";
//    private static final String UPDATE_ROUTES_QUERY = "update routes set stations=?,delays=?,cost=? where id =?";
//    private static final String UPDATE_TRAIN_SET_ENABLE_FALSE_QUERY = "update trains set enabled = false where id =?";
//    private static final String UPDATE_USER_INFORMATION_QUERY = "update users set money= ?,usertype=?,password=? where id =?";
//    private static final String INSERT_TICKET_QUERY = "insert into tickets values (default,?,?,?,?,?,?,true)";
//    private static final String INSERT_NEW_BOOKED_QUERY = "update trains set booked = booked+1 where id =?";
//    private static final String SELECT_ENABLE_TICKETS_QUERY = "select * from tickets where enabled = true";
//    private static final String INSERT_NEW_TRAIN_QUERY = "insert into trains values(default,?,?,?,?,?,0,true)";
//    private static final String UPDATE_TRAIN_AGENDA_QUERY ="update trains set agenda=?, stations= ? where id =?";
//    private static final String UPDATE_TICKET_ENABLE_QUERY="update tickets set enabled = ? where id =?";
//    private static final String SELECT_USER_BY_EMAIL_QUERY="select * from users where email=?";
//    private static final String SELECT_TRAIN_BY_ID_QUERY= "select * from trains where id =?";
//    private static final String SELECT_ROUTE_BY_ID_QUERY= "select * from routes where id=?";
//    private Connection connection;
//    private static DBManager instance;
//    public static DBManager getInstance() throws SQLException, ClassNotFoundException {
//        if(instance==null){
//            Class.forName("org.postgresql.Driver");
//            instance= new DBManager();
//            instance.connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres","3228");}
//        return instance;
//    }
//
//
//    static public User getUser(String email, String password) throws SQLException, IllegalArgumentException, ClassNotFoundException {
//        User u=null;
//        try(ResultSet set = getInstance().connection.createStatement().executeQuery("select *from users where email = '"+email+"'");
//        ) {
//            set.next();
//            String pass = set.getString("password");
//            if (!pass.equals(password)) throw new IllegalArgumentException("Wrong password!");
//
//            int ut=Integer.parseInt(set.getString("usertype"));
//            String name=set.getString("name");
//            int id=Integer.parseInt(set.getString("id"));
//            int money=Integer.parseInt(set.getString("money"));
//            ArrayList<Message> messages=new ArrayList<>();
//            Array array=set.getArray("messages");
//            if(array!=null){Object ob=array.getArray();
//                Object[] objects= (Object[]) ob;
//                for(Object obj: objects){
//                    String str=(String)obj;
//                    messages.add(Message.readMessage(str));}}
//            u=new User.UserBuilder()
//                    .userType(ut)
//                    .id(id)
//                    .messages(messages)
//                    .money(money)
//                    .email(email)
//                    .password(password)
//                    .name(name)
//                    .build();
//        }
//        return u;
//    }
//    public static ArrayList<String> getAllEmails() throws SQLException, ClassNotFoundException {
//        ArrayList<String> emails = new ArrayList<>();
//        try(Statement statement=getInstance().connection.createStatement();){
//            ResultSet set = statement.executeQuery("select email from users");
//            while (set.next()){
//                emails.add(set.getString("email"));
//            }
//        }
//        return emails;
//    }
//    public static boolean addRouteToDB(Route route) throws SQLException, ClassNotFoundException {
//        try(PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(INSERT_ROUTE_QUERY)){
//            Object[] arrayStations=new Object[route.getStations().size()];
//            for(int i =0;i<route.getStations().size();i++){
//                arrayStations[i]=route.getStations().get(i).getName();
//            }
//            Object[] arrayDelays= route.getDelays().toArray();
//            statement.setArray(1,getInstance()
//                    .connection.createArrayOf("VARCHAR",arrayStations));
//            statement.setInt(2,route.getCost());
//            statement.setArray(3,getInstance()
//                    .connection.createArrayOf("integer",arrayDelays));
//                return statement.execute();
//        }
//    }
//
//    public static ArrayList<Route> getRoutes() throws SQLException, ClassNotFoundException {
//        ArrayList<Route> res = new ArrayList<>();
//        try  (Statement stmnt =getInstance().connection.createStatement();) {
//            ResultSet set = stmnt.executeQuery(SELECT_ALL_ROUTES_QUERY);
//            while (set.next()) {
//                Route route = new Route();
//                route.setId(set.getInt("id"));
//                Array stations = set.getArray("stations");
//                int cost = set.getInt("cost");
//                Object obj = stations.getArray();
//                Object[] arr = (Object[]) obj;
//                for (Object ob : arr) {
//                    route.addStation(new Station(String.valueOf(ob)));
//                }
//                Array delaysArray = set.getArray("delays");
//                obj= delaysArray.getArray();
//                Object[] delays = (Object[]) obj;
//                for(Object ob : delays){
//                    route.addDelay((Integer) ob);
//                }
//                route.setCost(cost);
//                res.add(route);
//            }
//        }
//        return res;
//    }
//    public ArrayList<Station> getAllStations() throws SQLException {
//        try(Statement statement= connection.createStatement();){
//            ResultSet set =statement.executeQuery("select*from stations");
//            ArrayList<Station> stations= new ArrayList<>();
//            while (set.next()){
//                stations.add(new Station(set.getString("name")));
//            }
//
//            return stations;}
//    }
//    public void addStationToDB(Station station) throws SQLException {
//        try (PreparedStatement statement =connection.prepareStatement(INSERT_STATION_QUERY);) {
//            statement.setString(1,station.getName());
//            statement.execute();
//        }
//    }
//    public void registerUser(String email, String password, String name) throws SQLException, IllegalArgumentException, ClassNotFoundException {
//        try(Statement statement = connection.createStatement();){
//            ArrayList<String> emails = getAllEmails();
//            if(emails.contains(email))throw new IllegalArgumentException("Email is already in use");
//            String str= String.format("insert into users values(default, '%s', '%s','%s',0,0,null)",name,email,password);
//            statement.execute(str);
//        }
//
//    }
//
//
//    static public ArrayList<Train> getAllEnableTrains() throws SQLException, ClassNotFoundException {
//        ArrayList<Train> res = new ArrayList<>();
//        try(Statement stmt = getInstance().connection.createStatement();){
//            ResultSet set =stmt.executeQuery(SELECT_ALL_ENABLE_TRAINS_QUERY);
//            ArrayList<Route> routes=getRoutes();
//
//            while (set.next()){
//                LinkedList<Station> TrainStations=new LinkedList<>();
//                Array stats = set.getArray("stations");
//                Object object = stats.getArray();
//                Object[] arrayofStations = (Object[]) object;
//                for (Object ob : arrayofStations ) {
//                    TrainStations.add(new Station(String.valueOf(ob)));
//                }
//                int idRoute =Integer.parseInt(set.getString("route"));
//                Train t=new Train(new Route());
//                for(Route r :routes)
//                    if(r.getId()==idRoute)
//                    { t= new Train.TrainBuilder().route(r).build();}
//                t.setStations(TrainStations);
//                Array arr =set.getArray("agenda");
//                Object obj = arr.getArray();
//                Object[] array =(Object[]) obj;
//                HashMap<Station,java.util.Date> agenda= new HashMap<>();
//                for(int i =0;i<array.length;i++){
//                    Timestamp timestamp = (Timestamp) array[i];
//                    agenda.put(t.getStations().get(i),new Date(timestamp.getTime()));
//                }
//
//                t.setAgenda(agenda);
//                t.setEnabled(set.getBoolean("enabled"));
//                t.setId(set.getInt("id"));
//                t.setSeats(set.getInt("seats"));
//                t.setBooked(set.getInt("booked"));
//                res.add(t);
//            }
//            return res;}
//    }
//    public void sentMessagesToUsers(String message, User ... users){
//        try(Statement statement = connection.createStatement()) {
//            for (User u : users) {
////                String sql="update users set messages= array_append(messages,'"+message+"') where id ="+u.getId();
//                statement.execute("update users set messages= array_append(messages,'"+message+"') where id ="+u.getId());
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public static boolean isPasswordCorrect(String email,String password) throws SQLException, ClassNotFoundException {
//        try(PreparedStatement statement=DBManager.getInstance().connection.prepareStatement(SELECT_USER_BY_EMAIL_QUERY)){
//            statement.setString(1,email);
//            ResultSet set = statement.executeQuery();
//            set.next();
//                User user = new User();
//                user.setId(set.getInt("id"));
//                user.setPassword(set.getString("password"));
//               if(user.getPassword().equals(password))return true;
//        }
//        return false;
//    }
//
//    static public void addTrainToDB(Train train) throws SQLException, ClassNotFoundException {
//        try(PreparedStatement stmt =getInstance().connection.prepareStatement(INSERT_NEW_TRAIN_QUERY)){
//            ArrayList<Route> allRts=getRoutes();
//            boolean found =false;
//            int rID=0;
//            for(Route r : allRts)
//                if(train.getRoute().equals(r))
//                {rID=r.getId();found=true;}
//            allRts=getRoutes();
//            for(Route r : allRts)
//                if(train.getRoute().equals(r))
//                {rID=r.getId();found=true;}
//            List<String> stationStringList= new ArrayList<>();
//            for(Station station :train.getStations())
//                stationStringList.add(station.getName());
//            stmt.setArray(1,getInstance()
//                    .connection.createArrayOf("varchar",stationStringList.toArray()));
//            List<Timestamp> timestamps = new ArrayList<>();
//            for(Station station: train.getStations()) {
//                Date date = train.getAgenda().get(station);
//                Timestamp timestamp = new Timestamp(date.getTime());
//                timestamps.add(timestamp);
//            }
//            stmt.setArray(2,getInstance()
//                    .connection.createArrayOf("timestamp",timestamps.toArray()));
//            stmt.setInt(3,train.getCost());
//            stmt.setInt(4,rID);
//            stmt.setInt(5,train.getSeats());
//            stmt.execute();}
//    }
//    static public void changeAgendaOfTrain(Train train) throws SQLException, ClassNotFoundException {
//        try(PreparedStatement stmt= getInstance().connection.prepareStatement(UPDATE_TRAIN_AGENDA_QUERY)){
//            List<Timestamp> timestamps = new ArrayList<>();
//            for(Station station : train.getStations())
//            {Date date = train.getAgenda().get(station);
//                timestamps.add(new Timestamp(date.getTime()));}
//            List<String> stationStringList= new ArrayList<>();
//            for(Station station :train.getStations())
//                stationStringList.add(station.getName());
//            stmt.setArray(2,getInstance()
//                    .connection.createArrayOf("varchar",stationStringList.toArray()));
//            stmt.setArray(1,getInstance()
//                    .connection.createArrayOf("timestamp",timestamps.toArray()));
//            stmt.setInt(3,train.getId());
//            stmt.execute();
//        }
//    }
//
//    static public boolean changeRoute(Route route) throws SQLException, ClassNotFoundException {
//        try(  PreparedStatement statement=getInstance().connection.prepareStatement(UPDATE_ROUTES_QUERY)) {
//            Object[] arrayStations=new Object[route.getStations().size()];
//            for(int i =0;i<route.getStations().size();i++){
//                arrayStations[i]=route.getStations().get(i).getName();
//            }
//            Object[] arrayDelays= route.getDelays().toArray();
//            statement.setArray(1,getInstance()
//                    .connection.createArrayOf("VARCHAR",arrayStations));
//            statement.setArray(2,getInstance()
//                    .connection.createArrayOf("integer",arrayDelays));
//            statement.setInt(3,route.getCost());
//
//            statement.setInt(4,route.getId());
//            return statement.execute();
//        }
//    }
//
//    static public boolean addTicket(User u, Train train,Station startStation, Station endStation)
//            throws SQLException, ClassNotFoundException {
//        User user = getUser(u.getEmail(),u.getPassword());
//        int TID=train.getId();
//        if(train.getSeats()<=train.getBooked())throw new IllegalArgumentException("No free seats!");
//        try(PreparedStatement statement=getInstance().connection.prepareStatement(INSERT_TICKET_QUERY);){
//
//            String start=null;
//            String end = null;
//            if(startStation!=null&&endStation!=null){
//                start=startStation.getName();
//                end=endStation.getName();
//            }
//            u.setMoney(u.getMoney()-train.getCost());
//            statement.setInt(1,user.getId());
//            statement.setInt(2,TID);
//            statement.setInt(3,train.getCost());
//            statement.setTimestamp(4,Timestamp.from(Instant.now()));
//            statement.setString(5,start);
//            statement.setString(6,end);
//            updateUserMoney(user, u.getMoney());
//            addNewBookedSeatOnTrain(train);
//            return statement.execute();
//       }
//    }
//    public ArrayList<Ticket> findAllTicketsOfUser(User user) throws SQLException, ClassNotFoundException {
//        ArrayList<Ticket> tickets =new ArrayList<>();
//        try(Statement statement = connection.createStatement(); ){
//            ResultSet set =statement.executeQuery("select *from tickets where userid = "+user.getId());
//            ArrayList<Train> trains = getAllEnableTrains();
//            while (set.next()){
//                Ticket ticket= null;
//                for(Train t : trains){
//                    if(t.getId()==set.getInt("trainid"))
//                        ticket= new Ticket(t);
//                }
//                ticket.setOwner(user);
//                ticket.setId(set.getInt("id"));
//                String date = set.getString("date");
//                java.util.Date d= new java.util.Date();
//                d.setDate(Integer.parseInt(date.substring(0,date.indexOf("-"))));
//                date=date.substring(date.indexOf("-")+1);
//                d.setMonth(Integer.parseInt(date.substring(0,date.indexOf("-")))-1);
//                date=date.substring(date.indexOf("-")+1);
//                d.setYear(Integer.parseInt(date.substring(0,date.indexOf("-"))));
//                date=date.substring(date.indexOf("-")+1);
//                d.setHours(Integer.parseInt(date.substring(0,date.indexOf(":"))));
//                date=date.substring(date.indexOf(":")+1);
//                d.setMinutes(Integer.parseInt(date));
//                ticket.setDate(d);
//                ticket.setStartStation(new Station(set.getString("startstation")));
//                ticket.setEndStation(new Station(set.getString("endstation")));
//                tickets.add(ticket);
//            }
//        }
//        if(tickets.size()<1)
//            throw new IllegalArgumentException("There is no tickets");
//        return tickets;
//    }
//
//    static public ArrayList<Ticket> getAllEnableTickets() throws SQLException, ClassNotFoundException {
//        ArrayList<Ticket> tickets =new ArrayList<>();
//        try(Statement statement = getInstance().connection.createStatement(); ){
//            ResultSet set =statement.executeQuery(SELECT_ENABLE_TICKETS_QUERY);
//            ArrayList<Train> trains = getAllEnableTrains();
//            while (set.next()){
//                Ticket ticket= null;
//                for(Train t : trains){
//                    if(t.getId()==set.getInt("trainid"))
//                        ticket= new Ticket(t);
//                }
//                int id = set.getInt("userid");
//                ticket.setOwner(DBManager.findUserById(id));
//                ticket.setId(set.getInt("id"));
//                Timestamp date = set.getTimestamp("date");
//                java.util.Date d= new Date();
//                d.setYear(date.getYear()+1900);
//                d.setMonth(date.getMonth());
//                d.setDate(date.getDate());
//                d.setHours(date.getHours());
//                d.setMinutes(date.getMinutes());
//                d.setSeconds(0);
//                ticket.setDate(d);
//                Station start = null;
//                Station end = null;
//                if(set.getString("startstation")!=null)
//                if(set.getString("startstation")!=null||!set.getString("startstation").equals("null")){
//                    end=new Station(set.getString("endstation"));
//                    start =new Station(set.getString("startstation"));
//                }
//                ticket.setStartStation(start);
//                ticket.setEndStation(end);
//                tickets.add(ticket);
//            }
//        }
//        return tickets;
//    }
//    public static boolean updateTrainNotEnabled(Train train) throws SQLException, ClassNotFoundException {
//        try(PreparedStatement statement= DBManager.getInstance()
//                .connection.prepareStatement(UPDATE_TRAIN_SET_ENABLE_FALSE_QUERY)){
//            statement.setInt(1,train.getId());
//            return statement.execute();
//        }
//    }
//    public static User findUserById(int id) throws SQLException, ClassNotFoundException {
//        try(Statement statement = DBManager.getInstance().connection.createStatement()){
//            ResultSet set = statement.executeQuery("select *from users where id ="+id);
//            set.next();
//            String pass = set.getString("password");
//            String email =set.getString("email");
//            int ut=Integer.parseInt(set.getString("usertype"));
//            String name=set.getString("name");
//            id=Integer.parseInt(set.getString("id"));
//            int money=Integer.parseInt(set.getString("money"));
//            ArrayList<Message> messages=new ArrayList<>();
//            Array array=set.getArray("messages");
//            if(array!=null){Object ob=array.getArray();
//                Object[] objects= (Object[]) ob;
//                for(Object obj: objects){
//                    String str=(String)obj;
//                    messages.add(Message.readMessage(str));}}
//           User user=new User.UserBuilder()
//                    .userType(ut)
//                    .id(id)
//                    .messages(messages)
//                    .money(money)
//                    .email(email)
//                    .password(pass)
//                    .name(name)
//                    .build();
//            return user;
//        }
//    }
//    public static boolean updateUserMoney(User user, int money) throws SQLException, ClassNotFoundException {
//        try(PreparedStatement statement= DBManager.getInstance()
//                .connection.prepareStatement(UPDATE_USER_INFORMATION_QUERY)){
//                statement.setInt(1,money);
//                statement.setInt(2,user.getUserType().ordinal());
//                statement.setString(3,user.getPassword());
//                statement.setInt(4,user.getId());
//            return statement.execute();
//        }
//    }
//    public static boolean updateTicketEnabled(Ticket ticket,Boolean bool) throws SQLException, ClassNotFoundException {
//        try(PreparedStatement statement= DBManager.getInstance().connection.prepareStatement(UPDATE_TICKET_ENABLE_QUERY)){
//            statement.setBoolean(1,bool);
//            statement.setLong(2,ticket.getId());
//            return statement.execute();
//        }
//    }
//    public static boolean addNewBookedSeatOnTrain(Train train) throws SQLException, ClassNotFoundException {
//        try(PreparedStatement statement = getInstance().connection.prepareStatement(INSERT_NEW_BOOKED_QUERY)){
//            statement.setInt(1,train.getId());
//            return statement.execute();
//        }
//    }
//    public static Optional<Train> getTrainById(int id) throws SQLException, ClassNotFoundException {
//        try(PreparedStatement statement = getInstance().connection.prepareStatement(SELECT_TRAIN_BY_ID_QUERY)){
//            statement.setInt(1,id);
//            ResultSet set = statement.executeQuery();
//            set.next();
//            Optional<Train> trainOptional = Optional.empty();
//            int trainId = set.getInt("id");
//            if(trainId>0){
//            LinkedList<Station> stationLinkedList=new LinkedList<>();
//            Array stats = set.getArray("stations");
//            Object object = stats.getArray();
//            Object[] arrayOfStations = (Object[]) object;
//            for (Object ob : arrayOfStations ) {
//                stationLinkedList.add(new Station(String.valueOf(ob)));
//            }
//            int idRoute =Integer.parseInt(set.getString("route"));
//            Optional<Route> optionalRoute = getRouteById(idRoute);
//            Train train=null;
//            if(!optionalRoute.isPresent())
//            throw new IllegalArgumentException("Route not found");
//            else train = new Train(optionalRoute.get());
//            Array arr =set.getArray("agenda");
//            train.setStations(stationLinkedList);
//            Object obj = arr.getArray();
//            Object[] array =(Object[]) obj;
//            HashMap<Station,java.util.Date> agenda= new HashMap<>();
//            for(int i =0;i<array.length;i++){
//                Timestamp timestamp = (Timestamp) array[i];
//                agenda.put(train.getStations().get(i),new Date(timestamp.getTime()));
//            }
//            train.setAgenda(agenda);
//            train.setEnabled(set.getBoolean("enabled"));
//            train.setId(set.getInt("id"));
//            train.setSeats(set.getInt("seats"));
//            train.setBooked(set.getInt("booked"));
//            trainOptional= Optional.of(train);
//            return trainOptional;
//        }else return trainOptional;
//     }
//    }
//    public static Optional<Route> getRouteById(int id) throws SQLException, ClassNotFoundException {
//        try(PreparedStatement statement = getInstance().connection.prepareStatement(SELECT_ROUTE_BY_ID_QUERY)){
//            statement.setInt(1,id);
//            ResultSet set = statement.executeQuery();
//            set.next();
//            Route route = new Route();
//            Optional<Route> routeOptional=Optional.empty();
//            route.setId(set.getInt("id"));
//            if(route.getId()>0){
//            Array stations = set.getArray("stations");
//            int cost = set.getInt("cost");
//            Object obj = stations.getArray();
//            Object[] arr = (Object[]) obj;
//            for (Object ob : arr) {
//                route.addStation(new Station(String.valueOf(ob)));
//            }
//            Array delaysArray = set.getArray("delays");
//            obj= delaysArray.getArray();
//            Object[] delays = (Object[]) obj;
//            for(Object ob : delays){
//                route.addDelay((Integer) ob);
//            }
//            route.setCost(cost);
//            routeOptional=Optional.of(route);}
//            return routeOptional;
//        }
//    }
//}
