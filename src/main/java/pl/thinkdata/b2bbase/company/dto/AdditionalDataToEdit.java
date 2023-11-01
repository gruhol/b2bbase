package pl.thinkdata.b2bbase.company.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdditionalDataToEdit {
    private String description;
    private List<Long> categories;
    private String logo;
}
