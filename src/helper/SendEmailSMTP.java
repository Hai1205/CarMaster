package helper;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmailSMTP {
    public static void sendOTP(String emailTo, String otp) {
        String username = "carmaster1205@gmail.com";
        String password = "iyci jfyt gegl mfyp";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject("Your OTP:" + otp);
            message.setContent("<div style=\"font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2\">" +
"                <div style=\"margin:50px auto;width:70%;padding:20px 0\">" +
"                <div style=\"border-bottom:1px solid #eee\">" +
"                    <a href=\"#\" style=\"font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600\">CarMaster</a>" +
"                </div>" +
"                <p style=\"font-size:1.1em\">Please use the following security OTP for the CarMaster account:</p>" +
"                <h2 style=\"background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;\">" + otp + "</h2>" +
"                <p>This OTP can only be used once and will expire in 5 minutes. return to App and enter this OTP to verify your email address. Please don't share this OTP with others, don't reply this mail. If you require assitance, please contact our support team carmaster1205@gmail.com!</p>" +
"                <p style=\"font-size:0.9em;\">Thanks,<br />The CarMaster Team</p>" +
"                <hr style=\"border:none;border-top:1px solid #eee\" />" +
"                <div style=\"float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300\">" +
"                    <p>No. 273 An Duong Vuong, Ward 3, District 5, City. HCM</p>" +
"                    <p>Vietnam</p>" +
"                </div>" +
"                </div>" +
"                </div>", "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendPassword(String emailTo, String yourPassword) {
        String username = "carmaster1205@gmail.com";
        String password = "iyci jfyt gegl mfyp";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject("your PASSWORD: "+ yourPassword);
            message.setContent("<div style=\"font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2\">" +
"                <div style=\"margin:50px auto;width:70%;padding:20px 0\">" +
"                <div style=\"border-bottom:1px solid #eee\">" +
"                    <a href=\"#\" style=\"font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600\">CarMaster</a>" +
"                </div>" +
"                <p style=\"font-size:1.1em\">Congratulations on successfully joining CarMaster:</p>" +
"                <h2 style=\"background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;\">" + yourPassword + "</h2>" +
"                <p>This is your PASSWORD that can be used to log into your CarMaster account. Please don't share this PASSWORD with others, don't reply this mail. If you require assitance, please contact our support team carmaster1205@gmail.com!</p>" +
"                <p style=\"font-size:0.9em;\">Thanks,<br />The CarMaster Team</p>" +
"                <hr style=\"border:none;border-top:1px solid #eee\" />" +
"                <div style=\"float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300\">" +
"                    <p>No. 273 An Duong Vuong, Ward 3, District 5, City. HCM</p>" +
"                    <p>Vietnam</p>" +
"                </div>" +
"                </div>" +
"                </div>", "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
