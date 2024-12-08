package pl.thinkdata.b2bbase.sitemap.service;

import cz.jiripinkas.jsitemapgenerator.ChangeFreq;
import cz.jiripinkas.jsitemapgenerator.WebPage;
import cz.jiripinkas.jsitemapgenerator.generator.SitemapGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.blog.repository.BlogCategoryRepository;
import pl.thinkdata.b2bbase.blog.repository.BlogRepository;
import pl.thinkdata.b2bbase.common.repository.CategoryRepository;
import pl.thinkdata.b2bbase.common.repository.CompanyRepository;
import pl.thinkdata.b2bbase.common.service.baseUrlService.BaseUrlService;
import pl.thinkdata.b2bbase.staticpage.repository.StaticPageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteMapService {

    private final BaseUrlService baseUrlService;
    private final BlogRepository blogRepository;
    private final BlogCategoryRepository blogCategoryRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;
    private final StaticPageRepository staticPageRepository;

    public String getSiteMap() {
        SitemapGenerator siteMap = SitemapGenerator.of(baseUrlService.getUrl());
        siteMap.addPage(WebPage.builder().nameRoot().priorityMax().changeFreqDaily().build());

        siteMap.addPage(createPage("/blog", 0.9, ChangeFreq.DAILY));
        siteMap.addPages(getBlogPostUrlsToSiteMap());
        siteMap.addPages(getBlogCategoriesUrlsToSiteMap());
        createPage("/catalog", 0.9, ChangeFreq.DAILY);
        siteMap.addPages(getCompanyUrlsToSiteMap());
        siteMap.addPages(getCompanyCategoryUrlsToSiteMap());
        siteMap.addPages(getStaticPagesUrlsToSiteMap());

        return siteMap.toString();
    }

    private WebPage createPage(String url, Double priority, ChangeFreq freq) {
        return WebPage.builder()
                .name(url)
                .priority(priority)
                .changeFreq(freq)
                .build();
    }

    private List<WebPage> getBlogPostUrlsToSiteMap() {
        return blogRepository.findAll().stream()
            .map(post -> WebPage.builder()
                    .name("/blog/" + post.getSlug())
                    .changeFreq(ChangeFreq.DAILY)
                    .priority(0.7)
                    .build())
                .toList();
    }

    private List<WebPage> getBlogCategoriesUrlsToSiteMap() {
        return blogCategoryRepository.findAll().stream()
                .map(categoryBlog -> WebPage.builder()
                        .name("blog/category/" + categoryBlog.getSlug())
                        .changeFreq(ChangeFreq.DAILY)
                        .priority(0.8)
                        .build())
                .toList();
    }

    private List<WebPage> getCompanyUrlsToSiteMap() {
        return companyRepository.findAll().stream()
                .map(company -> WebPage.builder()
                        .name("company/" + company.getSlug())
                        .changeFreq(ChangeFreq.DAILY)
                        .priority(0.7)
                        .build())
                .toList();
    }

    private List<WebPage> getCompanyCategoryUrlsToSiteMap() {
        return categoryRepository.findAll().stream()
                .map(category -> WebPage.builder()
                        .name("category/" + category.getSlug())
                        .changeFreq(ChangeFreq.DAILY)
                        .priority(0.8)
                        .build())
                .toList();
    }

    private List<WebPage> getStaticPagesUrlsToSiteMap() {
        return staticPageRepository.findAll().stream()
                .map(page -> WebPage.builder()
                        .name("page/" + page.getSlug())
                        .changeFreq(ChangeFreq.MONTHLY)
                        .priority(0.6)
                        .build())
                .toList();
    }
}
