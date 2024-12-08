package pl.thinkdata.b2bbase.sitemap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.sitemap.service.SiteMapService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class SiteMapController {

    private final SiteMapService siteMapService;

    @GetMapping(value = "/sitemap.xml", produces = "application/xml")
    public ResponseEntity<String> getSitemap() throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/xml");

        return new ResponseEntity<>(siteMapService.getSiteMap(), headers, HttpStatus.OK);
    }

}
