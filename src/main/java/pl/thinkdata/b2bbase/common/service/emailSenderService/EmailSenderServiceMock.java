package pl.thinkdata.b2bbase.common.service.emailSenderService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
public class EmailSenderServiceMock implements EmailSenderService {

    @Override
    public boolean sendEmail(String to, String title, String content, String url) {
        System.out.println("Url link verification: " + url);
        return true;
    }
}
