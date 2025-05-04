package jpabook.library.Controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jpabook.library.domain.Author;
import jpabook.library.domain.Book;
import jpabook.library.domain.Role;
import jpabook.library.domain.User;
import jpabook.library.repository.AuthorRepository;
import jpabook.library.service.AuthorService;
import jpabook.library.service.BookService;
import jpabook.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

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
//        book.setAuthor(author);

        bookService.join(book); // 책 저장

        return "redirect:/";
    }

    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
