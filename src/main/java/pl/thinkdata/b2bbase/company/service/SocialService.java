package pl.thinkdata.b2bbase.company.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.common.util.TokenUtil;
import pl.thinkdata.b2bbase.company.dto.EditSocialDto;
import pl.thinkdata.b2bbase.company.dto.SocialDto;
import pl.thinkdata.b2bbase.company.dto.SocialResponse;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.model.Social;
import pl.thinkdata.b2bbase.company.repository.SocialRepository;

import java.util.List;
import java.util.stream.Collectors;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.*;
import static pl.thinkdata.b2bbase.common.tool.LoginDictionary.TOKEN_HEADER;
import static pl.thinkdata.b2bbase.company.mapper.SocialMapper.mapToSocial;
import static pl.thinkdata.b2bbase.company.mapper.SocialMapper.mapToSocialResponse;

@Service
@RequiredArgsConstructor
public class SocialService {

    private final SocialRepository socialRepository;
    private final TokenUtil tokenUtil;
    private final MessageGenerator messageGenerator;
    private final CompanyService companyService;

    public SocialResponse addSocial(SocialDto socialDto, HttpServletRequest request) {
        Company companyInBase = getUserCompanyByToken(request.getHeader(TOKEN_HEADER));
        Social social = mapToSocial(socialDto);
        social.setCompanyId(companyInBase.getId());

        boolean socialTypExist = socialRepository.findAllByCompanyId(companyInBase.getId()).stream()
                .anyMatch(socialExist -> socialExist.getType() == socialDto.getType());
        if (socialTypExist) throw new InvalidRequestDataException(messageGenerator.get(YOU_CAN_ADD_ONLY_ONE_SOCIAL_TYPE));
        return mapToSocialResponse(socialRepository.save(social));
    }

    public List<SocialResponse> getSocials(HttpServletRequest request) {
        Company companyInBase = getUserCompanyByToken(request.getHeader(TOKEN_HEADER));
        return socialRepository.findAllByCompanyId(companyInBase.getId()).stream()
                .map(social -> mapToSocialResponse(social))
                .collect(Collectors.toList());
    }

    public SocialResponse editSocial(EditSocialDto editSocialDto, HttpServletRequest request) {
        Company companyInBase = getUserCompanyByToken(request.getHeader(TOKEN_HEADER));

        boolean isSocialOwner = socialRepository.findAllByCompanyId(companyInBase.getId()).stream()
                .anyMatch(socialOwner -> (socialOwner.getId() == editSocialDto.getId()));
        if (!isSocialOwner) throw new InvalidRequestDataException(messageGenerator.get(YOU_ARE_NOT_OWNER_THIS_SOCIAL_URL));

        boolean typeExist = socialRepository.findAllByCompanyId(companyInBase.getId()).stream()
                .filter(social -> social.getId() != editSocialDto.getId())
                .anyMatch(socialOwner ->  socialOwner.getType() == editSocialDto.getType());
        if (typeExist) throw new InvalidRequestDataException(messageGenerator.get(YOU_CAN_ADD_ONLY_ONE_SOCIAL_TYPE));

        Social social = mapToSocial(editSocialDto);
        social.setCompanyId(companyInBase.getId());
        return mapToSocialResponse(socialRepository.save(social));
    }

    private Company getUserCompanyByToken(String token) {
        String username = tokenUtil.validTokenAndGetUsername(token);
        return tokenUtil.getCompanyByUsernameFormDataBase(username);
    }

    public void deleteSocial(Long id, HttpServletRequest request) {
        Company companyInBase = getUserCompanyByToken(request.getHeader(TOKEN_HEADER));
        boolean isSocialOwner = socialRepository.findAllByCompanyId(companyInBase.getId()).stream()
                .anyMatch(socialOwner -> socialOwner.getId() == id);
        if (!isSocialOwner)  throw new InvalidRequestDataException(messageGenerator.get(YOU_ARE_NOT_OWNER_THIS_SOCIAL_URL));
        socialRepository.deleteById(id);
    }

    public SocialResponse getSocial(Long id, HttpServletRequest request) {
        Company company = companyService.getCompany(request);
        Social socialToEdit = socialRepository.findAllByCompanyId(company.getId()).stream()
                .filter(social -> social.getId() == id)
                .findAny()
                .orElseThrow(() -> new InvalidRequestDataException(messageGenerator.get(SOCIAL_NOT_FOUND)));
        return mapToSocialResponse(socialToEdit);
    }
}
