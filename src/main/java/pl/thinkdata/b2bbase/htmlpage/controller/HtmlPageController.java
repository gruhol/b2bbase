package pl.thinkdata.b2bbase.htmlpage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.htmlpage.dto.HtmlPageResponse;
import pl.thinkdata.b2bbase.htmlpage.service.HtmlPageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/htmlpage")
public class HtmlPageController {

    private final HtmlPageService service;

    @GetMapping("/{slug}")
    public HtmlPageResponse getBlogPost(@PathVariable String slug) {
        return service.getHtmlPage(slug);
    }
}
