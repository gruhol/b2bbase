package pl.thinkdata.b2bbase.company.model.enums;

public enum PackageTypeEnum {
    BASIC("Podstawowy");

    private String value;

    PackageTypeEnum(String value) {
        this.value = value;
    }

    public static PackageTypeEnum[] getCompanyTypes() {
        return PackageTypeEnum.values();
    }
}
