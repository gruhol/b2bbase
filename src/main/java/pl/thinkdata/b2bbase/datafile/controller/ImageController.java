package pl.thinkdata.b2bbase.datafile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.thinkdata.b2bbase.company.dto.UploadResponse;
import pl.thinkdata.b2bbase.datafile.service.ImageService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/uploadLogo")
    public UploadResponse uploadLogoImages(@RequestParam("file") MultipartFile multipartFile, String dir) {
        return imageService.uploadImage(multipartFile, dir);
    }

    @GetMapping("/logoImages/{filename}")
    public ResponseEntity<Resource> serveLogoImages(@PathVariable String filename) throws IOException {
        return imageService.serveLogoImages(filename);
    }
}
