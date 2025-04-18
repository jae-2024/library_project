package jpabook.library.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.library.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    @PersistenceContext
    private final EntityManager em;

    private void save(Book book) {
        em.persist(book);
    }

    private Book findOne(int id) {
        return em.find(Book.class, id);
    }

    private List<Book> findAll() {
        return em.createQuery("select m from Book m", Book.class)
                .getResultList();
    }
}
