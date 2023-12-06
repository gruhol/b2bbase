package pl.thinkdata.b2bbase.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.thinkdata.b2bbase.company.model.SocialType;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SocialToCatalog {
    private SocialType type;
    private String url;
}
