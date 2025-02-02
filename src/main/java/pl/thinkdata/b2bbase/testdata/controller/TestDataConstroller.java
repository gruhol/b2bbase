package pl.thinkdata.b2bbase.testdata.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.blog.model.Blog;
import pl.thinkdata.b2bbase.blog.model.BlogCategory;
import pl.thinkdata.b2bbase.blog.repository.BlogCategoryRepository;
import pl.thinkdata.b2bbase.blog.repository.BlogRepository;
import pl.thinkdata.b2bbase.common.repository.BranchRepository;
import pl.thinkdata.b2bbase.common.repository.CategoryRepository;
import pl.thinkdata.b2bbase.common.repository.CompanyRepository;
import pl.thinkdata.b2bbase.common.repository.SocialRepository;
import pl.thinkdata.b2bbase.company.model.*;
import pl.thinkdata.b2bbase.company.model.enums.*;
import pl.thinkdata.b2bbase.company.repository.Category2CompanyRepository;
import pl.thinkdata.b2bbase.company.repository.SubscriptionOrderRepository;
import pl.thinkdata.b2bbase.company.repository.UserRole2CompanyRepository;
import pl.thinkdata.b2bbase.discountcode.enums.DiscountType;
import pl.thinkdata.b2bbase.discountcode.model.DiscountCode;
import pl.thinkdata.b2bbase.discountcode.repository.DiscountCodeRepository;
import pl.thinkdata.b2bbase.preferences.service.PreferencesService;
import pl.thinkdata.b2bbase.pricelist.model.PriceList;
import pl.thinkdata.b2bbase.pricelist.repository.PriceListRepository;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.model.UserRole;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Profile("dev")
@RequestMapping("/testdata")
public class TestDataConstroller {

    private final PriceListRepository priceListRepository;
    private final UserRepository userRepository;
    private final BlogCategoryRepository blogCategoryRepository;
    private final BlogRepository blogRepository;
    private final CompanyRepository companyRepository;
    private final UserRole2CompanyRepository userRole2CompanyRepository;
    private final BranchRepository branchRepository;
    private final CategoryRepository categoryRepository;
    private final Category2CompanyRepository company2CategoryRepository;
    private final SocialRepository socialRepository;
    private final SubscriptionOrderRepository packageOrderRepository;
    private final PreferencesService preferencesService;
    private final DiscountCodeRepository discountCodeRepository;

    @GetMapping("/pricelist")
    public String createPriceListTempData() {
        PriceList priceList = new PriceList();
        priceList.setProductName("SUBSCRIPTION_BASIC");
        priceList.setActive(true);
        priceList.setPromotionPrice(false);
        priceList.setPrice(BigInteger.valueOf(59));
        priceList.setStartDate(Date.valueOf(LocalDate.of(1900, 01, 20)));
        priceList.setEndDate(Date.valueOf(LocalDate.of(9999, 01, 20)));
        priceListRepository.save(priceList);

        PriceList priceList2 = new PriceList();
        priceList2.setProductName("SUBSCRIPTION_BASIC");
        priceList2.setActive(true);
        priceList2.setPromotionPrice(true);
        priceList2.setPrice(BigInteger.valueOf(39));
        priceList2.setStartDate(Date.valueOf(LocalDate.of(2024, 01, 20)));
        priceList2.setEndDate(Date.valueOf(LocalDate.of(2025, 12, 20)));
        priceListRepository.save(priceList2);

        return "Pricelist przygotowany";
    }

