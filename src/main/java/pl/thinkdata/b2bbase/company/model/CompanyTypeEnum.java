package pl.thinkdata.b2bbase.company.model;

public enum CompanyTypeEnum {
    CUSTOMER("Customer"),
    WHOLESALER("WholeSaler"),
    BOTH("Both");

    private String value;

    CompanyTypeEnum(String value) {
        this.value = value;
    }

    public static CompanyTypeEnum[] getCompanyTypes() {
        return CompanyTypeEnum.values();
    }
}
