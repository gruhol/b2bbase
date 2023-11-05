package pl.thinkdata.b2bbase.datafile.validator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.thinkdata.b2bbase.common.error.ValidationException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.datafile.validator.predicate.ImageWidthPredicate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ImageValidator {

    private static final String THE_IMAGE_WIDTH_MUST_BE_A_MAXIMUM_OF_PX = "the.image.width.must.be.a.maximum.of.px";
    private MultipartFile file;
    private MessageGenerator messageGenerator;
    private static final String error_message = "image.validation.error.the.following.parameters.contain.a.validation.error";
    private static final String width = "width";
    private static final String height = "height";
    private static final String proportions = "proportions";
    private static final int requiredWidth = 1000;
    private static final int requiredHeight = 1000;
    private static final int requiredProportion = 2;

    private ImageWidthPredicate<ImageValidator.Params> imageWidthPredicate = new ImageWidthPredicate<ImageValidator.Params>();

    public ImageValidator(MultipartFile file, MessageGenerator messageGenerator) {
        this.file = file;
        this.messageGenerator = messageGenerator;
    }

    public void valid() {
        ImageValidator.Params parameters = new ImageValidator.Params();
        parameters.setFile(file);
        parameters.setHeight(requiredHeight);
        parameters.setWidth(requiredWidth);
        parameters.setProportion(requiredProportion);

        boolean error = false;
        Map<String, String> fields = new HashMap<>();
        if (imageWidthPredicate.test(parameters)) {
            error = true;
            fields.put(width, messageGenerator.get(THE_IMAGE_WIDTH_MUST_BE_A_MAXIMUM_OF_PX, new Object[]{requiredWidth}));
        }

        if (error) {
            throw new ValidationException(messageGenerator.get(error_message), fields);
        }
    }

    @Setter
    @Getter
    public class Params {
        private int width;
        private int height;
        private double proportion;
        private MultipartFile file;
    }
}
