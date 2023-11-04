package pl.thinkdata.b2bbase.datafile.service;

import lombok.RequiredArgsConstructor;
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

    public ImageService(@Value("${file.directory}") String directory, MessageGenerator messageGenerator) {
        this.directory = directory;
        this.messageGenerator = messageGenerator;
    }

    public UploadResponse uploadImage(MultipartFile multipartFile, String dir) {
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
}
