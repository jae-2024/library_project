package jpabook.library.service;

import jpabook.library.domain.Author;
import jpabook.library.domain.Rental;
import jpabook.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    /**
     * 작가 조회
     */
    public List<Author> findAuthor() {
        return authorRepository.findAll();
    }

    public Author findOne(Long authorId) {
        return authorRepository.findOne(authorId);
    }

    public List<Author> findByName(String name) {
        return authorRepository.findByName(name);
    }

    public void saveAuthor(Author author) {
        authorRepository.save(author); // save 호출
    }
}
