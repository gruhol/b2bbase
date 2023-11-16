package pl.thinkdata.b2bbase.catalog.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CategoryToCatalog {

    private String name;
    private String slug;
    private List<CategoryToCatalog> children;

}
