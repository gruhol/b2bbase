package pl.thinkdata.b2bbase.company.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private List<CategoryResponse> children;
    private Boolean selected;
}
