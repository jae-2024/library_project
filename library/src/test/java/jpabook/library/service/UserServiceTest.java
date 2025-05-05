package jpabook.library.service;

import jakarta.persistence.EntityManager;
import jpabook.library.user.domain.User;
import jpabook.library.user.repository.UserRepository;
import jpabook.library.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired UserRepository userRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given
        User user = new User();
        user.setName("kim");

        //when
        int savedId = userService.join(user);

        //then
        assertEquals(user, userRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        User user1 = new User();
        user1.setName("kim");

        User user2 = new User();
        user2.setName("kim");

        //when
        userService.join(user1);
        userService.join(user2);

        //then
        fail("예외가 발생해야함");
    }
}