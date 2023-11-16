package pl.thinkdata.b2bbase.company.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.company.model.*;
import pl.thinkdata.b2bbase.company.repository.BranchRepository;
import pl.thinkdata.b2bbase.common.repository.CategoryRepository;
import pl.thinkdata.b2bbase.company.repository.Category2CompanyRepository;
import pl.thinkdata.b2bbase.common.repository.CompanyRepository;
import pl.thinkdata.b2bbase.company.repository.UserRole2CompanyRepository;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.model.UserRole;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Profile("dev")
public class TempDataController {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final UserRole2CompanyRepository userRole2CompanyRepository;
    private final BranchRepository branchRepository;
    private final CategoryRepository categoryRepository;
    private final Category2CompanyRepository company2CategoryRepository;

    @GetMapping("/testdata")
    public String createTempData() {

        User user = userRepository.save(User.builder()
                .firstname("Wojciech")
                .lastname("Dąbrowski")
                .username("dabrowskiw@gmail.com")
                .phone("123456789")
                .password("{bcrypt}" + new BCryptPasswordEncoder().encode("Aniakopec32"))
                .enabled(true)
                .authorities(List.of(UserRole.ROLE_USER))
                .build());

        Company newCompany = companyRepository.save(Company.builder()
                .name("Dupa")
                .slug("dupa")
                .type(CompanyTypeEnum.WHOLESALER)
                .nip("5327055988")
                .regon("336099521")
                .legalForm(LegalFormEnum.JDG)
                .krs("1234567899")
                .email("dabrowskiw@gmail.com")
                .phone("123456789")
                .wwwSite("http://www.wp.pl")
                .wwwStore("http://www.wp.pl")
                .build());

        UserRole2Company userRole2Company = UserRole2Company.builder()
                .company(newCompany)
                .role(CompanyRoleEnum.ADMIN)
                .user(user)
                .build();
        userRole2CompanyRepository.save(userRole2Company);

        branchRepository.save(Branch.builder()
                .name("Dupa Warszawa")
                        .headquarter(true)
                        .company(newCompany)
                        .slug("dupa-warszawa")
                        .voivodeship(VoivodeshipEnum.LB)
                        .post_code("04-113")
                        .city("Warszawa")
                        .street("Łukowska")
                        .house_number("1")
                        .office_number("2")
                        .email("dabrowskiw@gmail.com")
                        .phone("123456789")
                .build());

        Category zdrowieIuroda = Category.builder()
                .name("Zdrowie i uroda")
                .slug("zdrowie-i-uroda")
                .build();

        Category zdrowieIurodaSave = categoryRepository.save(zdrowieIuroda);

        Category farmaceutyka = Category.builder()
                .name("Farmaceutyka")
                .slug("farmaceutyka")
                .parent(zdrowieIuroda)
                .build();

        Category fryzjerstwo = Category.builder()
                .name("Fryzjerstwo")
                .slug("fryzjerstwo")
                .parent(zdrowieIuroda)
                .build();

        Category kosmetyki = Category.builder()
                .name("kosmetyki")
                .slug("kosmetyki")
                .parent(zdrowieIuroda)
                .build();

        categoryRepository.saveAll(Arrays.asList(kosmetyki, farmaceutyka, fryzjerstwo));

        Category2Company category2Company = Category2Company.builder()
                .categoryId(2L)
                .companyId(newCompany.getId())
                .build();

        company2CategoryRepository.save(category2Company);


        Category erotyka = Category.builder()
                .name("Erotyka")
                .slug("erotyka")
                .build();

        Category erotykaSave = categoryRepository.save(erotyka);

        Category bieliznaIodziez = Category.builder()
                .name("Bielizna i odzież")
                .slug("bielizna-i-odzież")
                .parent(erotykaSave)
                .build();

        Category drogeriaErotyczna = Category.builder()
                .name("Drogeria erotyczna")
                .slug("drogeria-erotyczna")
                .parent(erotykaSave)
                .build();

        categoryRepository.saveAll(Arrays.asList(bieliznaIodziez, drogeriaErotyczna));

        return "Created";
    }
}
