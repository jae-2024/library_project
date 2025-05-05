package jpabook.library.rental.domain;

import jakarta.persistence.*;
import jpabook.library.book.domain.Book;
import jpabook.library.model.RentalBook;
import jpabook.library.model.RentalStatus;
import jpabook.library.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Long id;

    private LocalDateTime rentalDate;
    private LocalDateTime deadLine;
    private LocalDateTime returnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL)
    private List<RentalBook> rentalBooks = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private RentalStatus status= RentalStatus.LOAN;; //빌린 상태 [대출LOAN, 반납RETURN, 연체OVERDUE]

    //==연관관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getRentals().add(this);
    }

    public void addRentalBook(Book book) {
        RentalBook rentalBook = new RentalBook();
        rentalBook.setRental(this);
        rentalBook.setBook(book);
        rentalBooks.add(rentalBook);
    }

    // 추가적인 연관관계 메서드로 반납일 설정 등을 할 수 있음
    public void returnBook() {
        this.status = RentalStatus.RETURN;
        this.returnDate = LocalDateTime.now();
    }
}
