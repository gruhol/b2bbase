package pl.thinkdata.b2bbase.blog.mapper;

import pl.thinkdata.b2bbase.blog.dto.BlogResponse;
import pl.thinkdata.b2bbase.blog.dto.UserResponse;
import pl.thinkdata.b2bbase.blog.model.Blog;
import pl.thinkdata.b2bbase.security.model.User;

public class BlogMapper {

    public static BlogResponse mapToBlogResponse(Blog blog) {
        return BlogResponse.builder()
                .title(blog.getTitle())
                .category(blog.getCategory())
                .addDate(blog.getAddDate())
                .editDate(blog.getEditDate())
                .introduction(blog.getIntroduction())
                .content(blog.getContent())
                .slug(blog.getSlug())
                .author(mapToUserResponse(blog.getAuthor()))
                .build();
    }

    public static UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .phone(user.getPhone())
                .username(user.getUsername())
                .build();
    }
}
