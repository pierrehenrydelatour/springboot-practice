package com.zxy.service.likeServices;

import com.zxy.model.Like;

import java.util.List;

public interface LinkedService {

    Like save(Like like);

    List<Like> saveAll(List<Like> likes);

    List<Like> getLikeListByAId(int aId);

    List<Like> getLikeListByUserId(int userId);

    void transLikesFromRedis2DB();

    void transLikeCountFromRedis2DB();
}
