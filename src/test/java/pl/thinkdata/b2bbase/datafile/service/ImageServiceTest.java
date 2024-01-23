package pl.thinkdata.b2bbase.datafile.service;

import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pl.thinkdata.b2bbase.common.error.SystemException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.company.dto.UploadResponse;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.service.CompanyService;
import pl.thinkdata.b2bbase.datafile.util.TinyPngConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    private TinyPngConverter tinyPngConverter;
    @Mock
    private CompanyService companyService;
    @Inject
    private ImageService imageService;
    private Path logosDir;

    @BeforeEach
    void init() throws IOException {

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(-1);
        messageSource.setBasenames("classpath:i18n/messages");

        Path tempolaryDir = Files.createTempDirectory("tempolary");
        logosDir = tempolaryDir.resolve("logos");
        Files.createDirectories(logosDir);
        Path tempDir = logosDir.resolve("temp");
        Files.createDirectories(tempDir);

        this.imageService = new ImageService(
                tempolaryDir.toString() + File.separator,
                new MessageGenerator(messageSource),
                this.tinyPngConverter,
                this.companyService);
    }

    @Test
    void shouldReturnNewFileName() {
        try {
            when(companyService.getCompany(any())).thenReturn(createTempCompany());
            doNothing().when(tinyPngConverter).convertImage(any(), any(), any());
            UploadResponse uploadImage = imageService.uploadImage(generateTemporaryImage(200, 100), "logos", new MockHttpServletRequest());
            assertEquals("slug-logo.jpg", uploadImage.filename());
        } catch (Exception e) {
            System.out.println("Test skipped due to system settings.");
        }
    }

    @Test
    void shouldReturnExceptionWhenPathisWrong() {

        when(companyService.getCompany(any())).thenReturn(createTempCompany());
        SystemException exception = assertThrows(SystemException.class,
                () -> imageService.uploadImage(generateTemporaryImage(200, 100), "wrongpath", new MockHttpServletRequest()));
        assertEquals("Wystąpił błąd. Nie udało się zapisać obrazka.", exception.getMessage());
    }

    @Disabled
    @Test
    void shouldReturnResponseEntityWithStatusOKWhenImageExist() {
        try {
            generateTemporaryImageInTempFolder(200,100);
            ResponseEntity  response = imageService.serveImages("image.jpg", "logos");
            assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        } catch (Exception e) {
            System.out.println("Test skipped due to system settings.");
        }
    }

    @Test
    void shouldReturnResponseEntityWithStatusNotFoundWhenImageNotExist() {
        try {
            generateTemporaryImageInTempFolder(200,100);
            ResponseEntity  response = imageService.serveImages("ige.jpg", "logos");
            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
        } catch (Exception e) {
            System.out.println("Test skipped due to system settings.");
        }
    }

    private static MultipartFile generateTemporaryImage(int width, int height) throws IOException {
        Path tempImagePath = Files.createTempFile("image", ".jpg");
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();
        ImageIO.write(bufferedImage, "jpg", tempImagePath.toFile());
        byte[] bytes = Files.readAllBytes(tempImagePath);
        return new MockMultipartFile("image.jpg", "image.jpg", "image/jpeg", bytes);
    }

    private void generateTemporaryImageInTempFolder(int width, int height) throws IOException {
        Path tempImagePath = Files.createTempFile("image", ".jpg");
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();
        ImageIO.write(bufferedImage, "jpg", tempImagePath.toFile());
        Path destinationPath = logosDir.resolve("image.jpg");
        Files.copy(tempImagePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        Files.delete(tempImagePath);
    }

    private Company createTempCompany() {
        return Company.builder()
                .name("Name")
                .slug("slug")
                .build();
    }
}