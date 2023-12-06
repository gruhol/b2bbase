package pl.thinkdata.b2bbase.catalog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.catalog.dto.SocialToCatalog;
import pl.thinkdata.b2bbase.common.repository.SocialRepository;

import java.util.List;
import java.util.stream.Collectors;

import static pl.thinkdata.b2bbase.catalog.mapper.SocialMapper.mapToSocialToCatalog;

@Service
@RequiredArgsConstructor
public class CatalogSocialService {

    private final SocialRepository socialRepository;

    public List<SocialToCatalog> getSocialByCompanyId(Long id) {
        return socialRepository.findAllByCompanyId(id).stream()
                .map(social -> mapToSocialToCatalog(social))
                .collect(Collectors.toList());
    }
}
