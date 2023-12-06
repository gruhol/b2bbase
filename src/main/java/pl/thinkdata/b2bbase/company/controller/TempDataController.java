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
                .description("<h2>Czym jest Lorem Ipsum?</h2><p><strong>Lorem Ipsum</strong>&#160;jest tekstem stosowanym jako " +
                        "przyk&#322;adowy wype&#322;niacz w przemy&#347;le poligraficznym. Zosta&#322; po raz pierwszy u&#380;yty " +
                        "w XV w. przez nieznanego drukarza do wype&#322;nienia tekstem pr&#243;bnej ksi&#261;&#380;ki. Pi&#281;&#263; " +
                        "wiek&#243;w p&#243;&#378;niej zacz&#261;&#322; by&#263; u&#380;ywany przemy&#347;le elektronicznym, " +
                        "pozostaj&#261;c praktycznie niezmienionym. Spopularyzowa&#322; si&#281; w latach 60. XX w. wraz z publikacj&#261; " +
                        "arkuszy Letrasetu, zawieraj&#261;cych fragmenty Lorem Ipsum, a ostatnio z zawieraj&#261;cym r&#243;&#380;ne " +
                        "wersje Lorem Ipsum oprogramowaniem przeznaczonym do realizacji druk&#243;w na komputerach osobistych, jak Aldus " +
                        "PageMaker</p><h2>Do czego tego u&#380;y&#263;?</h2><p>Og&#243;lnie znana teza g&#322;osi, i&#380; u&#380;ytkownika " +
                        "mo&#380;e rozprasza&#263; zrozumia&#322;a zawarto&#347;&#263; strony, kiedy ten chce zobaczy&#263; sam jej wygl&#261;d." +
                        " Jedn&#261; z mocnych stron u&#380;ywania Lorem Ipsum jest to, &#380;e ma wiele r&#243;&#380;nych &#8222;" +
                        "kombinacji&#8221; zda&#324;, s&#322;&#243;w i akapit&#243;w, w przeciwie&#324;stwie do zwyk&#322;ego: &#8222;tekst, " +
                        "tekst, tekst&#8221;, sprawiaj&#261;cego, &#380;e wygl&#261;da to &#8222;zbyt czytelnie&#8221; po polsku. Wielu " +
                        "webmaster&#243;w i designer&#243;w u&#380;ywa Lorem Ipsum jako domy&#347;lnego modelu tekstu i wpisanie w internetowej " +
                        "wyszukiwarce &#8216;lorem ipsum&#8217; spowoduje znalezienie bardzo wielu stron, kt&#243;re wci&#261;&#380; s&#261; " +
                        "w budowie. Wiele wersji tekstu ewoluowa&#322;o i zmienia&#322;o si&#281; przez lata, czasem przez przypadek, czasem " +
                        "specjalnie (humorystyczne wstawki itd).</p>")
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
