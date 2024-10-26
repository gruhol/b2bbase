package pl.thinkdata.b2bbase.catalog.mapper;

import pl.thinkdata.b2bbase.catalog.dto.CategoryExtended;
import pl.thinkdata.b2bbase.company.model.Category;

public class CategoryMapper {

    public static CategoryExtended map(Category category) {
        return CategoryExtended.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .shortDescription(category.getShortDescription())
                .h1(category.getH1())
                .title(category.getTitle())
                .build();
    }
}
