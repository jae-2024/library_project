package jpabook.library.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Author {

    @Id @GeneratedValue
    @Column(name = "author_id")
    private int id;

    private String name;
    private String country;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> authorBooks = new ArrayList<>();
}
