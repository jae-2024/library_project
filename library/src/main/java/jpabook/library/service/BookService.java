package jpabook.library.service;

import jpabook.library.domain.Book;
import jpabook.library.domain.Role;
import jpabook.library.domain.User;
import jpabook.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    /**
     * 책 등록 (관리자만 책을 등록 가능)
     */
    @Transactional
    public Long join(Book book) {
//        if (user.getRole() != Role.ADMIN) {
//            throw new IllegalStateException("관리자만 책을 등록할 수 있습니다.");
//        }
        validateDuplicateBook(book);
        bookRepository.save(book);
        return book.getId();
    }

    private void validateDuplicateBook(Book book) {
        List<Book> findBook = bookRepository.findByName(book.getTitle());
        if (!findBook.isEmpty()) {
            System.out.println("입력 제목: '" + book.getTitle() + "'");
            throw new IllegalStateException("이미 존재하는 책입니다.");
        }
    }

    /**
     * 책 조회
     */
    public List<Book> findBook() {
        return bookRepository.findAll();
    }

    public Book findOne(Long bookId) {
        return bookRepository.findOne(bookId);
    }

    @Transactional
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findByName(List<Long> bookIds) {
        return bookRepository.findAllById(bookIds);
    }
}
