package pl.thinkdata.b2bbase.sitemap.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class BaseUrlGenerator {

    private final HttpServletRequest request;

    public String getUrl() {
        return ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
    }

    public String getTitleUrl() {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        String nameSite = baseUrl.substring(7);
        return " - " + nameSite.replace('/', ' ');
    }
}
