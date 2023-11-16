package pl.thinkdata.b2bbase.catalog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.catalog.dto.CategoryToCatalog;
import pl.thinkdata.b2bbase.common.repository.CategoryRepository;
import pl.thinkdata.b2bbase.company.dto.CategoryResponse;
import pl.thinkdata.b2bbase.company.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogCategoryService {

    private List<Category> allCategory;

    private final CategoryRepository categoryRepository;

    public List<CategoryToCatalog> getCategory() {
        this.allCategory = categoryRepository.findAll();

        List<CategoryToCatalog> categoryResponses = this.allCategory.stream()
                .filter(cat -> cat.getParent() == null)
                .map(cat -> mapToCategoryToCatalog(cat))
                .collect(Collectors.toList());

        return categoryResponses;
    }

    private CategoryToCatalog mapToCategoryToCatalog(Category cat) {
        if (cat == null) return null;
        return CategoryToCatalog.builder()
                .name(cat.getName())
                .slug(cat.getSlug())
                .children(createChildList(allCategory, cat))
                .build();
    }

    private List<CategoryToCatalog> createChildList(List<Category> allCategory, Category parent) {
        if (allCategory.size() == 0 && parent.getId() == null) new ArrayList<CategoryToCatalog>();
        return allCategory.stream()
                .filter(cat -> cat.getParent() != null)
                .filter(cat -> cat.getParent().getId() == parent.getId())
                .map(cat -> mapToCategoryResponse(parent, cat))
                .collect(Collectors.toList());
    }

    private CategoryToCatalog mapToCategoryResponse(Category parentCategory, Category category) {
        return CategoryToCatalog.builder()
                .name(category.getName())
                .slug(category.getSlug())
                .build();
    }
}
