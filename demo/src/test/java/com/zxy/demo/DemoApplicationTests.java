package com.zxy.demo;

import com.zxy.demo.mappers.UserService;
import com.zxy.demo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Autowired
    UserService us;
    @Test
    public void Test(){
        User user = us.findByMail("123@qq.com");
        System.out.println(user.getId());
    }
    @Test
    public void contextLoads() {
    }

}
