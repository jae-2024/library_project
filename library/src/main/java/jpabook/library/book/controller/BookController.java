package jpabook.library.book.controller;

import jakarta.validation.Valid;
import jpabook.library.book.dto.BookForm;
import jpabook.library.author.domain.Author;
import jpabook.library.book.domain.Book;
import jpabook.library.author.repository.AuthorRepository;
import jpabook.library.book.repository.BookRepository;
import jpabook.library.author.service.AuthorService;
import jpabook.library.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final AuthorRepository authorRepository;

    @GetMapping("/books")
    public String bookList(Model model) {
        model.addAttribute("books", bookService.findBook());
        return "books/bookList";
    }

    @GetMapping("/books/new")
    public String bookCreateForm(Model model) {
        model.addAttribute("bookForm", new BookForm());
        return "books/createBookForm";
    }

    @PostMapping("/books/new")
    public String createBook(@Valid BookForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "books/createBookForm";
        }

        LocalDateTime outblishedAt = LocalDateTime.now();

        // 2. 책 정보 설정
        Book book = new Book();
        book.setTitle(form.getTitle());
        book.setIsbn(form.getIsbn());
        book.setStock(form.getStock());
        book.setPublisher(form.getPublisher());
        book.setOutblishedAt(outblishedAt);

        Author author = new Author();
        author.setName(form.getName());
        authorRepository.save(author);

        book.setAuthor(author);

        bookService.join(book); // 책 저장

        return "redirect:/";
    }

    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
