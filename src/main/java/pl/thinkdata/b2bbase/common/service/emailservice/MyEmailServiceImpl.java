package pl.thinkdata.b2bbase.common.service.emailservice;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;


@Profile("prod")
@Service
@RequiredArgsConstructor
public class MyEmailServiceImpl implements MyEmailService {

    private final JavaMailSender javaMailSender;
    private final MessageGenerator messageGenerator;

    @Value("${mainEmailPage}")
    private String mailEmailPage;

    public void sendEmail(String to, String title, String content, String url) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setReplyTo(mailEmailPage);
            helper.setFrom(mailEmailPage);
            helper.setSubject(title);
            helper.setText(content, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mail);
    }
}
