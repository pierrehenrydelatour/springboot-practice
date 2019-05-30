package com.zxy.demo.service;

import com.zxy.demo.model.Like;
import com.zxy.demo.model.LikeCount;

import java.util.List;

public interface RedisService {
    /**
     * 点赞。状态为1
     * @param likedUserId
     * @param answerId
     */
    void saveLiked2Redis(int likedUserId, int answerId,int value);

    /**
     * 取消点赞。将状态改变为0
     * @param likedUserId
     * @param answerId
     */
    void unlikeFromRedis(int likedUserId, int answerId, int value);

    /**
     * 从Redis中删除一条点赞数据
     * @param likedUserId
     * @param answerId
     */
    void deleteLikedFromRedis(int likedUserId, int answerId);

    /**
     * 该用户的点赞数加1
     * @param answerId
     */
    void incrementLikedCount(int answerId);

    /**
     * 该用户的点赞数减1
     * @param answerId
     */
    void decrementLikedCount(int answerId);

    /**
     * 获取Redis中存储的所有点赞数据
     * @return
     */
    List<Like> getLikedDataFromRedis();

    /**
     * 获取Redis中存储的所有点赞数量
     * @return
     */
    List<LikeCount> getLikedCountFromRedis();

}
