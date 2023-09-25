package pl.thinkdata.b2bbase.company.mapper;

import pl.thinkdata.b2bbase.company.dto.BranchDto;
import pl.thinkdata.b2bbase.company.dto.BranchResponse;
import pl.thinkdata.b2bbase.company.dto.BranchToEditDto;
import pl.thinkdata.b2bbase.company.model.Branch;
import pl.thinkdata.b2bbase.company.model.Company;

import static pl.thinkdata.b2bbase.company.comonent.SlugGenerator.toSlug;

public class BranchMapper {
    public static Branch mapToBranchFromBranchToEditDto(BranchDto branchDto) {
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

    public static BranchResponse mapToBranchResponse(Branch branch) {
        return BranchResponse.builder()
                .id(branch.getId())
                .headquarter(branch.isHeadquarter())
                .slug(branch.getSlug())
                .voivodeship(branch.getVoivodeship())
                .post_code(branch.getPost_code())
                .city(branch.getCity())
                .street(branch.getStreet())
                .house_number(branch.getHouse_number())
                .office_number(branch.getOffice_number())
                .email(branch.getEmail())
                .phone(branch.getPhone())
                .build();
    }

    public static Branch mapToBranchFromBranchToEditDto(BranchToEditDto branchToEditDto, Company company) {
        return Branch.builder()
                .id(branchToEditDto.getId())
                .name(branchToEditDto.getName())
                .headquarter(branchToEditDto.isHeadquarter())
                .slug(branchToEditDto.getCity())
                .voivodeship(branchToEditDto.getVoivodeship())
                .post_code(branchToEditDto.getPost_code())
                .city(branchToEditDto.getCity())
                .street(branchToEditDto.getStreet())
                .house_number(branchToEditDto.getHouse_number())
                .office_number(branchToEditDto.getOffice_number())
                .email(branchToEditDto.getEmail())
                .phone(branchToEditDto.getPhone())
                .latitude(branchToEditDto.getLatitude())
                .longitude(branchToEditDto.getLongitude())
                .company(company)
                .build();
    }

    public static Branch mapToBranchFromBranchDto(BranchDto branchDto, Company company) {
        return Branch.builder()
                .name(branchDto.getName())
                .headquarter(branchDto.isHeadquarter())
                .slug(toSlug(branchDto.getName()))
                .voivodeship(branchDto.getVoivodeship())
                .post_code(branchDto.getPost_code())
                .city(branchDto.getCity())
                .street(branchDto.getStreet())
                .house_number(branchDto.getHouse_number())
                .office_number(branchDto.getOffice_number())
                .email(branchDto.getEmail())
                .phone(branchDto.getPhone())
                .longitude(branchDto.getLongitude())
                .latitude(branchDto.getLatitude())
                .company(company)
                .build();
    }
}
