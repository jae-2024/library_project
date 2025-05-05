package jpabook.library.model;

import jakarta.persistence.*;
import jpabook.library.book.domain.Book;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class BookGenre {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookGenre_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public void setBook(Book book) {
        this.book = book;
        book.getBookGenres().add(this);
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
        genre.getBookGenre().add(this);
    }
}
