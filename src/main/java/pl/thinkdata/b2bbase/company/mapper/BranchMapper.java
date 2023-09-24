package pl.thinkdata.b2bbase.company.mapper;

import pl.thinkdata.b2bbase.company.dto.BranchDto;
import pl.thinkdata.b2bbase.company.model.Branch;

public class BranchMapper {
    public static Branch mapToBranch(BranchDto branchDto) {
        return Branch.builder()
                .name(branchDto.getName())
                .headquarter(branchDto.isHeadquarter())
                .slug(branchDto.getCity())
                .voivodeship(branchDto.getVoivodeship())
                .post_code(branchDto.getPost_code())
                .city(branchDto.getCity())
                .street(branchDto.getStreet())
                .house_number(branchDto.getHouse_number())
                .office_number(branchDto.getOffice_number())
                .email(branchDto.getEmail())
                .phone(branchDto.getPhone())
                .latitude(branchDto.getLatitude())
                .longitude(branchDto.getLongitude())
                .build();
    }
}
