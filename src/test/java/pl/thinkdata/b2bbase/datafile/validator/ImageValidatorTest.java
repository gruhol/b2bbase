package pl.thinkdata.b2bbase.datafile.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pl.thinkdata.b2bbase.common.error.ValidationException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ImageValidatorTest {

    private MessageGenerator messageGenerator;

    @BeforeEach
    void init() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(-1);
        messageSource.setBasenames("classpath:i18n/messages");
        messageGenerator = new MessageGenerator(messageSource);
    }

    @Test
    void ShouldThrowValidationExceptionWhenImgHeightIsWithinTheDimensionsRange() throws IOException {
        MultipartFile tempImg = generateTemporaryImage(800, 1000);
        ImageValidator.Params params = new ImageValidator.Params();
        params.setHeight(100);
        params.setWidth(200);
        params.setProportion(2);
        params.setFile(tempImg);
        ImageValidator imageValidator = new ImageValidator(tempImg, messageGenerator);

        ValidationException exception = assertThrows(ValidationException.class, () -> imageValidator.valid(200, 100, 2));
        assertEquals(3, exception.getFileds().size());
        assertEquals("Szerokość obrazka musi być maksymalnie 200 px.", exception.getFileds().get("width"));
        assertEquals("Wysokość obrazka musi być maksymalnie 100 px.", exception.getFileds().get("height"));
        assertEquals("Proporcje obrazka muszą wynosić 2 do 1.", exception.getFileds().get("proportions"));
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

}