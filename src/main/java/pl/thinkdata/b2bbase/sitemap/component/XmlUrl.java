package pl.thinkdata.b2bbase.sitemap.component;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "url")
public class XmlUrl {
    public enum Priority {
        TOP("1.0"),
        HIGH("0.8"),
        MEDIUM("0.6"),
        LOW("0.3");

        private String value;

        Priority(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @XmlElement
    private String loc;

    @XmlElement
    private String lastmod;

    @XmlElement
    private String changefreq = "daily";

    @XmlElement
    private String priority;

    public XmlUrl() {
        setLastmod();
    }

    public XmlUrl(String loc, Priority priority) {
        this.loc = loc;
        this.priority = priority.getValue();
        setLastmod();
    }

    private void setLastmod() {
        this.lastmod = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getLoc() {
        return loc;
    }

    public String getPriority() {
        return priority;
    }

    public String getChangefreq() {
        return changefreq;
    }

    public String getLastmod() {
        return lastmod;
    }
}