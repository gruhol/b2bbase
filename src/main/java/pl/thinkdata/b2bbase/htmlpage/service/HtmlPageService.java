package pl.thinkdata.b2bbase.htmlpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.htmlpage.dto.HtmlPageResponse;
import pl.thinkdata.b2bbase.htmlpage.mapper.HtmlPageMapper;
import pl.thinkdata.b2bbase.htmlpage.model.HtmlPage;
import pl.thinkdata.b2bbase.htmlpage.repository.HtmlPageRepository;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.PAGE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class HtmlPageService {

    private final HtmlPageRepository htmlPageRepository;
    private final MessageGenerator messageGenerator;

    public HtmlPageResponse getHtmlPage(String slug) {
        HtmlPage htmlPage = htmlPageRepository.findBySlug(slug)
                .orElseThrow(() -> new InvalidRequestDataException(messageGenerator.get(PAGE_NOT_FOUND)));
        return HtmlPageMapper.mapToBlogResponse(htmlPage);
    }

}
