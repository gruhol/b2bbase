package pl.thinkdata.b2bbase.discountcode.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import pl.thinkdata.b2bbase.discountcode.enums.DiscountType;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class DiscountCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String subscriptionName;
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    private BigDecimal discountAmount;
    private int usageLimit;
    private Date startDate;
    private Date endDate;
    private boolean isActive;
    @CreatedDate
    private Date createdAt;

    public boolean isNotExpired(Date date) {
        return date.after(startDate) && date.before(endDate);
    }
}
