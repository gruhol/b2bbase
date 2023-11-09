package pl.thinkdata.b2bbase.company.service;

import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.company.dto.BranchDto;
import pl.thinkdata.b2bbase.company.model.Branch;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.repository.BranchRepository;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchServiceTest {

    @Mock
    private CompanyService companyService;
    @Mock
    private BranchRepository branchRepository;
    @Inject
    private BranchService branchService;

    @BeforeEach
    void init() {

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(-1);
        messageSource.setBasenames("classpath:i18n/messages");

        this.branchService = new BranchService(branchRepository, this.companyService, new MessageGenerator(messageSource));
    }

    @Test
    void shouldThrowExceptionWhenInDataBaseExistHeadquarter() {
        //given
        BranchDto branchDto = new BranchDto();
        branchDto.setName("Some Company");
        branchDto.setHeadquarter(true);
        Branch branchInDataBase = Branch.builder().headquarter(true).build();
        Company company = Company.builder().branches(Arrays.asList(branchInDataBase)).build();
        //when
        when(companyService.getCompany(any())).thenReturn(company);
        when(branchRepository.findAllByCompany(any())).thenReturn(Arrays.asList(branchInDataBase));
        //then
        InvalidRequestDataException exception = assertThrows(InvalidRequestDataException.class,
                () -> branchService.addBranch(branchDto,  any()));
        assertEquals("Możesz wprowadzić tylko jedną siedzibę główną", exception.getMessage());
    }

}