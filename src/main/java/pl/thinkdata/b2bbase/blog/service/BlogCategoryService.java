package pl.thinkdata.b2bbase.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.blog.model.BlogCategory;
import pl.thinkdata.b2bbase.blog.repository.BlogCategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogCategoryService {

    private final BlogCategoryRepository blogCategoryRepository;

    public List<BlogCategory> getBlogCategories() {
        return blogCategoryRepository.findAll();
    }
}
