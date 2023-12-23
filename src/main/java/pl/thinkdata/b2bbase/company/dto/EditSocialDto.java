package pl.thinkdata.b2bbase.company.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import pl.thinkdata.b2bbase.company.model.enums.SocialTypeEnum;

@Getter
@Setter
public class EditSocialDto {
    @NotNull
    private Long id;
    @NotNull
    private SocialTypeEnum type;
    @URL
    private String url;
}
