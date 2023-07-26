package pl.thinkdata.b2bbase.common.service.emailservice;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class MyEmailServiceMock implements MyEmailService {

    @Override
    public boolean sendEmail(String to, String title, String content, String url) {
        System.out.println("Url link verification: " + url);
        return true;
    }
}
