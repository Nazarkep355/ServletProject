package com.example.finalproject3.Utility;


import com.example.finalproject3.DAO.DAOException;
import com.example.finalproject3.Entity.Station;
import com.example.finalproject3.Entity.Ticket;
import com.example.finalproject3.Entity.Train;
import com.example.finalproject3.Services.TrainService;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Stateless
@LocalBean
public class EmailSessionBean {

   private int port = 465;
    private String host = "smtp.gmail.com";
    private String from = "TrainEmailer";
    private boolean auth = true;
    private String username = "TrainEmailer";
    private String password1 = "Tr@inEmailer08";

    private String password = "lesrghacbycgjgwg";
    private Protocol protocol = Protocol.SMTPS;
    private boolean debug = true;

    public void sendEmail(String to, String subject,String body) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        switch (protocol) {
            case SMTPS:
                props.put("mail.smtp.ssl.enable", true);
                break;
            case TLS:
                props.put("mail.smtp.starttls.enable", true);
                break;
        }
        Authenticator authenticator = null;
        if (auth) {
            props.put("mail.smtp.auth", true);
            authenticator = new Authenticator() {
                private PasswordAuthentication pa = new PasswordAuthentication(username, password);
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return pa;
                }
            };
        }
        Session session = Session.getInstance(props, authenticator);
        session.setDebug(debug);
        MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            message.setRecipients(javax.mail.Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setSentDate(Date.from(Instant.now()));
            message.setText(body);
            Transport.send(message);

    }
    public void sendEmails(List<String> recipients,String subject, String body )throws MessagingException{
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        switch (protocol) {
            case SMTPS:
                props.put("mail.smtp.ssl.enable", true);
                break;
            case TLS:
                props.put("mail.smtp.starttls.enable", true);
                break;
        }
        Authenticator authenticator = null;
        if (auth) {
            props.put("mail.smtp.auth", true);
            authenticator = new Authenticator() {
                private PasswordAuthentication pa = new PasswordAuthentication(username, password);
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return pa;
                }
            };
        }
        Session session = Session.getInstance(props, authenticator);
        session.setDebug(debug);
        MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            InternetAddress[] addresses = new InternetAddress[recipients.size()];
            for(int i =0;i<recipients.size();i++)
                addresses[i]=new InternetAddress(recipients.get(i));
            message.setRecipients(javax.mail.Message.RecipientType.TO, addresses);
            message.setSubject(subject);
            message.setSentDate(Date.from(Instant.now()));
            message.setText(body);
            Transport.send(message);

    }
    public void sendEmailAboutTicketBuying(Ticket ticket, HttpServletRequest request) throws MessagingException {
        Optional<Station> startOptional = Optional.ofNullable(ticket.getStartStation());
        Station start = startOptional.orElse(ticket.getTrain().getStations().get(0));
        String message = Utility.getBundle(request).getString("HelloYouJustBooked")+
                " "+TrainUtility.fromTo(ticket.getTrain())+". "+
                Utility.getBundle(request).getString("HeIsComingOn")+" "+ Utility
                .dateToString(ticket.getTrain().getAgenda().get(start))+". "
                +Utility.getBundle(request).getString("YouWatchSchedule");
        this.sendEmail(  ticket.getOwner().getEmail(),
                Utility.getBundle(request).getString("TicketBought"),message);
    }
    public void sendEmailAboutTrainCanceling(Train train,HttpServletRequest request) throws MessagingException, DAOException {
        TrainService trainService = new TrainService();
        List<String> recipients = trainService.getRecipientsEmailsOfTrain(train);
        if(train.getBooked()==0)
            return;
        String message = Utility.getBundle(request).getString("SorryTrain")+TrainUtility.fromTo(train)
                +" "+Utility.dateToString(train.getAgenda().get(train.getStations().get(0)))+
                Utility.getBundle(request).getString("CostWasReturned");
        this.sendEmails(recipients,Utility.getBundle(request).getString("TrainCanceled"),message);
    }
}
