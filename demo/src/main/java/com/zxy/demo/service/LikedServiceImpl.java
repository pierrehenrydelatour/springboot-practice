package com.zxy.demo.service;

import com.zxy.demo.model.Like;
import com.zxy.demo.model.LikeCount;
import com.zxy.demo.service.mappers.answerMapper;
import com.zxy.demo.service.mappers.likesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component(value = "lsi")
public class LikedServiceImpl implements LinkedService {
    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    likesMapper lm;

    @Autowired
    RedisServiceImpl rsi;

    @Autowired
    answerMapper am;

    @Override
    public Like save(Like like) {
        log.info("enter lsi.save");
        Like l = lm.getLikeByAidAndUserId(like.getaId(),like.getUserId());
        if(l == null && like.getValue() == 1) {
            log.info("not in likes");
            lm.save(like);
        }
        else if(l != null){
            log.info("find in likes, l :"+l.getValue()+" like:"+like.getValue());
            if(l.getValue() == 1 && like.getValue() == 0){
                log.info("pre delete");
                lm.delete(l);
            }else if(l.getValue() == 0 && like.getValue() == 1) {
                lm.upDateLike(like.getUserId(),like.getaId(),like.getValue());
            }
        }
        return like;
    }

    @Override
    public List<Like> saveAll(List<Like> likes) {
        if(likes != null && likes.size()>0){
            for(Like l : likes){
                this.save(l);
            }
        }
        return likes;
    }

    @Override
    public List<Like> getLikeListByAId(int aId) {
        return lm.getLikeListByAId(aId);
    }

    @Override
    public List<Like> getLikeListByUserId(int userId) {
        return lm.getLikeListByUserId(userId);
    }


    public Like getLikeByUserIdAndAId(int userId, int aId){
        return lm.getLikeByAidAndUserId(aId,userId);
    }

    @Override
    public void transLikesFromRedis2DB() {
        List<Like> list = rsi.getLikedDataFromRedis();
        this.saveAll(list);
    }

    @Override
    public void transLikeCountFromRedis2DB() {
        List<LikeCount> list = rsi.getLikedCountFromRedis();
        if(list != null && list.size() > 0){
            for(LikeCount lc : list){
                am.updateOneAnswer(lc.getA_id(),lc.getCnt());
            }
        }
    }
}
