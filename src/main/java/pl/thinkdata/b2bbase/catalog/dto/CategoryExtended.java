package pl.thinkdata.b2bbase.catalog.dto;

import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CategoryExtended {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String shortDescription;
    private String h1;
    private String title;
}
