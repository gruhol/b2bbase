package pl.thinkdata.b2bbase.company.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import pl.thinkdata.b2bbase.company.model.SocialType;

@Getter
@Setter
public class SocialDto {
    @NotNull
    private SocialType type;
    @URL
    private String url;
}
