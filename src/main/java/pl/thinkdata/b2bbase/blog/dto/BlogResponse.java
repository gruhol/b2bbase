package pl.thinkdata.b2bbase.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.thinkdata.b2bbase.blog.model.BlogCategory;
import pl.thinkdata.b2bbase.security.model.User;

import java.util.Date;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BlogResponse {
    private String title;
    private BlogCategory category;
    private Date addDate;
    private Date editDate;
    private String content;
    private String slug;
    private UserResponse author;
}
