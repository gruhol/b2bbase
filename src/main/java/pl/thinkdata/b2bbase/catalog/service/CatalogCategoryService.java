package pl.thinkdata.b2bbase.catalog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.catalog.dto.CategoryToCatalog;
import pl.thinkdata.b2bbase.common.repository.CategoryRepository;
import pl.thinkdata.b2bbase.company.model.Category;

import java.util.List;
import java.util.stream.Collectors;

import static pl.thinkdata.b2bbase.catalog.mapper.CatalogMapper.mapToCategoryToCatalog;

@Service
@RequiredArgsConstructor
public class CatalogCategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryToCatalog> getCategory() {
        List<Category> categories = categoryRepository.findAll();

        List<CategoryToCatalog> categoryResponses = categories.stream()
                .filter(cat -> cat.getParent() == null)
                .map(cat -> mapToCategoryToCatalog(cat, true, categories))
                .collect(Collectors.toList());

        return categoryResponses;
    }


}
