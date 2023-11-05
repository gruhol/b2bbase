package pl.thinkdata.b2bbase.datafile.validator.predicate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.thinkdata.b2bbase.datafile.validator.ImageValidator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class ImageWidthPredicate<T extends ImageValidator.Params> implements Predicate<ImageValidator.Params> {

    @Override
    public boolean test(ImageValidator.Params params) {
        try {
            BufferedImage image = ImageIO.read(params.getFile().getInputStream());
            int width = image.getWidth();
            return width > params.getWidth();
        } catch (IOException e) {
            return false;
        }
    }
}
