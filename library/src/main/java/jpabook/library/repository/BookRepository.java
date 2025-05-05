package jpabook.library.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.library.domain.Book;
import jpabook.library.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final EntityManager em;

    @Transactional
    public void save(Book book) {em.persist(book);}

    public Book findOne(Long id) {
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

    public void deleteById(Long id) {
        Book book = em.find(Book.class, id);

        if (book != null) {
            em.remove(book);
        } else {
            throw new IllegalArgumentException("책을 찾을 수 없습니다. ID: " + id);
        }
    }

    public List<Book> findAllById(List<Long> bookIds) {
        return em.createQuery("select b from Book b where b.id in :bookIds", Book.class)
                .setParameter("bookIds", bookIds)
                .getResultList();
    }

    public List<Book> findById(List<Long> ids) {
        return em.createQuery("select b from Book b where b.id in :ids", Book.class)
                .setParameter("ids", ids)
                .getResultList();
    }
}
