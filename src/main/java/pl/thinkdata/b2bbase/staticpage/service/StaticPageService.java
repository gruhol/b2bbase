package pl.thinkdata.b2bbase.staticpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.staticpage.model.StaticPage;
import pl.thinkdata.b2bbase.staticpage.repository.StaticPageRepository;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.PAGE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StaticPageService {

    private final StaticPageRepository staticPageRepository;
    private final MessageGenerator messageGenerator;

    public StaticPage getStaticPageBySlug(String slug) {
        return staticPageRepository.getBySlug(slug)
                .orElseThrow(() -> new InvalidRequestDataException(messageGenerator.get(PAGE_NOT_FOUND)));
    }
}
