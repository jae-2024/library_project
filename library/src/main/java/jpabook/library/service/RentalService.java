package jpabook.library.service;

import jakarta.persistence.EntityManager;
import jpabook.library.domain.Rental;
import jpabook.library.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private static final int EXTEND_DAYS = 7;

    /**
     * 대여일, 반납 예정일, 반납한 날짜 조회
     */
    public Rental findOne(int rentalId) {
        return rentalRepository.findOne(rentalId);
    }

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    /**
     * 반납 예정일 연장 update (최대 한 번까지 연장할 수 있게 제한하는 코드 짜기)
     */
    public void extendRentalDate(int id) {
        Rental rental = rentalRepository.findOne(id);

        // 기존 반납일에서 7일 연장
        rental.setDeadLine(rental.getDeadLine().plusDays(EXTEND_DAYS));
    }
}
