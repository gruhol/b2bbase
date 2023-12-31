package pl.thinkdata.b2bbase.search.constroller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.search.dto.SearchCompanyResult;
import pl.thinkdata.b2bbase.search.service.SearchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/{keyword}")
    public SearchCompanyResult searchCompany(@PathVariable(required = true) String keyword, Pageable pageable) {
        return searchService.searchCompanyWithCategoryAndVoivodeshipByKeyword("%" + keyword + "%", pageable);
    }
}
