package pl.thinkdata.b2bbase.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.thinkdata.b2bbase.company.model.enums.SocialTypeEnum;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SocialToCatalog {
    private SocialTypeEnum type;
    private String url;
}
