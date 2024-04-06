package pl.thinkdata.b2bbase.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.blog.dto.BlogResponse;
import pl.thinkdata.b2bbase.blog.mapper.BlogMapper;
import pl.thinkdata.b2bbase.blog.model.Blog;
import pl.thinkdata.b2bbase.blog.repository.BlogRepository;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;

import java.util.List;
import java.util.stream.Collectors;

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

    public Page<BlogResponse> getBlogPosts(List<Long> categories, Pageable pageable) {
        Page<Blog> blogPosts = blogRepository.findAllByCategory(categories, pageable);

        List<BlogResponse> posts = blogPosts.stream()
                .map(BlogMapper::mapToBlogResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(posts, pageable, blogPosts.getTotalElements());
    }
}
