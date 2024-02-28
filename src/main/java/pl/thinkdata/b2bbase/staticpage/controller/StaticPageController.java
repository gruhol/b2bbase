package pl.thinkdata.b2bbase.staticpage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.staticpage.model.StaticPage;
import pl.thinkdata.b2bbase.staticpage.service.StaticPageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/page")
public class StaticPageController {

    private final StaticPageService staticPageService;

    @GetMapping("/{slug}")
    public StaticPage getPageBySlug(@PathVariable String slug) {
        return staticPageService.getStaticPageBySlug(slug);
    }
}
