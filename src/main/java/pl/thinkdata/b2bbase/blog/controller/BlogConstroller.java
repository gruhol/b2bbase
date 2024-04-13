package pl.thinkdata.b2bbase.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.thinkdata.b2bbase.blog.dto.BlogResponse;
import pl.thinkdata.b2bbase.blog.model.BlogCategory;
import pl.thinkdata.b2bbase.blog.service.BlogCategoryService;
import pl.thinkdata.b2bbase.blog.service.BlogService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogConstroller {

    private final BlogService blogService;
    private final BlogCategoryService blogCategoryService;

    @GetMapping("/{slug}")
    public BlogResponse getBlogPost(@PathVariable String slug) {
       return blogService.getBlogPost(slug);
    }

    @GetMapping
    public Page<BlogResponse> getBlogPosts(@RequestParam(required = false) List<Long> categories, Pageable pageable) {
        return blogService.getBlogPosts(categories, pageable);
    }

    @GetMapping("/category/{slug}")
    public Page<BlogResponse> getBlogPostsByCategoryName(@PathVariable String slug, Pageable pageable) {
        return blogService.getBlogPostsByCategoryName(slug, pageable);
    }

    @GetMapping("/categories")
    public List<BlogCategory> getBlogCategory() {
        return blogCategoryService.getBlogCategories();
    }
}
