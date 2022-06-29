package com.example.finalproject3.Utility;

import java.util.Date;

public class Message {
    private String message;
    private Date dateOfNotification;
//    static public void sentMessageAboutRouteChanging(Route route) throws SQLException, ClassNotFoundException {
//        DBManager dbManager= DBManager.getInstance();
//        route.getIdFromDB();
//        Date today = Date.from(Instant.now());
//        today.setYear(2022);
//        java.util.List<Ticket> tickets = dbManager.getAllTickets().stream().filter((a)->{
//            return a.getTrain().getAgenda().get(a.getTrain().getRoute().getStations().get(0)).after(today)&&a.getTrain().getRoute().equals(route);
//        }).collect(Collectors.toList());
//        HashMap<Train, Set<User>> users= new HashMap<>();
//        for(Ticket t :tickets){
//            if(users.containsKey(t.getTrain())){
//                users.get(t.getTrain()).add(t.getOwner());
//            }else {Set<User> set=new HashSet<>();
//                set.add(t.getOwner());
//                users.put(t.getTrain(),set);
//            }
//        }
//        for(Train t: users.keySet()) {
//            for(User u: users.get(t)) {
//                Date date = Date.from(Instant.now());
//                date.setYear(date.getYear()+1900);
//                String train1= t.getRoute().getStations().get(0).getName() + "(" + Train.dateToString(t.getAgenda().get(t.getRoute().getStations().get(0)))+")";
//                String train2=  t.getRoute().getStations().get(t.getRoute().getStations().size() - 2).getName() +
//                        "(" + Train.dateToString(t.getAgenda().get(t.getRoute().getStations().get(t.getRoute().getStations().size()-2)))+")";
//                String message = String.format("Hello, we have to notify about that route of your train %s - %s"
//                        +" has been changed, please check it in your tickets.             Date:%s",train1,train2,Train.dateToString(date));
//                dbManager.sentMessagesToUsers(message,u);
//            }
//        }
//    }
//
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public Date getDate(){
        return dateOfNotification;
    }
    public void setDateOfNotification(Date d){
        this.dateOfNotification=d;
    }
    static public Message readMessage(String message){
        Message m= new Message();
        m.setMessage(message.substring(0,message.indexOf("             Date")));
//        m.setDateOfNotification(Train.stringToDate(message.substring(message.indexOf("Date:")+5)));/
        return m;
    }
}
