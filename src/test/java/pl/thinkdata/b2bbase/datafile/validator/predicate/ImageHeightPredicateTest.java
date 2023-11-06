package pl.thinkdata.b2bbase.datafile.validator.predicate;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pl.thinkdata.b2bbase.datafile.validator.ImageValidator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImageHeightPredicateTest {


    @Test
    void shouldReturnFalseWhenImgHeightIsWithinTheDimensionsRange() throws IOException {
        MultipartFile tempImg = generateTemporaryImage(200, 100);
        ImageValidator.Params params = new ImageValidator.Params();
        params.setHeight(100);
        params.setWidth(200);
        params.setProportion(2);
        params.setFile(tempImg);
        ImageHeightPredicate<ImageValidator.Params> predicate = new ImageHeightPredicate<>();
        boolean result = predicate.test(params);
        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenImgHeightIsNotWithinTheDimensionsRange() throws IOException {
        MultipartFile tempImg = generateTemporaryImage(500, 500);
        ImageValidator.Params params = new ImageValidator.Params();
        params.setHeight(100);
        params.setWidth(200);
        params.setProportion(2);
        params.setFile(tempImg);
        ImageHeightPredicate<ImageValidator.Params> predicate = new ImageHeightPredicate<>();
        boolean result = predicate.test(params);
        assertTrue(result);
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