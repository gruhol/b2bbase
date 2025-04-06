package pl.thinkdata.b2bbase.product.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.common.util.TokenUtil;
import pl.thinkdata.b2bbase.company.validator.RegistrationValidator;
import pl.thinkdata.b2bbase.product.dto.ProductCompanyDto;
import pl.thinkdata.b2bbase.product.model.ProductCompany;
import pl.thinkdata.b2bbase.product.repository.ProductCompanyRepository;
import pl.thinkdata.b2bbase.product.validator.ProductValidator;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import java.util.Optional;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.USER_FROM_GIVEN_TOKEN_NOT_FOUND;
import static pl.thinkdata.b2bbase.common.tool.LoginDictionary.TOKEN_HEADER;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final MessageGenerator messageGenerator;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final TokenUtil tokenUtil;
    private final ProductCompanyRepository productCompanyRepository;

    public ProductCompany addProductService(ProductCompanyDto productCompanyDto, HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);
        ProductValidator productValidator = new ProductValidator(productCompanyDto, this);
        productValidator.valid();

        String username = tokenUtil.validTokenAndGetUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        User user = Optional.ofNullable(userRepository.findByUsername(userDetails.getUsername())).get()
                .orElseThrow(() -> new InvalidRequestDataException(messageGenerator.get(USER_FROM_GIVEN_TOKEN_NOT_FOUND)));

//        if(userRole2CompanyRepository.findByUser(user).isPresent()) {
//            throw new InvalidRequestDataException(messageGenerator.get(YOU_CAN_ADD_ONLY_ONE_COMPANY));
//        }
        //check if user is admin company
        //check if product ean exist - if not create new in product table

        return null;
    }

    public boolean checkIfEanExistInProductCompany(String ean) {
        return !productCompanyRepository.findByProduct_Ean(ean).isEmpty();
    }
}
