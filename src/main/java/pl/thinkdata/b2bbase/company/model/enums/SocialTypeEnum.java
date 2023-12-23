package pl.thinkdata.b2bbase.company.model.enums;

public enum SocialTypeEnum {
    FACEBOOK("Facebook"),
    LINKEDIN("LinkedIn"),
    TWITTER("Twitter"),
    YOUTUBE("YouTube"),
    INSTAGRAM("Instagram"),
    TIKTOK("TikTok");

    private String value;

    SocialTypeEnum(String value) {
        this.value = value;
    }

}
