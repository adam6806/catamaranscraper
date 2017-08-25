package com.github.adam6806.catamaranscraper.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    public void sendEmail(String emailMessage, String toEmail){

        try{
            final String fromEmail = "asmith0935@gmail.com"; //requires valid gmail id
            final String password = "GmailaA1!"; // correct password for gmail id

            System.out.println("TLSEmail Start");
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };
            Session session = Session.getInstance(props, auth);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            System.out.println("Mail Check 2");

            message.setSubject("New Catamarans From Adam's Catamaran Scraper");
            message.setContent(emailMessage, "text/html");

            System.out.println("Mail Check 3");

            Transport.send(message);
            System.out.println("Mail Sent");
        }catch(Exception ex){
            System.out.println("Mail fail");
            System.out.println(ex);
        }
    }
}