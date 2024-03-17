package pl.thinkdata.b2bbase.sitemap.controller;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.sitemap.component.BaseUrlGenerator;
import pl.thinkdata.b2bbase.sitemap.component.XmlUrl;
import pl.thinkdata.b2bbase.sitemap.component.XmlUrlSet;

@RestController
@RequiredArgsConstructor
public class SiteMapController {

    private final BaseUrlGenerator baseUrlGenerator;

    @RequestMapping(value = "/sitemap.xml", method = RequestMethod.GET, produces = "text/xml; charset=utf-8")
    @ResponseBody
    public XmlUrlSet getSiteMap() {
        XmlUrlSet xmlUrlSet = new XmlUrlSet();
        create(xmlUrlSet, "/", XmlUrl.Priority.TOP);
        create(xmlUrlSet, "/brands/a", XmlUrl.Priority.TOP);
        create(xmlUrlSet, "/notes", XmlUrl.Priority.TOP);
        create(xmlUrlSet, "/authors/a", XmlUrl.Priority.TOP);

        return xmlUrlSet;
    }

    private void create(@NotNull XmlUrlSet xmlUrlSet, String link, XmlUrl.Priority priority) {
        String DOMAIN = baseUrlGenerator.getUrl();
        xmlUrlSet.addUrl(new XmlUrl(DOMAIN + link, priority));
    }
}
