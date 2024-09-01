package pl.thinkdata.b2bbase.catalog.dto;

import lombok.*;

import java.util.List;

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
}
