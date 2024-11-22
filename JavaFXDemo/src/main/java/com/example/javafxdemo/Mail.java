package com.example.javafxdemo;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Mail {
    Session newSession = null;
    MimeMessage mimeMessage = null;
    public static void main(String[] args) throws MessagingException {
        Mail mail = new Mail();
        mail.setupServer();
        mail.draft();
        mail.send();
    }
    public void setupServer(){
        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        newSession = Session.getDefaultInstance(properties,null);
    }

    public MimeMessage draft() throws MessagingException {
        String[] emailRecipient = new String[]{"bebedorkarolvincent@gmail.com", "valencia.judemikaell@gmail.com", "noehboiser221@gmail.com"};
        String emailBody = "Your OTP is 123 ayaw kalimti";
        String emailSubject = "bahogoten si christian";
        mimeMessage = new MimeMessage(newSession);

        for(String r : emailRecipient){
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(r));
        }

        //mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRecipient[0]));
        mimeMessage.setSubject(emailSubject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(emailBody, "html/text");
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        mimeMessage.setContent(multipart);
        return mimeMessage;
    }

    public void send() throws MessagingException {
        String fromUser = "exceloneinfo@gmail.com";

        String fromUserPassword = "upcw teeu ahih xmrv";
        String emailHost = "smtp.gmail.com";
        Transport transport = newSession.getTransport("smtp");
        transport.connect(emailHost, fromUser, fromUserPassword);
        transport.sendMessage(mimeMessage, null);
        transport.close();
        System.out.println("sent success");
    }
}
