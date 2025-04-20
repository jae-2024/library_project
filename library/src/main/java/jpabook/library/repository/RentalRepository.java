package jpabook.library.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.library.domain.Rental;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RentalRepository {

    @PersistenceContext
    private final EntityManager em;

    private void save(Rental rental) {
        em.persist(rental);
    }

    private Rental findOne(int id) {
        return em.find(Rental.class, id);
    }

    private List<Rental> findAll() {
        return em.createQuery("select m from Rental m", Rental.class)
                .getResultList();
    }
}
