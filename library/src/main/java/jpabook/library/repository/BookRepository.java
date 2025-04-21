package jpabook.library.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.library.domain.Book;
import jpabook.library.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final EntityManager em;

    public void save(Book book) {
        em.persist(book);
    }

    public Book findOne(int id) {
        return em.find(Book.class, id);
    }

    public List<Book> findAll() {
        return em.createQuery("select m from Book m", Book.class)
                .getResultList();
    }

    public List<Book> findByName(String title) {
        return em.createQuery("select m from Book m where m.title = :title", Book.class)
                .setParameter("title", title)
                .getResultList();
    }
}
