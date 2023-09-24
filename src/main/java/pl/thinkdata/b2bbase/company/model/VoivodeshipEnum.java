package pl.thinkdata.b2bbase.company.model;

public enum VoivodeshipEnum {
    DS("dolnośląskie"),
    KP("kujawsko-pomorskie"),
    LU("lubelskie"),
    LB("lubuskie"),
    LD("łódzkie"),
    MA("małopolskie"),
    MZ("mazowieckie"),
    OP("opolskie"),
    PK("podkarpackie"),
    PD("podlaskie"),
    PM("pomorskie"),
    SL("śląskie"),
    SK("świętokrzyskie"),
    WN("warmińsko-mazurskie"),
    WP("wielkopolskie"),
    ZP("zachodniopomorskie");

    private String value;

    VoivodeshipEnum(String value) {
        this.value = value;
    }
}
