package backend.service;

import backend.common.Constants;
import backend.common.JWTUtils;
import backend.forms.EmailConf;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final EmailConf emailConf;

    public EmailService(EmailConf emailConf) {
        this.emailConf = emailConf;
    }

    @Async
    public void sendMailInfo(String email, String location) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConf.getHost());
        mailSender.setPort(this.emailConf.getPort());
        mailSender.setUsername(this.emailConf.getUsername());
        mailSender.setPassword(this.emailConf.getPassword());

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper msg = new MimeMessageHelper(message);

        try {
            msg.setFrom("DistTrav@gmail.com");
            msg.setTo(email);
            msg.setSubject("Event Delete");
            msg.setText("<html><body>hi,<br/>The event was remotely located:"+ location +"</body></html>", true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendMail(String email) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConf.getHost());
        mailSender.setPort(this.emailConf.getPort());
        mailSender.setUsername(this.emailConf.getUsername());
        mailSender.setPassword(this.emailConf.getPassword());

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper msg = new MimeMessageHelper(message);

        try {
            final String token = generateToken(email);
            msg.setFrom("DistTrav@gmail.com");
            msg.setTo(email);
            msg.setSubject("Password Reset");
            msg.setText("<html><body>hi,<br/><a href='http://localhost:4200/forgot-password/" + token + "'> Click here</a> to reset password</body></html>", true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String generateToken(String email) {
        return Constants.HEADER_PREFIX + JWTUtils.generateToken(null, email, null, null, null);
    }
}
