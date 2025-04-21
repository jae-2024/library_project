package jpabook.library.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.library.domain.Author;
import jpabook.library.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorRepository {

    private final EntityManager em;

    public Author findOne(int id) {
        return em.find(Author.class, id);
    }

    public List<Author> findAll() {
        return em.createQuery("select m from Member m", Author.class)
                .getResultList();
    }

    public List<Author> findByName(String name) {
        return em.createQuery("select distinct a from Author a left join fetch a.books " +
                        "where a.name = :name", Author.class)
                .setParameter("name", name)
                .getResultList();
    }
}
