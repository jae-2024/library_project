package jpabook.library.Controller;

import jakarta.validation.constraints.NotEmpty;
import jpabook.library.domain.Author;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookForm {

    @NotEmpty(message = "책 제목은 필수 입니다")
    private String title;

    private String isbn;
    private int stock;
    private String publisher;
    private LocalDateTime outblishedAt;
    private String name;
}
