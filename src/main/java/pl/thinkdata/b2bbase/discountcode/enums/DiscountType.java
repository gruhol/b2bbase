package pl.thinkdata.b2bbase.discountcode.enums;

public enum DiscountType {
    PRECENTAGE("percentage"),
    VALUE("value");

    private String value;

    DiscountType(String value) {
        this.value = value;
    }

    public static DiscountType[] getCompanyTypes() {
        return DiscountType.values();
    }
}
