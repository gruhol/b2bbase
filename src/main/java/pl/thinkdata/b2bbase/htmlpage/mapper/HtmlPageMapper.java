package pl.thinkdata.b2bbase.htmlpage.mapper;

import pl.thinkdata.b2bbase.htmlpage.dto.HtmlPageResponse;
import pl.thinkdata.b2bbase.htmlpage.model.HtmlPage;

public class HtmlPageMapper {

    public static HtmlPageResponse mapToBlogResponse(HtmlPage blog) {
        return HtmlPageResponse.builder()
                .title(blog.getTitle())
                .content(blog.getContent())
                .slug(blog.getSlug())
                .build();
    }
}
