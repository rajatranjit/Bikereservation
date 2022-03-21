package com.example.BikeReservation.BikeReservation.Service;

import com.example.BikeReservation.BikeReservation.Entity.CustomerInfo;
import org.springframework.stereotype.Service;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailAlertService {
    public void sendNotification(CustomerInfo customerInfo, String job) {
        final String username = "bikereservation2274@gmail.com";
        final String password = "aicrcjcbwejhpvgh";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        String header = "";
        String body = "";
        if (job.equals("book")){
            header = "Congratulation your Bike reservation is Successful!!!";
            body = "Dear " + customerInfo.getName() + ","
                    + "\n\nThank you for the reservation!" + "Your total fare is : " + customerInfo.getFare()+".\n" +
                    "Your bike reservation is from "+customerInfo.getPickupTime()+" to "+customerInfo.getArrivalTime();
        } else if (job.equals("update")){
            header = "Congratulation your Bike reservation update is Successful!!!";
            body = "Dear " + customerInfo.getName() + ","
                    + "\n\nThank you for the reservation!" + "Your total fare is : " + customerInfo.getFare()+".\n" +
                    "Your bike reservation is from "+customerInfo.getPickupTime()+" to "+customerInfo.getArrivalTime();
        } else if (job.equals("cancel")){
            header = "Your cancellation of you booking is successful!!!";
            body = "Dear " + customerInfo.getName() + ","
                    + "\n\nYour cancellation is successful!" + "Your total fare refunded is : " + customerInfo.getFare()+".";
        }
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(customerInfo.getEmail())
            );
            message.setSubject(header);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email Sent successfully!!!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}