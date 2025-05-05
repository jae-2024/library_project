package jpabook.library.rental.service;

import jpabook.library.book.domain.Book;
import jpabook.library.model.*;
import jpabook.library.book.repository.BookRepository;
import jpabook.library.rental.domain.Rental;
import jpabook.library.rental.repository.RentalRepository;
import jpabook.library.user.repository.UserRepository;
import jpabook.library.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private static final int EXTEND_DAYS = 7;

    /**
     * 대여일, 반납 예정일, 반납한 날짜 조회
     */
    public Rental findOne(Long rentalId) {
        return rentalRepository.findOne(rentalId);
    }

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    /**
     * 반납 예정일 연장 update (최대 한 번까지 연장할 수 있게 제한하는 코드 짜기)
     */
    public void extendRentalDate(Long id) {
        Rental rental = rentalRepository.findOne(id);

        // 기존 반납일에서 7일 연장
        rental.setDeadLine(rental.getDeadLine().plusDays(EXTEND_DAYS));
    }

    @Transactional
    public void createRental(Long userId, List<Long> bookIds) {
        User user = userRepository.findOne(userId);
        List<Book> books = bookRepository.findById(bookIds);

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setRentalDate(LocalDateTime.now());
        rental.setStatus(RentalStatus.LOAN);

        for (Book book : books) {
            rental.addRentalBook(book); // 연관관계 메서드 설정
            book.removeStock(1); // 재고 감소
        }

        rentalRepository.save(rental);
    }

    public boolean checkBookStatus(Long userId, Long bookId) {
        // 해당 사용자가 이미 대여한 책 목록을 가져옵니다.
        List<Rental> rentals = rentalRepository.findByUserId(userId);

        // 대여 중인 책 중에서 해당 책이 있는지 확인
        for (Rental rental : rentals) {
            if (rental.getRentalBooks().stream().anyMatch(rentalBook -> rentalBook.getBook().getId().equals(bookId))) {
                return true; // 이미 대여 중인 경우
            }
        }

        // 대여 중이지 않은 경우
        return false;
    }

    @Transactional
    public void deleteRental(Long rentalId) {
        rentalRepository.deleteById(rentalId);
    }

    @Transactional
    public void returnRental(Long id) {
        rentalRepository.returnById(id);
    }

}
