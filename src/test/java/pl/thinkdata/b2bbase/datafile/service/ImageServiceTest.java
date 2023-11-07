package pl.thinkdata.b2bbase.datafile.service;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.service.CompanyService;
import pl.thinkdata.b2bbase.datafile.util.TinyPngConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    private MessageGenerator messageGenerator;
    @Mock
    private TinyPngConverter tinyPngConverter;
    @Mock
    private CompanyService companyService;
    @Inject
    private ImageService imageService;

    @BeforeEach
    void init() {

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(-1);
        messageSource.setBasenames("classpath:i18n/messages");

        this.imageService = new ImageService(
                "test",
                new MessageGenerator(messageSource),
                this.tinyPngConverter,
                this.companyService);
    }

    @Test
    @Disabled
    void ShouldReturnNewFileName() throws IOException {
        when(companyService.getCompany(new MockHttpServletRequest())).thenReturn(createTempCompany());
        imageService.uploadImage(generateTemporaryImage(200, 100), "test", new MockHttpServletRequest());
    }

    private static MultipartFile generateTemporaryImage(int width, int height) throws IOException {
        Path tempImagePath = Files.createTempFile("tempImage", ".jpg");
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();
        ImageIO.write(bufferedImage, "jpg", tempImagePath.toFile());
        byte[] bytes = Files.readAllBytes(tempImagePath);
        return new MockMultipartFile("tempImage.jpg", "tempImage.jpg", "image/jpeg", bytes);
    }

    private Company createTempCompany() {
        return Company.builder()
                .name("Name")
                .build();
    }
}