package com.zxy.demo;

import com.zxy.demo.service.RedisServiceImpl;
import com.zxy.demo.service.mappers.userMapper;
import com.zxy.demo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Autowired
    RedisServiceImpl rsi;
    @Autowired
    userMapper us;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void Test(){
        User user = us.findByMail("123@qq.com");
        System.out.println(user.getId());
    }

    @Test
    public void testForRedis(){
        System.out.println(redisTemplate.opsForHash().get("likeCount",6));
        System.out.println(redisTemplate.opsForHash().get("likeCount",7));
    }

    @Test
    public void contextLoads() {
    }

}
