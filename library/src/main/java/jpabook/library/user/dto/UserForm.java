package jpabook.library.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jpabook.library.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserForm {
    @NotEmpty(message = "회원 이름은 필수 입니다")
    private String name;

    private String email;
    private String password;
    private String phone;
    private LocalDateTime createdAt;
    private Role role;
}