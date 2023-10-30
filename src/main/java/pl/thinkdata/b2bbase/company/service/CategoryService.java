package pl.thinkdata.b2bbase.company.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.company.dto.CategoryResponse;
import pl.thinkdata.b2bbase.company.model.Category;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.repository.Category2CompanyRepository;
import pl.thinkdata.b2bbase.company.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Category2CompanyRepository category2CompanyRepository;
    private final CompanyService companyService;

    List<Category> allCategory;

    public List<CategoryResponse> getCategories(HttpServletRequest request) {

        this.allCategory = categoryRepository.findAll();
        Company company = companyService.getCompany(request);
        List<Long> category2CompanyList = category2CompanyRepository.findAllByCompanyId(company.getId())
                .stream()
                .map(cat -> cat.getCategoryId())
                .collect(Collectors.toList());

        List<CategoryResponse> categoryResponses = this.allCategory.stream()
                .filter(cat -> cat.getParent() == null)
                .map(cat -> mapToCategoryResponse(category2CompanyList, cat))
                .collect(Collectors.toList());

        return categoryResponses;
    }

    private CategoryResponse mapToCategoryResponse(List<Long> category2CompanyList, Category cat) {
        if (cat == null) return null;
        return CategoryResponse.builder()
                .id(cat.getId())
                .name(cat.getName())
                .children(createChildList(allCategory, category2CompanyList, cat))
                .build();
    }

    private List<CategoryResponse> createChildList(List<Category> allCategory, List<Long> userCategory, Category parent) {
        if (allCategory.size() == 0 && parent.getId() == null) new ArrayList<CategoryResponse>();
        return allCategory.stream()
                .filter(cat -> cat.getParent() != null)
                .filter(cat -> cat.getParent().getId() == parent.getId())
                .map(cat -> mapToCategoryResponse(userCategory, parent, cat))
                .collect(Collectors.toList());
    }

    private CategoryResponse mapToCategoryResponse(List<Long> userCategory, Category parentCategory, Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .parent(CategoryResponse.builder()
                        .id(parentCategory.getId())
                        .name(parentCategory.getName())
                        .selected(userCategory.contains(parentCategory.getId()))
                        .parent(mapToCategoryResponse(userCategory, parentCategory.getParent()))
                        .build())
                .name(category.getName())
                .selected(userCategory.contains(category.getId()))
                .build();
    }
}
