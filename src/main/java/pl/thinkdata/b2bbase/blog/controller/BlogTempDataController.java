package pl.thinkdata.b2bbase.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.blog.model.Blog;
import pl.thinkdata.b2bbase.blog.model.BlogCategory;
import pl.thinkdata.b2bbase.blog.repository.BlogCategoryRepository;
import pl.thinkdata.b2bbase.blog.repository.BlogRepository;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import java.util.Arrays;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@Profile("dev")
public class BlogTempDataController {

    private final UserRepository userRepository;
    private final BlogCategoryRepository blogCategoryRepository;
    private final BlogRepository blogRepository;

    @GetMapping("/blogtestdata")
    public String createBlogTempData() {

        User user = userRepository.findById(2L).get();

        BlogCategory blogCategory = BlogCategory.builder()
                .name("Categoria Testowa")
                .description("Opis kategorii testowej")
                .slug("categoria-testowa")
                .build();

        BlogCategory blogCategory2 = BlogCategory.builder()
                .name("Categoria Testowa 2")
                .description("Opis kategorii testowej 2")
                .slug("categoria-testowa2")
                .build();

        BlogCategory saveBlogCategory = blogCategoryRepository.save(blogCategory);
        BlogCategory saveBlogCategory2 = blogCategoryRepository.save(blogCategory2);

        Blog blog1 = Blog.builder()
                .title("Wpis testowy nr1")
                .slug("wpis-testowy-nr1")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
                        "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
                        "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                .addDate(new Date())
                .editDate(new Date())
                .author(user)
                .category(saveBlogCategory)
                .build();

        Blog blog2 = Blog.builder()
                .title("Wpis testowy nr2")
                .slug("wpis-testowy-nr2")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
                        "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
                        "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                .addDate(new Date())
                .editDate(new Date())
                .author(user)
                .category(saveBlogCategory2)
                .build();

        Blog blog3 = Blog.builder()
                .title("Wpis testowy nr3")
                .slug("wpis-testowy-nr3")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
                        "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
                        "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                .addDate(new Date())
                .editDate(new Date())
                .author(user)
                .category(saveBlogCategory)
                .build();

        Blog blog4 = Blog.builder()
                .title("Wpis testowy nr4")
                .slug("wpis-testowy-nr4")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
                        "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
                        "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                .addDate(new Date())
                .editDate(new Date())
                .author(user)
                .category(saveBlogCategory)
                .build();

        Blog blog5 = Blog.builder()
                .title("Wpis testowy nr5")
                .slug("wpis-testowy-nr5")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
                        "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
                        "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                .addDate(new Date())
                .editDate(new Date())
                .author(user)
                .category(saveBlogCategory2)
                .build();

        blogRepository.saveAll(Arrays.asList(blog1, blog2, blog3,blog4,blog5));

        return "Dane przygotowane";
    }
}
