package pl.thinkdata.b2bbase.company.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractCompany {
    private String nip;
    private String regon;
}