    @GetMapping("/blog")
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
                .introduction("Wstęp Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
                        "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
                        "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                .addDate(new java.util.Date())
                .editDate(new java.util.Date())
                .author(user)
                .category(saveBlogCategory)
                .build();

        Blog blog2 = Blog.builder()
                .title("Wpis testowy nr2")
                .slug("wpis-testowy-nr2")
                .introduction("Wstęp Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
                        "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
                        "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                .addDate(new java.util.Date())
                .editDate(new java.util.Date())
                .author(user)
                .category(saveBlogCategory2)
                .build();

        Blog blog3 = Blog.builder()
                .title("Wpis testowy nr3")
                .slug("wpis-testowy-nr3")
                .introduction("Wstęp Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
                        "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
                        "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                .addDate(new java.util.Date())
                .editDate(new java.util.Date())
                .author(user)
                .category(saveBlogCategory)
                .build();

        Blog blog4 = Blog.builder()
                .title("Wpis testowy nr4")
                .slug("wpis-testowy-nr4")
                .introduction("Wstęp Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
                        "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
                        "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                .addDate(new java.util.Date())
                .editDate(new java.util.Date())
                .author(user)
                .category(saveBlogCategory)
                .build();

        Blog blog5 = Blog.builder()
                .title("Wpis testowy nr5")
                .slug("wpis-testowy-nr5")
                .introduction("Wstęp Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
                        "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
                        "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                .addDate(new java.util.Date())
                .editDate(new java.util.Date())
                .author(user)
                .category(saveBlogCategory2)
                .build();

        Blog blog6 = Blog.builder()
                .title("Wpis testowy nr6")
                .slug("wpis-testowy-nr6")
                .introduction("Wstęp Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
                        "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
                        "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                .addDate(new java.util.Date())
                .editDate(new java.util.Date())
                .author(user)
                .category(saveBlogCategory2)
                .build();

        Blog blog7 = Blog.builder()
                .title("Wpis testowy nr7")
                .slug("wpis-testowy-nr7")
                .introduction("Wstęp Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
                        "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
                        "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                .addDate(new java.util.Date())
                .editDate(new java.util.Date())
                .author(user)
                .category(saveBlogCategory2)
                .build();

        Blog blog8 = Blog.builder()
                .title("Wpis testowy nr8")
                .slug("wpis-testowy-nr8")
                .introduction("Wstęp Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
                        "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
                        "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                .addDate(new java.util.Date())
                .editDate(new java.util.Date())
                .author(user)
                .category(saveBlogCategory2)
                .build();

        Blog blog9 = Blog.builder()
                .title("Wpis testowy nr9")
                .slug("wpis-testowy-nr9")
                .introduction("Wstęp Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
                        "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
                        "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                .addDate(new java.util.Date())
                .editDate(new java.util.Date())
                .author(user)
                .category(saveBlogCategory)
                .build();

        Blog blog10 = Blog.builder()
                .title("Wpis testowy nr10")
                .slug("wpis-testowy-nr10")
                .introduction("Wstęp Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
                        "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
                        "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                .addDate(new java.util.Date())
                .editDate(new java.util.Date())
                .author(user)
                .category(saveBlogCategory2)
                .build();

        Blog blog11 = Blog.builder()
                .title("Wpis testowy nr11")
                .slug("wpis-testowy-nr11")
                .introduction("Wstęp Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
                        "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
                        "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                .addDate(new java.util.Date())
                .editDate(new java.util.Date())
                .author(user)
                .category(saveBlogCategory)
                .build();

        Blog blog12 = Blog.builder()
                .title("Wpis testowy nr12")
                .slug("wpis-testowy-nr12")
                .introduction("Wstęp Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
                        "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
                        "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                .addDate(new java.util.Date())
                .editDate(new java.util.Date())
                .author(user)
                .category(saveBlogCategory2)
                .build();

        Blog blog13 = Blog.builder()
                .title("Wpis testowy nr13")
                .slug("wpis-testowy-nr13")
                .introduction("Wstęp Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been " +
                        "the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of " +
                        "type and scrambled it to make a type specimen book. It has survived not only five centuries, but " +
                        "also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
                        "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently " +
                        "with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                .addDate(new java.util.Date())
                .editDate(new java.util.Date())
                .author(user)
                .category(saveBlogCategory)
                .build();

        blogRepository.saveAll(Arrays.asList(blog1, blog2, blog3,blog4,blog5, blog6, blog7, blog8, blog9, blog10, blog11, blog12, blog13));

        return "Dane przygotowane";
    }

    @GetMapping("/company")
    public String createCompanyTempData() {

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
                .active(true)
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

        socialRepository.save(Social.builder()
                .url("https://www.etutor.pl/profil")
                .type(SocialTypeEnum.FACEBOOK)
                .companyId(newCompany.getId())
                .build());

        socialRepository.save(Social.builder()
                .url("https://www.twitch.tv")
                .type(SocialTypeEnum.LINKEDIN)
                .companyId(newCompany.getId())
                .build());

        socialRepository.save(Social.builder()
                .url("https://www.hltv.org/")
                .type(SocialTypeEnum.INSTAGRAM)
                .companyId(newCompany.getId())
                .build());

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
                .shortDescription("Zdrowie i uroda short description")
                .description("Zdrowie i uroda long description")
                .h1("Zdrowie i uroda h1")
                .title("Zdrowie i uroda title")
                .build();

        Category zdrowieIurodaSave = categoryRepository.save(zdrowieIuroda);

        Category farmaceutyka = Category.builder()
                .name("Farmaceutyka")
                .slug("farmaceutyka")
                .parent(zdrowieIuroda)
                .shortDescription("Farmaceutyka short description")
                .description("Farmaceutyka long description")
                .h1("Farmaceutyka h1")
                .title("Farmaceutyka title")
                .build();

        Category fryzjerstwo = Category.builder()
                .name("Fryzjerstwo")
                .slug("fryzjerstwo")
                .parent(zdrowieIuroda)
                .shortDescription("Fryzjerstwo short description")
                .description("Fryzjerstwo long description")
                .h1("Fryzjerstwo h1")
                .title("Fryzjerstwo title")
                .build();

        Category kosmetyki = Category.builder()
                .name("kosmetyki")
                .slug("kosmetyki")
                .parent(zdrowieIuroda)
                .shortDescription("kosmetyki short description")
                .description("kosmetyki long description")
                .h1("kosmetyki h1")
                .title("kosmetyki title")
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
                .shortDescription("Erotyka short description")
                .description("Erotyka long description")
                .h1("Erotyka H1")
                .title("Erotyka Title")
                .build();

        Category erotykaSave = categoryRepository.save(erotyka);

        Category bieliznaIodziez = Category.builder()
                .name("Bielizna i odzież")
                .slug("bielizna-i-odzież")
                .shortDescription("Bielizna i odzież short description")
                .description("Bielizna i odzież long description")
                .parent(erotykaSave)
                .h1("Bielizna i odzież h1")
                .title("Bielizna i odzież title")
                .build();

        Category drogeriaErotyczna = Category.builder()
                .name("Drogeria erotyczna")
                .slug("drogeria-erotyczna")
                .parent(erotykaSave)
                .shortDescription("Drogeria erotyczna short description")
                .description("Drogeria erotyczna long description")
                .h1("Drogeria erotyczna h1")
                .title("Drogeria erotyczna title")
                .build();

        categoryRepository.saveAll(Arrays.asList(bieliznaIodziez, drogeriaErotyczna));

        packageOrderRepository.save(SubscriptionOrder.builder()
                .companyId(newCompany.getId())
                .startDate(Date.valueOf(LocalDate.of(1900, 01, 20)))
                .endDate(Date.valueOf(LocalDate.of(2100, 01, 20)))
                .price(new BigInteger("59"))
                .subscriptionType(SubscriptionTypeEnum.BASIC)
                .paymentType(PaymentTypeEnum.BANK_TRANSFER)
                .paymentStatus(PaymentStatusEnum.NOTPAID)
                .build());

        priceListRepository.save(PriceList.builder()
                        .isActive(true)
                        .productName("SUBSCRIPTION_BASIC")
                        .startDate(Date.valueOf(LocalDate.of(1900, 01, 20)))
                        .endDate(Date.valueOf(LocalDate.of(2100, 01, 20)))
                        .price(new BigInteger("59"))
                .build());

        return "Created";
    }

    @GetMapping("/code")
    public String createCodeTempData() {
        DiscountCode code = DiscountCode.builder()
                .code("dupa")
                .subscriptionName("SUBSCRIPTION_BASIC")
                .createdAt(Date.valueOf(LocalDate.of(2025, 01, 27)))
                .startDate(Date.valueOf(LocalDate.of(2022, 01, 27)))
                .endDate(Date.valueOf(LocalDate.of(2026, 01, 27)))
                .discountType(DiscountType.PRECENTAGE)
                .usage_limit(100)
                .isActive(true)
                .discountAmount(new BigDecimal("0.5"))
                .build();

        DiscountCode codeExpired = DiscountCode.builder()
                .code("expired")
                .subscriptionName("SUBSCRIPTION_BASIC")
                .createdAt(Date.valueOf(LocalDate.of(2025, 01, 27)))
                .startDate(Date.valueOf(LocalDate.of(2022, 01, 27)))
                .endDate(Date.valueOf(LocalDate.of(2023, 01, 27)))
                .discountType(DiscountType.PRECENTAGE)
                .usage_limit(100)
                .isActive(true)
                .discountAmount(new BigDecimal("0.5"))
                .build();

        DiscountCode limit = DiscountCode.builder()
                .code("limit")
                .subscriptionName("SUBSCRIPTION_BASIC")
                .createdAt(Date.valueOf(LocalDate.of(2025, 01, 27)))
                .startDate(Date.valueOf(LocalDate.of(2022, 01, 27)))
                .endDate(Date.valueOf(LocalDate.of(2025, 01, 27)))
                .discountType(DiscountType.PRECENTAGE)
                .usage_limit(0)
                .isActive(true)
                .discountAmount(new BigDecimal("0.5"))
                .build();

        DiscountCode disable = DiscountCode.builder()
                .code("disable")
                .subscriptionName("SUBSCRIPTION_BASIC")
                .createdAt(Date.valueOf(LocalDate.of(2025, 01, 27)))
                .startDate(Date.valueOf(LocalDate.of(2022, 01, 27)))
                .endDate(Date.valueOf(LocalDate.of(2025, 01, 27)))
                .discountType(DiscountType.PRECENTAGE)
                .usage_limit(0)
                .isActive(false)
                .discountAmount(new BigDecimal("0.5"))
                .build();

        discountCodeRepository.saveAll(Arrays.asList(code, codeExpired, limit, disable));
        return "Kod dodany";
    }
}
