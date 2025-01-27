package pl.thinkdata.b2bbase.discountcode.model;

import jakarta.persistence.*;
import lombok.*;
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
    @Enumerated(EnumType.STRING)
    private DiscountType discount_type;
    private BigDecimal discount_amount;
    private int usage_limit;
    private Date startDate;
    private Date endDate;
    private boolean isActive;
    @CreatedDate
    private Date createdAt;

    public boolean isNotExpired(Date date) {
        return date.after(startDate) && date.before(endDate);
    }
}
