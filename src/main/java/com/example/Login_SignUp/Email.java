package com.example.Login_SignUp;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.security.SecureRandom;
import java.util.Properties;

public class Email {
    Session newSession = null;
    MimeMessage mimeMessage;
    private String OTP;
    public String getOTP() {
        return OTP;
    }

    public void sendEmail() throws MessagingException {
        String fromUser = "exceloneinfo@gmail.com";
        String fromUserPassword = "acte mwad aqco hifd";
        String emailHost = "smtp.gmail.com";
        Transport transport = newSession.getTransport("smtp");
        transport.connect(emailHost,fromUser, fromUserPassword);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully!");
    }
    public MimeMessage draftEmail(String emailRecipient) throws MessagingException {
        String emailSubject = "ExcelOne OTP";
        OTP = generateOTP();
        String emailBody = "<b>ExcelOne Authorization</b> <br><br>Thank you for signing up with us! Your One-Time Password is <b>" + OTP + "</b>.";
        mimeMessage = new MimeMessage(newSession);
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRecipient));
        mimeMessage.setSubject(emailSubject);
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(emailBody, "text/html");
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        mimeMessage.setContent(multipart);
        return mimeMessage;
    }
    public void setupServer(){
        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
//        properties.setProperty("javax.net.debug", "ssl,handshake");
        newSession = Session.getDefaultInstance(properties,null);
    }
    public String generateOTP(){
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
