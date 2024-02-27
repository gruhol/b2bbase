package pl.thinkdata.b2bbase.staticpage.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaticPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @CreatedDate
    private Date add_date;
    @LastModifiedDate
    private Date edit_date;
    private String content;
    private String slug;
}
