package utilities;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.mail.*;

public class Mail {    
    private static final String FROM_EMAIL = "talan.transformationrh@gmail.com";
    private static final String FROM_EMAIL_PASSWORD = "jzxslxnnxycmsrsz";
    
    private Session newSession = null;
    private MimeMessage mimeMessage = null;


    public void setupServerProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        
        newSession = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, FROM_EMAIL_PASSWORD);
            }
        });
    }
    
    public MimeMessage draftEmail(String emailTo, String subject, String body) throws AddressException, MessagingException {
        mimeMessage = new MimeMessage(newSession);

        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
        
        mimeMessage.setSubject(subject);
        mimeMessage.setText(body, "utf-8");

        return mimeMessage;
    }
    
    public void sendEmail() throws MessagingException {
        Transport transport = newSession.getTransport("smtp");
        transport.connect();
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }
}