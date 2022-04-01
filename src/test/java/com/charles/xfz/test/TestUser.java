package com.charles.xfz.test;

import com.charles.xfz.domain.entity.User;
import com.charles.xfz.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestUser {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void TestInsert(){
        User user = new User();
        user.setEmail("admin@163.com");
        user.setTelephone("18888888888");
        user.setPassword("111111");
        user.setStaff(true);
        System.out.println(userRepository.save(user));

    }
}
