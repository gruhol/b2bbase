package pl.thinkdata.b2bbase.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.blog.dto.BlogResponse;
import pl.thinkdata.b2bbase.blog.service.BlogService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogConstroller {

    private final BlogService blogService;

    @GetMapping("/{slug}")
    public BlogResponse getBlogPost(@PathVariable String slug) {
       return blogService.getBlogPost(slug);
    }

    @GetMapping
    public Page<BlogResponse> getBlogPosts(Pageable pageable) {
        return blogService.getBlogPosts(pageable);
    }
}
