package pl.thinkdata.b2bbase.company.mapper;

import pl.thinkdata.b2bbase.company.dto.EditSocialDto;
import pl.thinkdata.b2bbase.company.dto.SocialDto;
import pl.thinkdata.b2bbase.company.model.Social;

public class SocialMapper {

    public static Social mapToSocial(SocialDto socialDto) {
        return Social.builder()
                .type(socialDto.getType())
                .url(socialDto.getUrl())
                .build();
    }

    public static Social mapToSocial(EditSocialDto editSocialDto) {
        return Social.builder()
                .id(editSocialDto.getId())
                .type(editSocialDto.getType())
                .url(editSocialDto.getUrl())
                .build();
    }

}
