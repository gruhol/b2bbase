package pl.thinkdata.b2bbase.company.service;

import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import pl.thinkdata.b2bbase.company.dto.CategoryResponse;
import pl.thinkdata.b2bbase.company.model.Category;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.common.repository.CategoryRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CompanyService companyService;
    @Inject
    private CategoryService categoryService;

    @BeforeEach
    void init() {
        this.categoryService = new CategoryService(this.categoryRepository, this.companyService);
    }

    @Test
    void shouldReturnCompanyCategorySelected() {
        //when
        when(categoryRepository.findAll()).thenReturn(getAllCategory());
        when(companyService.getCompany(any())).thenReturn(getCompanyCategory());
        //then
        List<CategoryResponse> response = categoryService.getCategories(new MockHttpServletRequest());
        long countSelected = response.stream()
                .flatMap(cat -> cat.getChildren().stream())
                .map(catSelected -> catSelected.getSelected() == true)
                .filter(istrue -> istrue == true).count();
        assertEquals(2, response.size());
        assertEquals(2, countSelected);
    }

    private List<Category> getAllCategory() {
        Category category1 = Category.builder()
                .id(1L)
                .name("Category1")
                .slug("category1")
                .build();
        Category category2 = Category.builder()
                .id(2L)
                .name("Category2")
                .parent(category1)
                .slug("category2")
                .build();
        Category category3 = Category.builder()
                .id(3L)
                .name("Category3")
                .parent(category1)
                .slug("category3")
                .build();
        Category category4 = Category.builder()
                .id(4L)
                .name("Category4")
                .slug("category4")
                .build();
        Category category5 = Category.builder()
                .id(5L)
                .name("Category5")
                .parent(category4)
                .slug("category5")
                .build();
        return Arrays.asList(category1, category2, category3, category4, category5);
    }

    private Company getCompanyCategory() {
        Category category1 = Category.builder()
                .id(1L)
                .name("Category1")
                .slug("category1")
                .build();
        Category category2 = Category.builder()
                .id(2L)
                .name("Category2")
                .parent(category1)
                .slug("category2")
                .build();
        Category category3 = Category.builder()
                .id(3L)
                .name("Category3")
                .parent(category1)
                .slug("category3")
                .build();
        Category category4 = Category.builder()
                .id(4L)
                .name("Category4")
                .slug("category4")
                .build();
        Category category5 = Category.builder()
                .id(5L)
                .name("Category5")
                .parent(category4)
                .slug("category5")
                .build();
        Set<Category> setCategory = new HashSet<>();
        setCategory.add(category5);
        setCategory.add(category2);
        Company company = Company.builder()
                .categories(setCategory)
                .build();
        return company;
    }
}