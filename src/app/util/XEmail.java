/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Admin
 */
public class XEmail {

    public static void sendEmailInfor(String subject, String to, String message) throws AddressException, MessagingException, MessagingException {

        String fromManager = "minhhieu12322132@gmail.com";
        String password = "ywshfnmzrnnbhjbg";

        // Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Get Session object
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromManager, password);
            }
        });

        // Create Message object
        Message message1 = new MimeMessage(session);
        message1.setFrom(new InternetAddress(fromManager));
        message1.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message1.setSubject(subject);
        message1.setText(message);

        // Send message
        Transport.send(message1);
    }

}
