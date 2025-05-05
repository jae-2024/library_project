package jpabook.library.rental.controller;

import jpabook.library.rental.dto.RentalForm;
import jpabook.library.book.domain.Book;
import jpabook.library.user.domain.User;
import jpabook.library.book.service.BookService;
import jpabook.library.rental.service.RentalService;
import jpabook.library.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final UserService userService;
    private final BookService bookService;

    @GetMapping
    public String listRentals(Model model) {
        model.addAttribute("rentals", rentalService.findAll());
        return "rentals/rentalList";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("users", userService.findUser());
        model.addAttribute("books", bookService.findBook());
        model.addAttribute("rentalForm", new RentalForm());
        return "rentals/createRentalForm";
    }

    @PostMapping("/new")
    public String createRental(@ModelAttribute RentalForm rentalForm, Model model) {
        // 대여할 책과 회원을 가져옵니다.
        Long userId = rentalForm.getUserId();
        List<Long> bookIds = rentalForm.getBookIds();

        if (userId == null || bookIds == null || bookIds.isEmpty()) {
            model.addAttribute("errorMessage", "회원 또는 도서를 선택하세요.");
            return "rentals/createRentalForm"; // 에러 메시지가 포함된 폼 페이지로 리턴
        }

        // 대여할 책과 회원을 가져오기
        User user = userService.findOne(userId); // 사용자 찾기
        List<Book> books = bookService.findByName(bookIds); // 책 목록 찾기

        // 재고가 없거나 이미 대여 중인 책을 확인
        StringBuilder errorMessages = new StringBuilder();
        boolean isValid = true;

        for (Book book : books) {
            if (book.getStock() <= 0) {
                errorMessages.append("책 '").append(book.getTitle()).append("'의 재고가 부족합니다. ");
                isValid = false;
            }

            // 이미 빌린 상태인 책 체크 'rentalService.checkBookStatus'는 대여 상태를 체크하는 서비스 메서드
            if (rentalService.checkBookStatus(userId, book.getId())) {
                errorMessages.append("책 '").append(book.getTitle()).append("'은 이미 대여 중입니다. ");
                isValid = false;
            }
        }

        // 책이 유효하지 않으면 에러 메시지 출력
        if (!isValid) {
            model.addAttribute("errorMessage", errorMessages.toString());
            model.addAttribute("users", userService.findUser());
            model.addAttribute("books", bookService.findBook());
            return "rentals/createRentalForm"; // 다시 선택하도록 대여 신청 폼으로 돌아가기
        }

        // 책이 모두 유효하면 대여 진행
        rentalService.createRental(userId, bookIds);

        return "redirect:/rentals";
    }

    @PostMapping("/delete/{id}")
    public String deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
        return "redirect:/rentals";
    }

    @PostMapping("/return/{id}")
    public String returnRental(@PathVariable Long id) {
        rentalService.returnRental(id);
        return "redirect:/rentals";
    }
}
