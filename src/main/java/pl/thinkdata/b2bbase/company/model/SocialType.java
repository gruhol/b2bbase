package pl.thinkdata.b2bbase.company.model;

public enum SocialType {
    FACEBOOK("Facebook"),
    LINKEDIN("LinkedIn"),
    TWITTER("Twitter"),
    YOUTUBE("YouTube"),
    INSTAGRAM("Instagram"),
    TIKTOK("TikTok");

    private String value;

    SocialType(String value) {
        this.value = value;
    }

}