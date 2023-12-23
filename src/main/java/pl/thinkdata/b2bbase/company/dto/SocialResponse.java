package pl.thinkdata.b2bbase.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.thinkdata.b2bbase.company.model.enums.SocialTypeEnum;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialResponse {
    private Long id;
    private SocialTypeEnum type;
    private String url;
}
