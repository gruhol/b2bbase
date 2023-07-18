package pl.thinkdata.b2bbase.common.service.emailservice;

public interface MyEmailService {

    void sendEmail(String to, String title, String content, String url);
}
