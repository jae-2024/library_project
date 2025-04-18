package jpabook.library.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
public class Rental {

    @Id @GeneratedValue
    @Column(name = "rental_id")
    private int id;

    private LocalDateTime rental_date;
    private LocalDateTime deadline;
    private LocalDateTime return_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL)
    private List<Book> rentalBooks = new ArrayList<>();
}
