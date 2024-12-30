package com.example.realestate.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.*;

public class SendEmail {

    private final String senderEmailID = "tasneemjber@gmail.com";
    private final String senderPassword = "uriu dsph ohfy jqwk";
    private final String emailSMTPserver = "smtp.gmail.com";
    private final String emailServerPort = "587";
    private String receiverEmailID;
    private String emailSubject;
    private String emailBody;

    public SendEmail(String receiverEmailID, String emailSubject, String emailBody) {
        this.receiverEmailID = receiverEmailID;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;
    }

    public void send() {
        Properties props = new Properties();
        props.put("mail.smtp.host", emailSMTPserver);
        props.put("mail.smtp.port", emailServerPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new SMTPAuthenticator());
        session.setDebug(true);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(senderEmailID));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmailID));
            msg.setSubject(emailSubject);
            msg.setText(emailBody);
            Transport.send(msg);
            System.out.println("Message sent successfully.");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            System.err.println("Error: " + mex.getMessage());
        }
    }

    private class SMTPAuthenticator extends Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(senderEmailID, senderPassword);
        }
    }
}
