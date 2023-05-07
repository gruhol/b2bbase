package pl.thinkdata.b2bbase.company.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.thinkdata.b2bbase.company.service.CompanyService;

@Component
public class ServiceUtils {
    private static ServiceUtils instance;

    @Autowired
    private CompanyService companyService;

    @PostConstruct
    public void fillInstance() {
        instance = this;
    }

    public static CompanyService getCompanyService() {
        return instance.companyService;
    }
}