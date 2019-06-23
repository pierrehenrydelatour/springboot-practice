package com.zxy.config;

import com.zxy.service.MQ.MessageReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisMQConfig {

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter1,
                                            MessageListenerAdapter listenerAdapter2){

        RedisMessageListenerContainer container = new
                RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter1,new PatternTopic("signup"));
        container.addMessageListener(listenerAdapter2,new PatternTopic("poseq"));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter1(MessageReceiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage1");
    }

    @Bean
    MessageListenerAdapter listenerAdapter2(MessageReceiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage2");
    }
}
