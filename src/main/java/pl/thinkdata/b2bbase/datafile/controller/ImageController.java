package pl.thinkdata.b2bbase.datafile.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.thinkdata.b2bbase.company.dto.UploadResponse;
import pl.thinkdata.b2bbase.datafile.service.ImageService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/img")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload/{dir}")
    public UploadResponse uploadImages(@RequestParam("file") MultipartFile multipartFile, @PathVariable String dir, HttpServletRequest request) {
        return imageService.uploadImage(multipartFile, dir, request);
    }

    @GetMapping("/get/{dir}/{filename}")
    public ResponseEntity<Resource> serveImages(@PathVariable String filename, @PathVariable String dir) {
        return imageService.serveImages(filename, dir);
    }
}
