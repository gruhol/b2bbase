package pl.thinkdata.b2bbase.catalog.mapper;

import pl.thinkdata.b2bbase.catalog.dto.SocialToCatalog;
import pl.thinkdata.b2bbase.company.model.Social;

public class SocialMapper {

    public static SocialToCatalog mapToSocialToCatalog(Social social) {
        return SocialToCatalog.builder()
                .type(social.getType())
                .url(social.getUrl())
                .build();
    }
}
