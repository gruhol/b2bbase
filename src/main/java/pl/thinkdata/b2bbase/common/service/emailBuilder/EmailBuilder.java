package pl.thinkdata.b2bbase.common.service.emailBuilder;

import java.util.Map;

public interface EmailBuilder {

    String prepareEmail(String template, Map<String, String> values);
}
