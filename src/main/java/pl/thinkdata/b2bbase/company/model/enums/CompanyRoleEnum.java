package pl.thinkdata.b2bbase.company.model.enums;

public enum CompanyRoleEnum {
    ADMIN("Admin"),
    TRADER("Handlowiec");

    private String value;

    CompanyRoleEnum(String value) {
        this.value = value;
    }
}
