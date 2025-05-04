package jpabook.library.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class RentalBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_book_id")
    private Long id;

    // Rental과의 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id")
    private Rental rental;

    // Book과의 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public void setRental(Rental rental) {
        this.rental = rental;
        rental.getRentalBooks().add(this);
    }

    public void setBook(Book book) {
        this.book = book;
        book.getRentalBooks().add(this);
    }

    public String getTitle() {
        return book.getTitle(); // Book 객체에서 title 가져오기
    }
}
