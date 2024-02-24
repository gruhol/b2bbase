package pl.thinkdata.b2bbase.datafile.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.thinkdata.b2bbase.common.error.SystemException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.company.dto.UploadResponse;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.service.CompanyService;
import pl.thinkdata.b2bbase.datafile.util.TinyPngConverter;
import pl.thinkdata.b2bbase.datafile.validator.ImageValidator;

import java.io.File;
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
    private final CompanyService companyService;

    public ImageService(@Value("${file.directory}") String directory,
                        MessageGenerator messageGenerator,
                        TinyPngConverter tinyPngConverter,
                        CompanyService companyService) {
        this.directory = directory;
        this.messageGenerator = messageGenerator;
        this.tinyPngConverter = tinyPngConverter;
        this.companyService = companyService;
    }

    public UploadResponse uploadImage(MultipartFile multipartFile, String dir,  HttpServletRequest request) {
        ImageValidator imageValidator = new ImageValidator(multipartFile, messageGenerator);
        imageValidator.valid(1000, 500, 2);

        String fileName = multipartFile.getOriginalFilename();
        String uploadDir = directory + dir + File.separator + "temp" + File.separator;
        Path filePath = Paths.get(uploadDir, fileName);
        String fileNameToSave = generateFileLogoName(request, fileName);

        try (InputStream stream = multipartFile.getInputStream()) {
            OutputStream outputStream = Files.newOutputStream(filePath);
            stream.transferTo(outputStream);
            //tinyPngConverter.convertImage(fileName, fileNameToSave, dir);
            Files.deleteIfExists(filePath);
            return new UploadResponse(fileNameToSave);
        } catch (IOException e) {
            throw new SystemException(messageGenerator.get(AN_ERROR_OCCURED_FAILED_TO_SAVE_THE_IMAGE));
        }
    }

    public ResponseEntity<Resource> serveImages(String filename, String dir) {
        try {
            String uploadDir = directory + dir + File.separator;
            FileSystemResourceLoader fileSystemResourceLoader = new FileSystemResourceLoader();
            Resource file = fileSystemResourceLoader.getResource(uploadDir + filename);
            if (file.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(Path.of(filename)))
                        .body(file);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "Not found image");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .headers(headers)
                    .build();
        } catch (IOException e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "Not found image");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .headers(headers)
                    .build();
        }
    }

    private String generateFileLogoName(HttpServletRequest request, String fileName) {
        Company company = companyService.getCompany(request);
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        return company.getSlug() + "-logo" + fileExtension;
    }
}
