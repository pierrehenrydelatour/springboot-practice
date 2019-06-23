package com.zxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zxy.Utils.DoubleArrayTrie;
import com.zxy.Utils.TrieNode;
import com.zxy.model.Question;
import com.zxy.service.search.QuestionRepository;
import com.zxy.service.likeServices.RedisServiceImpl;
import com.zxy.service.mappers.userMapper;
import com.zxy.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Autowired
    RedisServiceImpl rsi;
    @Autowired
    userMapper us;
    @Autowired
    QuestionRepository qr;
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
    public void testTrie(){
        char c = '中';
        System.out.println((int) c);

        TrieNode root = new TrieNode();
        root.addWords("你好");
        root.addWords("好看");
        root.addWords("明天吃饭");
        System.out.println(root.containsWord("你好"));
        System.out.println(root.containsWord("明天吃饭"));
        System.out.println(root.containsWord("你好好看"));
        System.out.println(root.containsWord("好好"));
        System.out.println(root.containsWord("你好好的"));
    }

    @Test
    public void contextLoads() {
    }


}
