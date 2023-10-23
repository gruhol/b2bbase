package pl.thinkdata.b2bbase.company.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.company.dto.CategoryResponse;
import pl.thinkdata.b2bbase.company.model.Category;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.repository.Category2CompanyRepository;
import pl.thinkdata.b2bbase.company.service.CategoryService;
import pl.thinkdata.b2bbase.company.service.CompanyService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final Category2CompanyRepository category2CompanyRepository;
    List<Category> allCategory;
    private final CompanyService companyService;

    @GetMapping
    public List<CategoryResponse> getCategories(HttpServletRequest request) {

        this.allCategory = categoryService.getCategories();
        Company company = companyService.getCompany(request);
        List<Long> category2CompanyList = category2CompanyRepository.findAllByCompanyId(company.getId())
                .stream()
                .map(cat -> cat.getCategoryId())
                .collect(Collectors.toList());

        List<CategoryResponse> categoryResponses = categoryService.getCategories().stream()
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
                .children(createChildList(allCategory, cat.getId(), category2CompanyList, cat))
                .build();
    }


    private List<CategoryResponse> createChildList(List<Category> allCategory, Long id, List<Long> userCategory, Category parent) {
        if (allCategory.size() <= 0 && id == null) new ArrayList<CategoryResponse>();
        return allCategory.stream()
                .filter(cat -> cat.getParent() != null)
                .filter(cat -> cat.getParent().getId() == id)
                .map(cat -> CategoryResponse.builder()
                        .id(cat.getId())
                        .parent(CategoryResponse.builder()
                                .id(parent.getId())
                                .name(parent.getName())
                                .selected(userCategory.contains(parent.getId()))
                                .parent(mapToCategoryResponse(userCategory, parent.getParent()))
                                .build())
                        .name(cat.getName())
                        .selected(userCategory.contains(cat.getId()))
                        .build())
                .collect(Collectors.toList());
    }


}
