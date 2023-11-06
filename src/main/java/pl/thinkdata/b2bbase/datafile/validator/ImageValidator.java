package pl.thinkdata.b2bbase.datafile.validator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.thinkdata.b2bbase.common.error.ValidationException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.datafile.validator.predicate.ImageHeightPredicate;
import pl.thinkdata.b2bbase.datafile.validator.predicate.ImageProportionPredicate;
import pl.thinkdata.b2bbase.datafile.validator.predicate.ImageWidthPredicate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ImageValidator {

    private static final String THE_IMAGE_WIDTH_MUST_BE_A_MAXIMUM_OF_PX = "the.image.width.must.be.a.maximum.of.px";
    private static final String THE_IMAGE_HEIGHT_MUST_BE_A_MAXIMUM_OF_PX = "the.image.height.must.be.a.maximum.of.px";
    private static final String THE_IMAGE_PROPORTIONS_MUST_OF_ONE = "the.image.proportions.must.be.of_one";
    private MultipartFile file;
    private MessageGenerator messageGenerator;
    private static final String error_message = "image.validation.error.the.following.parameters.contain.a.validation.error";
    private static final String width = "width";
    private static final String height = "height";
    private static final String proportions = "proportions";

    private ImageWidthPredicate<ImageValidator.Params> imageWidthPredicate = new ImageWidthPredicate<ImageValidator.Params>();
    private ImageHeightPredicate<Params> imageHeightPredicate = new ImageHeightPredicate<ImageValidator.Params>();
    private ImageProportionPredicate<Params> imageProportionPredicate = new ImageProportionPredicate<ImageValidator.Params>();

    public ImageValidator(MultipartFile file, MessageGenerator messageGenerator) {
        this.file = file;
        this.messageGenerator = messageGenerator;
    }

    public void valid(int requiredWidth, int requiredHeight, int requiredProportion) {
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
        if (imageHeightPredicate.test(parameters)) {
            error = true;
            fields.put(height, messageGenerator.get(THE_IMAGE_HEIGHT_MUST_BE_A_MAXIMUM_OF_PX, new Object[]{requiredHeight}));
        }
        if (imageProportionPredicate.test(parameters)) {
            error = true;
            fields.put(proportions, messageGenerator.get(THE_IMAGE_PROPORTIONS_MUST_OF_ONE, new Object[]{requiredProportion}));
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
