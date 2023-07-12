package pl.thinkdata.b2bbase.security.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "verification_link")
public class VerificationLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token")
    private Long id;
    private String token;
    @Column(name = "is_consumed")
    private Boolean isConsumed;
    @Column(name = "expired_date")
    private LocalDateTime expiredDateTime;
    @Column(name = "issued_date")
    private LocalDateTime issuedDateTime;
    @Column(name = "confirmed_date")
    private LocalDateTime confirmedDateTime;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_user")
    private User user;

    public VerificationLink(){
        this.token = UUID.randomUUID().toString();
        this.issuedDateTime = LocalDateTime.now();
        this.expiredDateTime = this.issuedDateTime.plusDays(1);
        this.isConsumed = false;
    }
}
