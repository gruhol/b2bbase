package pl.thinkdata.b2bbase.company.model;

public enum LegalForm {
    JDG("Jednoosobowa działalność gospodarcza"),
    SC("Spółka cywilna"),
    SJ("Spółka jawna"),
    SP("Spółka partnerska"),
    SK("Spółka komandytowa"),
    SKA("Spółka komandytowo-akcyjna"),
    ZOO("Spółka z ograniczoną odpowiedzialnością"),
    PSA("Prosta spółka akcyjna"),
    SA("Spółka akcyjna");

    private String value;

    LegalForm(String value) {
        this.value = value;
    }

}
