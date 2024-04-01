package pl.thinkdata.b2bbase.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.blog.dto.BlogResponse;
import pl.thinkdata.b2bbase.blog.mapper.BlogMapper;
import pl.thinkdata.b2bbase.blog.model.Blog;
import pl.thinkdata.b2bbase.blog.repository.BlogRepository;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.POST_BLOG_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final MessageGenerator messageGenerator;
    public BlogResponse getBlogPost(String slug) {
        Blog blog = blogRepository.findBySlug(slug)
                .orElseThrow(() -> new InvalidRequestDataException(messageGenerator.get(POST_BLOG_NOT_FOUND)));
        return BlogMapper.mapToBlogResponse(blog);
    }
}
