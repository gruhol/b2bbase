package pl.thinkdata.b2bbase.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.thinkdata.b2bbase.company.model.SocialType;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialResponse {
    private Long id;
    private SocialType type;
    private String url;
}
