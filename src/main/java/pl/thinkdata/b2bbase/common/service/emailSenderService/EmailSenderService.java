package pl.thinkdata.b2bbase.common.service.emailSenderService;

public interface EmailSenderService {

    boolean sendEmail(String to, String title, String content, String url);
}
