package pl.thinkdata.b2bbase.company.service;

import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mock.web.MockHttpServletRequest;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.common.util.TokenUtil;
import pl.thinkdata.b2bbase.company.dto.EditSocialDto;
import pl.thinkdata.b2bbase.company.dto.SocialDto;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.model.Social;
import pl.thinkdata.b2bbase.company.model.enums.SocialTypeEnum;
import pl.thinkdata.b2bbase.common.repository.SocialRepository;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SocialServiceTest {

    @Mock
    private SocialRepository socialRepository;
    @Mock
    private TokenUtil tokenUtil;
    @Mock
    private CompanyService companyService;
    @Inject
    private SocialService service;


    @BeforeEach
    void init() {

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(-1);
        messageSource.setBasenames("classpath:i18n/messages");

        this.service = new SocialService(
                this.socialRepository,
                this.tokenUtil,
                new MessageGenerator(messageSource),
                this.companyService);
    }

    @Test
    void shouldThrowInvalidRequestDataExceptionWhenTypeOfSocialExistInDataBase() {
        Social socialInDataBase = Social.builder()
                .id(2L)
                .type(SocialTypeEnum.FACEBOOK)
                .build();
        Company tempCompany = Company.builder()
                .id(2L)
                .build();
        when(tokenUtil.getCompanyByUsernameFormDataBase(any())).thenReturn(tempCompany);
        when(socialRepository.findAllByCompanyId(any())).thenReturn(Arrays.asList(socialInDataBase));
        SocialDto socialDto = new SocialDto();
        socialDto.setType(SocialTypeEnum.FACEBOOK);
        InvalidRequestDataException exception = assertThrows(InvalidRequestDataException.class,
                () -> service.addSocial(socialDto, new MockHttpServletRequest()));
        assertEquals("Możesz dodać tylko jeden link z danego social mediów.", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidRequestDataExceptionWhenEditNotTheSameSocial() {
        Social socialInDataBase = Social.builder()
                .id(1L)
                .type(SocialTypeEnum.FACEBOOK)
                .build();
        Company tempCompany = Company.builder()
                .id(2L)
                .build();
        when(tokenUtil.getCompanyByUsernameFormDataBase(any())).thenReturn(tempCompany);
        when(socialRepository.findAllByCompanyId(any())).thenReturn(Arrays.asList(socialInDataBase));
        EditSocialDto editSocialDto = new EditSocialDto();
        editSocialDto.setId(2L);
        editSocialDto.setType(SocialTypeEnum.FACEBOOK);
        InvalidRequestDataException exception = assertThrows(InvalidRequestDataException.class,
                () -> service.editSocial(editSocialDto, new MockHttpServletRequest()));
        assertEquals("Nie jesteś właścicielem tego linku społecznościowego.", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidRequestDataExceptionWhenEditToSocialTypeWitchExist() {
        Social socialInDataBase1 = Social.builder()
                .id(1L)
                .type(SocialTypeEnum.FACEBOOK)
                .build();
        Social socialInDataBase2 = Social.builder()
                .id(2L)
                .type(SocialTypeEnum.LINKEDIN)
                .build();
        Company tempCompany = Company.builder()
                .id(2L)
                .build();
        when(tokenUtil.getCompanyByUsernameFormDataBase(any())).thenReturn(tempCompany);
        when(socialRepository.findAllByCompanyId(any())).thenReturn(Arrays.asList(socialInDataBase1, socialInDataBase2));
        when(socialRepository.findAllByCompanyId(any())).thenReturn(Arrays.asList(socialInDataBase1, socialInDataBase2));
        EditSocialDto editSocialDto = new EditSocialDto();
        editSocialDto.setId(2L);
        editSocialDto.setType(SocialTypeEnum.FACEBOOK);
        InvalidRequestDataException exception = assertThrows(InvalidRequestDataException.class,
                () -> service.editSocial(editSocialDto, new MockHttpServletRequest()));
        assertEquals("Możesz dodać tylko jeden link z danego social mediów.", exception.getMessage());
    }



}