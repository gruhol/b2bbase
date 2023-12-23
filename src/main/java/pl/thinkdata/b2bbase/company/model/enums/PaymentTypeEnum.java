package pl.thinkdata.b2bbase.company.model.enums;

public enum PaymentTypeEnum {
    BANK_TRANSFER("Przelew");

    private String value;

    PaymentTypeEnum(String value) {
        this.value = value;
    }

    public static PaymentTypeEnum[] getCompanyTypes() {
        return PaymentTypeEnum.values();
    }
}
