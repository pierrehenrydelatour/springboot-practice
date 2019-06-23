package com.zxy.service.likeServices;

import com.zxy.Utils.getLikeKeyUtil;
import com.zxy.model.Like;
import com.zxy.model.LikeCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component(value = "rsi")
public class RedisServiceImpl implements RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    public Integer findLikeByUserIdAndAId(int userId,int answerId){
        String key = getLikeKeyUtil.getKey(answerId,userId);
        return redisTemplate.opsForHash().get("likes",key)==null? null : (Integer) redisTemplate.opsForHash().get("likes",key);
    }

    @Override
    public void saveLiked2Redis(int likedUserId, int answerId,int value) {
        String key = getLikeKeyUtil.getKey(answerId,likedUserId);
        redisTemplate.opsForHash().put("likes",key,value);
    }

    @Override
    public void unlikeFromRedis(int likedUserId, int answerId,int value) {
        String key = getLikeKeyUtil.getKey(answerId,likedUserId);
        redisTemplate.opsForHash().put("likes",key,value);
    }

    @Override
    public void deleteLikedFromRedis(int likedUserId, int answerId) {
        String key = getLikeKeyUtil.getKey(answerId,likedUserId);
        redisTemplate.opsForHash().delete("likes",key);
    }

    @Override
    public void incrementLikedCount(int answerId) {
        redisTemplate.opsForHash().putIfAbsent("likeCount",answerId,0);
        redisTemplate.opsForHash().increment("likeCount",answerId,1);
    }

    @Override
    public void decrementLikedCount(int answerId) {
        redisTemplate.opsForHash().putIfAbsent("likeCount",answerId,0);
        redisTemplate.opsForHash().increment("likeCount",answerId,-1);
    }

    public Object getLikeCount(int aId){
        return redisTemplate.opsForHash().get("likeCount",aId);
    }

    public void putLikeCount(int aId, int value){
        redisTemplate.opsForHash().put("likeCount",aId,value);
    }

    @Override
    public List<Like> getLikedDataFromRedis() {
       Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan("likes", ScanOptions.NONE);
       List<Like> list = new LinkedList<>();
       while(cursor.hasNext()){
           Map.Entry<Object,Object> entry = cursor.next();
           String key = (String) entry.getKey();
           String[] parts = key.split("::");
           int aId = Integer.parseInt(parts[0]);
           int userId = Integer.parseInt(parts[1]);
           int value = (int) entry.getValue();
           Like like = new Like(userId,aId,value);
           list.add(like);
           redisTemplate.opsForHash().delete("likes",key);
       }
        return list;
    }

    @Override
    public List<LikeCount> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan("likeCount", ScanOptions.NONE);
        List<LikeCount> list = new LinkedList<>();
        while(cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            int aId = (int) entry.getKey();
            list.add(new LikeCount(aId,(int) entry.getValue()));
            redisTemplate.opsForHash().delete("likeCount",aId);
        }
        return list;
    }
}