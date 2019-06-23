package com.zxy.service.MQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendMessageService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void sendMessage(String channel,String message){
        redisTemplate.convertAndSend(channel,message);
    }
}
