package jpabook.library.rental.repository;

import jakarta.persistence.EntityManager;
import jpabook.library.book.domain.Book;
import jpabook.library.model.*;
import jpabook.library.rental.domain.Rental;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RentalRepository {

    private final EntityManager em;

    @Transactional
    public void save(Rental rental) {
        em.persist(rental);
    }

    public Rental findOne(Long id) {
        return em.find(Rental.class, id);
    }

    public List<Rental> findAll() {
        return em.createQuery("select m from Rental m", Rental.class)
                .getResultList();
    }

    public List<Rental> findByUserId(Long userId) {
        return em.createQuery("select r from Rental r where r.user.id = :userId", Rental.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public void deleteById(Long rentalId) {
        Rental rental = em.find(Rental.class, rentalId);

        if (rental != null) {
            em.remove(rental);  // 엔티티 삭제
        } else {
            throw new IllegalArgumentException("해당 대여 기록이 존재하지 않습니다.");
        }
    }

    public void returnById(Long returnId) {
        Rental rental = em.find(Rental.class, returnId);

        if (rental != null) {
            // 이미 반납된 경우 예외 처리
            if (rental.getStatus() == RentalStatus.RETURN) {
                throw new IllegalStateException("이미 반납된 대여입니다.");
            }

            // 상태 변경
            rental.setStatus(RentalStatus.RETURN);

            // 책 재고 복구
            for (RentalBook rentalBook : rental.getRentalBooks()) {
                Book book = rentalBook.getBook();
                book.addStock(1); // Book 엔티티에 정의된 메서드
            }

            // em.merge(rental); // 생략 가능: 트랜잭션 내에서 자동 감지되어 반영됨
        } else {
            throw new IllegalArgumentException("해당 대여 기록이 존재하지 않습니다.");
        }
    }
}
