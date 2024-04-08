package pl.thinkdata.b2bbase.blog.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import pl.thinkdata.b2bbase.security.model.User;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private BlogCategory category;
    @CreatedDate
    private Date addDate;
    @LastModifiedDate
    private Date editDate;
    private String introduction;
    private String content;
    private String slug;
    @ManyToOne
    @JoinColumn(name = "author")
    private User author;
}
