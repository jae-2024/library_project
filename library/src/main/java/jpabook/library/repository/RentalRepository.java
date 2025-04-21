package jpabook.library.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.library.domain.Rental;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RentalRepository {

    private final EntityManager em;

    public void save(Rental rental) {
        em.persist(rental);
    }

    public Rental findOne(int id) {
        return em.find(Rental.class, id);
    }

    public List<Rental> findAll() {
        return em.createQuery("select m from Rental m", Rental.class)
                .getResultList();
    }
}
