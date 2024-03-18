package pl.thinkdata.b2bbase.sitemap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.common.service.baseUrlService.BaseUrlService;
import pl.thinkdata.b2bbase.sitemap.component.XmlUrl;
import pl.thinkdata.b2bbase.sitemap.component.XmlUrlSet;

@RestController
@RequiredArgsConstructor
public class SiteMapController {

    private final BaseUrlService baseUrlService;

    @RequestMapping(value = "/sitemap.xml", method = RequestMethod.GET, produces = "text/xml; charset=utf-8")
    @ResponseBody
    public XmlUrlSet getSiteMap() {
        XmlUrlSet xmlUrlSet = new XmlUrlSet();
        create(xmlUrlSet, "/", XmlUrl.Priority.TOP);
        create(xmlUrlSet, "/page/regulamin-strony", XmlUrl.Priority.TOP);
        create(xmlUrlSet, "/page/polityka-prywatnosci", XmlUrl.Priority.TOP);
        create(xmlUrlSet, "/catalog", XmlUrl.Priority.TOP);
        create(xmlUrlSet, "/login", XmlUrl.Priority.TOP);
        create(xmlUrlSet, "/registration", XmlUrl.Priority.TOP);
        create(xmlUrlSet, "/remember-password", XmlUrl.Priority.TOP);

        return xmlUrlSet;
    }

    private void create(XmlUrlSet xmlUrlSet, String link, XmlUrl.Priority priority) {
        String DOMAIN = baseUrlService.getUrl();
        xmlUrlSet.addUrl(new XmlUrl(DOMAIN + link, priority));
    }
}
