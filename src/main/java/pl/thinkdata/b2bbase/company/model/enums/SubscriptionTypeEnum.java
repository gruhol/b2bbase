package pl.thinkdata.b2bbase.company.model.enums;

public enum SubscriptionTypeEnum {
    BASIC("Podstawowy"),
    BUSINESS("Biznesowy");

    private String value;

    SubscriptionTypeEnum(String value) {
        this.value = value;
    }

    public static SubscriptionTypeEnum[] getCompanyTypes() {
        return SubscriptionTypeEnum.values();
    }
}
