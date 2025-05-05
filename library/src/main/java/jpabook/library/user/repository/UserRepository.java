package jpabook.library.user.repository;

import jakarta.persistence.EntityManager;
import jpabook.library.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    @Transactional
    public void save(User user) {
        em.persist(user);
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select m from User m", User.class)
                .getResultList();
    }

    public List<User> findByName(String name) {
        return em.createQuery("select m from User m where m.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

    public void deleteById(Long id) {
        // 먼저 해당 ID를 가진 사용자 찾기
        User user = em.find(User.class, id);

        // 사용자가 존재하면 삭제
        if (user != null) {
            em.remove(user);
        } else {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + id);
        }
    }
}
