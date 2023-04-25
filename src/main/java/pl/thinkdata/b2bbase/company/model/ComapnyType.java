package pl.thinkdata.b2bbase.company.model;

public enum ComapnyType {
    CUSTOMER("Customer"),
    WHOLESALER("WholeSaler");

    private String value;

    ComapnyType(String value) {
        this.value = value;
    }
}
