package pl.thinkdata.b2bbase.common.service.baseUrlService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("prod")
@Service
@RequiredArgsConstructor
public class BaseUrlServiceImpl implements BaseUrlService{

    @Value("${base.url}")
    private String baseUrl;

    @Override
    public String getUrl() {
        return baseUrl;
    }
}
