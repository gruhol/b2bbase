package pl.thinkdata.b2bbase.blog.mapper;

import pl.thinkdata.b2bbase.blog.dto.BlogResponse;
import pl.thinkdata.b2bbase.blog.model.Blog;

public class BlogMapper {

    public static BlogResponse mapToBlogResponse(Blog blog) {
        return BlogResponse.builder()
                .title(blog.getTitle())
                .category(blog.getCategory())
                .addDate(blog.getAddDate())
                .editDate(blog.getEditDate())
                .content(blog.getContent())
                .slug(blog.getSlug())
                .author(blog.getAuthor())
                .build();
    }
}
