package pl.thinkdata.b2bbase.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile("prod")
@Configuration
public class ResourcesConfigurationForProdProfile implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Setting for docker
        registry.addResourceHandler("/data/**").addResourceLocations("file:/data/");
    }

}
