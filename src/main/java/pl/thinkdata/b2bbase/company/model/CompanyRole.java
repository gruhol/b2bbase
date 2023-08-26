package pl.thinkdata.b2bbase.company.model;

public enum CompanyRole {
    ADMIN("Admin"),
    TRADER("Handlowiec");

    private String value;

    CompanyRole(String value) {
        this.value = value;
    }
}
