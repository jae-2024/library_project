package jpabook.library.service;

import jpabook.library.domain.User;
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

    @Autowired UserService userService;
    @Autowired UserService userRepository;

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

    @Test
    public void 중복_회원_예외() throws Exception {
        //given

        //when

        //then

    }
}