package exercise.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

// BEGIN
@Getter
@Setter
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Lob
    private String body;
    @ManyToOne
    private Category category;

}
// END
