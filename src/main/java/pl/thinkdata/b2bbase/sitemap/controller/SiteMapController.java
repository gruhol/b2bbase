package pl.thinkdata.b2bbase.sitemap.controller;

import cz.jiripinkas.jsitemapgenerator.WebPage;
import cz.jiripinkas.jsitemapgenerator.generator.SitemapGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.common.service.baseUrlService.BaseUrlService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class SiteMapController {

    private final BaseUrlService baseUrlService;

    @GetMapping(value = "/sitemap.xml", produces = "application/xml")
    public ResponseEntity<String> getSitemap() throws IOException {
        String sitemapContent = SitemapGenerator.of(baseUrlService.getUrl())
                .addPage(WebPage.builder().maxPriorityRoot().build())
                .addPage("/catalog")
                .addPage("/blog")
                .toString();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/xml");

        return new ResponseEntity<>(sitemapContent, headers, HttpStatus.OK);
    }

}
