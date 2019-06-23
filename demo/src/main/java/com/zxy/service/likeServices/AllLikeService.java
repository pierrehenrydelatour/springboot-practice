package com.zxy.service.likeServices;

import com.zxy.model.Answer;
import com.zxy.model.Like;
import com.zxy.model.User;
import com.zxy.service.mappers.answerMapper;
import com.zxy.service.mappers.likesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="als")
public class AllLikeService {
    @Autowired
    LikedServiceImpl lsi;
    @Autowired
    RedisServiceImpl rsi;
    @Autowired
    likesMapper lm;
    @Autowired
    answerMapper am;

    public Integer Like(int aId, User user,int value){
        int userId = user.getId();
        if(rsi.getLikeCount(aId) == null){
            Answer answer = am.getAnswerByAId(aId);
            rsi.putLikeCount(aId,answer.getLike());
        }
        if(rsi.findLikeByUserIdAndAId(userId,aId) == null){
            Like l = lsi.getLikeByUserIdAndAId(userId,aId);
            rsi.saveLiked2Redis(userId,aId,l == null?0 : l.getValue());
        }
        if(rsi.findLikeByUserIdAndAId(userId,aId) == 0 && value == 1){
            rsi.saveLiked2Redis(userId,aId,1);
            rsi.incrementLikedCount(aId);

        }else if(rsi.findLikeByUserIdAndAId(userId,aId) == 1 && value == -1){
            rsi.saveLiked2Redis(userId,aId,0);
            rsi.decrementLikedCount(aId);

        }
        return  (Integer) rsi.getLikeCount(aId);
    }

    public Integer getLikeStatus(int aId, User user){
        int userId = user.getId();
        if(rsi.findLikeByUserIdAndAId(userId,aId) == null){
            Like l = lsi.getLikeByUserIdAndAId(userId,aId);
            rsi.saveLiked2Redis(userId,aId,l == null?0 : l.getValue());
            if(l==null){
                return 1;
            }else{
                return 0;
            }
        }else{
            Integer like = rsi.findLikeByUserIdAndAId(userId,aId);
            if(like==null){
                return 1;
            }else{
                return 0;
            }
        }
    }
    public Integer getLikeCount(int aId){
        if(rsi.getLikeCount(aId) == null){
            Answer answer = am.getAnswerByAId(aId);
            rsi.putLikeCount(aId,answer.getLike());
        }
        return (Integer) rsi.getLikeCount(aId);
    }
}
