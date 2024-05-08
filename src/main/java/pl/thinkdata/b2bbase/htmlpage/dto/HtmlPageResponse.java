package pl.thinkdata.b2bbase.htmlpage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class HtmlPageResponse {
    private String title;
    private String content;
    private String slug;
}
