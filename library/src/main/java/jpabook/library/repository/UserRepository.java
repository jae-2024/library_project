package jpabook.library.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.library.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    @PersistenceContext
    private final EntityManager em;

    private void save(User user) {
        em.persist(user);
    }

    private User findOne(int id) {
        return em.find(User.class, id);
    }

    private List<User> findAll() {
        return em.createQuery("select m from User m", User.class)
                .getResultList();
    }

    public List<User> findByName(String name) {
        return em.createQuery("select m from User m where m.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }
}
