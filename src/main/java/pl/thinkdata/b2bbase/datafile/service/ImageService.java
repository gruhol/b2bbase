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
import pl.thinkdata.b2bbase.datafile.util.TinyPngConverter;
import pl.thinkdata.b2bbase.datafile.validator.ImageValidator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.AN_ERROR_OCCURED_FAILED_TO_SAVE_THE_IMAGE;

@Service
public class ImageService {

    private String directory;
    private final MessageGenerator messageGenerator;
    private final TinyPngConverter tinyPngConverter;

    public ImageService(@Value("${file.directory}") String directory, MessageGenerator messageGenerator, TinyPngConverter tinyPngConverter) {
        this.directory = directory;
        this.messageGenerator = messageGenerator;
        this.tinyPngConverter = tinyPngConverter;
    }

    public UploadResponse uploadImage(MultipartFile multipartFile, String dir) {
        ImageValidator imageValidator = new ImageValidator(multipartFile, messageGenerator);
        imageValidator.valid();

        String fileName = multipartFile.getOriginalFilename();
        String uploadDir = directory + dir + "/";
        Path filePath = Paths.get(uploadDir).resolve(fileName);

        try {
            tinyPngConverter.convertImage(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
}
