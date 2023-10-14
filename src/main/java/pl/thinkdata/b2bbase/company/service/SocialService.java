package pl.thinkdata.b2bbase.company.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.common.util.TokenUtil;
import pl.thinkdata.b2bbase.company.dto.EditSocialDto;
import pl.thinkdata.b2bbase.company.dto.SocialDto;
import pl.thinkdata.b2bbase.company.mapper.SocialMapper;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.model.Social;
import pl.thinkdata.b2bbase.company.repository.SocialRepository;

import java.util.List;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.YOU_ARE_NOT_OWNER_THIS_SOCIAL_URL;
import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.YOU_CAN_ADD_ONLY_ONE_SOCIAL_TYPE;
import static pl.thinkdata.b2bbase.common.tool.LoginDictionary.TOKEN_HEADER;
import static pl.thinkdata.b2bbase.company.mapper.SocialMapper.mapToSocial;

@Service
@RequiredArgsConstructor
public class SocialService {

    private final SocialRepository socialRepository;
    private final TokenUtil tokenUtil;
    private final MessageGenerator messageGenerator;

    public Social addSocial(SocialDto socialDto, HttpServletRequest request) {
        Company companyInBase = getUserCompanyByToken(request.getHeader(TOKEN_HEADER));
        Social social = mapToSocial(socialDto);
        social.setCompanyId(companyInBase.getId());

        boolean socialTypExist = socialRepository.findAllByCompanyId(companyInBase.getId()).stream()
                .filter(socialExist -> socialExist.getType() == socialDto.getType())
                .findAny().isPresent();
        if (socialTypExist) throw new InvalidRequestDataException(messageGenerator.get(YOU_CAN_ADD_ONLY_ONE_SOCIAL_TYPE));
        return socialRepository.save(social);
    }

    public List<Social> getSocials(HttpServletRequest request) {
        Company companyInBase = getUserCompanyByToken(request.getHeader(TOKEN_HEADER));
        return socialRepository.findAllByCompanyId(companyInBase.getId());
    }

    public Social editSocial(EditSocialDto editSocialDto, HttpServletRequest request) {
        Company companyInBase = getUserCompanyByToken(request.getHeader(TOKEN_HEADER));
        boolean isSocialOwner = socialRepository.findAllByCompanyId(companyInBase.getId()).stream()
                .filter(socialOwner -> socialOwner.getCompanyId() == editSocialDto.getId())
                .findAny().isPresent();
        if (!isSocialOwner)  throw new InvalidRequestDataException(messageGenerator.get(YOU_ARE_NOT_OWNER_THIS_SOCIAL_URL));
        return socialRepository.save(mapToSocial(editSocialDto));
    }

    private Company getUserCompanyByToken(String token) {
        String username = tokenUtil.validTokenAndGetUsername(token);
        return tokenUtil.getCompanyByUsernameFormDataBase(username);
    }

    public void deleteSocial(Long id, HttpServletRequest request) {
        Company companyInBase = getUserCompanyByToken(request.getHeader(TOKEN_HEADER));
        boolean isSocialOwner = socialRepository.findAllByCompanyId(companyInBase.getId()).stream()
                .filter(socialOwner -> socialOwner.getCompanyId() == id)
                .findAny().isPresent();
        if (!isSocialOwner)  throw new InvalidRequestDataException(messageGenerator.get(YOU_ARE_NOT_OWNER_THIS_SOCIAL_URL));
        socialRepository.deleteById(id);
    }
}
