package pl.thinkdata.b2bbase.common.service.emailBuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.thinkdata.b2bbase.common.service.baseUrlService.BaseUrlService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailBuilderImpl implements EmailBuilder{

    private final BaseUrlService baseUrlService;
    private final TemplateEngine templateEngine;
    private static final String BASEURL = "baseurl";

    @Override
    public String prepareEmail(String template, Map<String, String> values) {
        String baseUrl = baseUrlService.getUrl();
        Context context = new Context();
        context.setVariable(BASEURL, baseUrl);
        for (Map.Entry value : values.entrySet()) {
            context.setVariable(value.getKey().toString(), value.getValue());
        }
        return templateEngine.process(template, context);
    }
}
