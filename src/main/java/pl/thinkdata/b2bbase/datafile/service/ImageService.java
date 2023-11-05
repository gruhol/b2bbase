package pl.thinkdata.b2bbase.datafile.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.thinkdata.b2bbase.common.error.SystemException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.company.dto.UploadResponse;
import pl.thinkdata.b2bbase.datafile.validator.ImageValidator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.AN_ERROR_OCCURED_FAILED_TO_SAVE_THE_IMAGE;

@Service
public class ImageService {

    public static final int MAX_WIDTH = 2000;
    public static final int MAX_HEIGHT = 1000;
    public static final double ALLOWED_PROPORTION = 2;
    private String directory;
    private final MessageGenerator messageGenerator;

    public ImageService(@Value("${file.directory}") String directory, MessageGenerator messageGenerator) {
        this.directory = directory;
        this.messageGenerator = messageGenerator;
    }

    public UploadResponse uploadImage(MultipartFile multipartFile, String dir) {
//        if (!isValidImage(multipartFile, MAX_WIDTH, MAX_HEIGHT, ALLOWED_PROPORTION)) {
//            throw new SystemException(messageGenerator.get(AN_ERROR_OCCURED_FAILED_TO_SAVE_THE_IMAGE));
//        }
        ImageValidator imageValidator = new ImageValidator(multipartFile, messageGenerator);
        imageValidator.valid();
        String fileName = multipartFile.getOriginalFilename();
        String uploadDir = directory + dir + "/";
        Path filePath = Paths.get(uploadDir).resolve(fileName);

        try (InputStream stream = multipartFile.getInputStream()) {
            OutputStream outputStream = Files.newOutputStream(filePath);
            stream.transferTo(outputStream);
            return new UploadResponse(fileName);
        } catch (IOException e) {
            throw new SystemException(messageGenerator.get(AN_ERROR_OCCURED_FAILED_TO_SAVE_THE_IMAGE));
        }
    }

    public ResponseEntity<Resource> serveImages(String filename, String dir) throws IOException {
        String uploadDir = "./data/" + dir + "/";
        FileSystemResourceLoader fileSystemResourceLoader = new FileSystemResourceLoader();
        Resource file = fileSystemResourceLoader.getResource(uploadDir + filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(Path.of(filename)))
                .body(file);
    }

    public static boolean isValidImage(MultipartFile file, int maxWidth, int maxHeight, double allowedProportion) {
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            int width = image.getWidth();
            int height = image.getHeight();

            return width <= maxWidth &&
                    height <= maxHeight &&
                    Math.abs((double) width / height - allowedProportion) < 0.1;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
