package jpabook.library.service;

import jpabook.library.domain.User;
import jpabook.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public int join(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user) {
        List<User> findUser = userRepository.findByName(User.getName());
        if (!findUser.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<User> findUser() {
        return userRepository.findAll();
    }

    public User findOne(int userId) {
        return userRepository.findOne(userId);
    }
}
