package pl.thinkdata.b2bbase.company.model;

public enum CompanyType {
    CUSTOMER("Customer"),
    WHOLESALER("WholeSaler"),
    BOTH("Both");

    private String value;

    CompanyType(String value) {
        this.value = value;
    }

    public static CompanyType[] getCompanyTypes() {
        return CompanyType.values();
    }
}
