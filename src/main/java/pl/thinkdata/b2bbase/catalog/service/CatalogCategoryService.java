package pl.thinkdata.b2bbase.catalog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.catalog.dto.CategoryToCatalog;
import pl.thinkdata.b2bbase.common.repository.CategoryRepository;
import pl.thinkdata.b2bbase.company.model.Category;

import java.util.Arrays;
import java.util.List;

import static pl.thinkdata.b2bbase.catalog.mapper.CatalogMapper.mapToCategoryToCatalog;

@Service
@RequiredArgsConstructor
public class CatalogCategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryToCatalog> getCategory(String slug) {
        List<Category> categories = categoryRepository.findAll();
        Category categorySlug = categories.stream()
                    .filter(cat -> cat.getSlug().equals(slug))
                    .findFirst()
                    .orElse(null);

        List<CategoryToCatalog> categoryResponses;

        if (categorySlug == null) {

            return categoryResponses = categories.stream()
                    .filter(cat -> cat.getParent() == null)
                    .map(cat -> mapToCategoryToCatalog(cat, categories))
                    .toList();
        } else {

            return Arrays.asList(mapToCategoryToCatalog(categorySlug, categories));
        }
    }


}
