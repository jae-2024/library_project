package jpabook.library.service;

import jpabook.library.domain.Book;
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
     * 책 등록 (관리자만 책을 등록할 수 있게 수정하기)
     */
    @Transactional
    public int join(Book book) {
        validateDuplicateBook(book);
        bookRepository.save(book);
        return book.getId();
    }

    private void validateDuplicateBook(Book book) {
        List<Book> findBook = bookRepository.findByName(book.getTitle());
        if (!findBook.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 책 조회
     */
    public List<Book> findBook() {
        return bookRepository.findAll();
    }

    public Book findOne(int bookId) {
        return bookRepository.findOne(bookId);
    }
}
