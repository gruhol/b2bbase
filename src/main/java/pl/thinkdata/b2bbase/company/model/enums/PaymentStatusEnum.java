package pl.thinkdata.b2bbase.company.model.enums;

public enum PaymentStatusEnum {
    NOTPAID("Niezapłacone"),
    PAID("Zapłacone");

    private String value;

    PaymentStatusEnum(String value) {
        this.value = value;
    }

    public static PaymentStatusEnum[] getCompanyTypes() {
        return PaymentStatusEnum.values();
    }
}
