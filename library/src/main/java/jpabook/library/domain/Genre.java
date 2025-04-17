package jpabook.library.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Genre {

    @Id @GeneratedValue
    @Column(name = "genre_id")
    private int id;

    private String genre_name;
    private String description;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private List<Book_genre> book_genres = new ArrayList<>();
}
