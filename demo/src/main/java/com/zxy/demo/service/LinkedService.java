package com.zxy.demo.service;

import com.zxy.demo.model.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LinkedService {

    Like save(Like like);

    List<Like> saveAll(List<Like> likes);

    List<Like> getLikeListByAId(int aId);

    List<Like> getLikeListByUserId(int userId);

    void transLikesFromRedis2DB();

    void transLikeCountFromRedis2DB();
}
