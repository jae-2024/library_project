package jpabook.library.user.controller;

import jpabook.library.user.dto.UserForm;
import jpabook.library.user.domain.User;
import jpabook.library.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

import static jpabook.library.model.Role.USER;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 회원 목록 보기
    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.findUser();
        model.addAttribute("users", users);
        return "users/userList";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/createUserForm";
    }

    @PostMapping("/new")
    public String create(@Valid UserForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "users/createUserForm";
        }
        LocalDateTime createdAt = LocalDateTime.now();

        User user = new User();
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setPhone(form.getPhone());
        user.setCreatedAt(createdAt);
        user.setRole(USER);

        userService.join(user);

        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
