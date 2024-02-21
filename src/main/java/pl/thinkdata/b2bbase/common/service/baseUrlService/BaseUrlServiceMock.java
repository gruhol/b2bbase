package pl.thinkdata.b2bbase.common.service.baseUrlService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Profile("dev")
@Service
@RequiredArgsConstructor
public class BaseUrlServiceMock implements BaseUrlService{

    private final HttpServletRequest request;

    @Override
    public String getUrl() {
        return ServletUriComponentsBuilder.fromRequestUri(this.request)
                .replacePath(null)
                .build()
                .toUriString();
    }
}
