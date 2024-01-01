package pl.thinkdata.b2bbase.search.constroller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.thinkdata.b2bbase.search.dto.SearchCompanyResult;
import pl.thinkdata.b2bbase.search.service.SearchService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/{keyword}")
    public SearchCompanyResult searchCompany(@PathVariable(required = true) String keyword,
                                             @RequestParam(required = false) List<Long> categories,
                                             @RequestParam(required = false) List<String> voivodeshipSlugs,
                                             @RequestParam(required = false) Boolean isEdiCooperation,
                                             @RequestParam(required = false) Boolean isApiCooperation,
                                             @RequestParam(required = false) Boolean isProductFileCooperation,
                                             Pageable pageable) {
        return searchService.searchCompanyWithCategoryAndVoivodeshipByKeyword("%" + keyword + "%", categories,
                voivodeshipSlugs, isEdiCooperation, isApiCooperation, isProductFileCooperation, pageable);
    }
}
