package pl.thinkdata.b2bbase.company.model.enums;

public enum VoivodeshipEnum {
    DS("dolnośląskie", "dolnoslaskie"),
    KP("kujawsko-pomorskie", "kujawsko-pomorskie"),
    LU("lubelskie", "lubelskie"),
    LB("lubuskie", "lubuskie"),
    LD("łódzkie", "lodzkie"),
    MA("małopolskie", "malopolskie"),
    MZ("mazowieckie", "mazowieckie"),
    OP("opolskie", "opolskie"),
    PK("podkarpackie", "podkarpackie"),
    PD("podlaskie", "podlaskie"),
    PM("pomorskie", "pomorskie"),
    SL("śląskie", "slaskie"),
    SK("świętokrzyskie", "swietokrzyskie"),
    WN("warmińsko-mazurskie", "warminsko-mazurskie"),
    WP("wielkopolskie", "wielkopolskie"),
    ZP("zachodniopomorskie", "zachodniopomorskie");

    private String value;
    private String slug;

    VoivodeshipEnum(String value, String slug) {
        this.value = value;
        this.slug = slug;
    }

    public String getValue() {
        return value;
    }

    public String getSlug() {
        return slug;
    }
}
