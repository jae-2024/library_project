package jpabook.library.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.library.domain.Author;
import jpabook.library.domain.Rental;
import jpabook.library.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorRepository {

    private final EntityManager em;

    public void save(Author author) {
        if (author.getId() == null) {
            em.persist(author);  // 새로운 엔티티는 persist로 저장
        } else {
            em.merge(author);  // 기존 엔티티는 merge로 갱신
        }
    }

    public Author findOne(Long id) {
        return em.find(Author.class, id);
    }

    public List<Author> findAll() {
        return em.createQuery("select m from Author m", Author.class)
                .getResultList();
    }

    public List<Author> findByName(String name) {
        return em.createQuery("select distinct a from Author a left join fetch a.books " +
                        "where a.name = :name", Author.class)
                .setParameter("name", name)
                .getResultList();
    }
}
