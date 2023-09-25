package pl.thinkdata.b2bbase.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.thinkdata.b2bbase.company.model.VoivodeshipEnum;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class BranchResponse {
    private Long id;
    private String name;
    private boolean headquarter;
    private String slug;
    private VoivodeshipEnum voivodeship;
    private String post_code;
    private String city;
    private String street;
    private String house_number;
    private String office_number;
    private String email;
    private String phone;
    private String latitude;
    private String longitude;
}
