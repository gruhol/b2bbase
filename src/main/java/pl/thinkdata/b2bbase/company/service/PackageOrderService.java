package pl.thinkdata.b2bbase.company.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.company.dto.PackageOrderToCatalog;
import pl.thinkdata.b2bbase.company.mapper.BranchMapper;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.repository.PackageOrderRepository;

import java.util.List;
import java.util.stream.Collectors;

import static pl.thinkdata.b2bbase.company.mapper.PackageOrderMapper.mapToPackageOrderToCatalog;

@Service
@RequiredArgsConstructor
public class PackageOrderService {

    private final CompanyService companyService;
    private final MessageGenerator messageGenerator;
    private final PackageOrderRepository packageOrderRepository;
    public List<PackageOrderToCatalog> getCompanyOrders(HttpServletRequest request) {
        Company company = companyService.getCompany(request);
        return packageOrderRepository.findAllByCompanyId(company.getId()).stream()
                .map(order -> mapToPackageOrderToCatalog(order))
                .collect(Collectors.toList());
    }
}
