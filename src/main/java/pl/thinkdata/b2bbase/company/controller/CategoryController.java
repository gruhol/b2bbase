package pl.thinkdata.b2bbase.company.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.company.dto.CategoryResponse;
import pl.thinkdata.b2bbase.company.model.Category;
import pl.thinkdata.b2bbase.company.service.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    List<Category> allCategory;

    @GetMapping
    public List<CategoryResponse> getCategories() {

        this.allCategory = categoryService.getCategories();


        List<CategoryResponse> categoryResponses = categoryService.getCategories().stream()
                .filter(cat -> cat.getParentId() == null)
                .map(cat -> CategoryResponse.builder()
                        .id(cat.getId())
                        .name(cat.getName())
                        .children(createChildList(allCategory, cat.getId()))
                        .build())
                .collect(Collectors.toList());

        return categoryResponses;
    }

    private List<CategoryResponse> createChildList(List<Category> allCategory, Long id) {
        if (allCategory.size() <= 0 && id == null) new ArrayList<CategoryResponse>();
        return allCategory.stream()
                .filter(cat -> cat.getParentId() == id)
                .map(cat -> CategoryResponse.builder()
                        .id(cat.getId())
                        .name(cat.getName())
                        .build())
                .collect(Collectors.toList());
    }


}
