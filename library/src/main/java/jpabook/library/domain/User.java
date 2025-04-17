package jpabook.library.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private int id;

    private String name;
    private String email;
    private String password;
    private String phone;
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Rental> rentals = new ArrayList<>();
}
