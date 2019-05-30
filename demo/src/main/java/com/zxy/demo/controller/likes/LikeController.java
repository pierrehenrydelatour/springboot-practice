package com.zxy.demo.controller.likes;

import com.zxy.demo.annotation.LoginRequired;
import com.zxy.demo.model.Answer;
import com.zxy.demo.model.Like;
import com.zxy.demo.model.LikeCount;
import com.zxy.demo.model.User;
import com.zxy.demo.service.LikedServiceImpl;
import com.zxy.demo.service.RedisServiceImpl;
import com.zxy.demo.service.mappers.answerMapper;
import com.zxy.demo.service.mappers.likesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
public class LikeController {
    @Autowired
    answerMapper am;
    @Autowired
    LikedServiceImpl lsi;
    @Autowired
    RedisServiceImpl rsi;
    @Autowired
    likesMapper lm;
    Logger log = LoggerFactory.getLogger(getClass());
    @RequestMapping("/answers/like")
    @LoginRequired
    @ResponseBody
    public Integer like(HttpServletRequest req, @RequestParam int aId, @RequestParam int value){
        User user = (User) req.getAttribute("currentUser");
        int userId = user.getId();
        log.info("find user");
        if(rsi.getLikeCount(aId) == null){
            Answer answer = am.getAnswerByAId(aId);
            rsi.putLikeCount(aId,answer.getLike());
            log.info("doesn't find like count in redis"+answer.getLike());
        }

        if(rsi.findLikeByUserIdAndAId(userId,aId) == null){
            Like l = lsi.getLikeByUserIdAndAId(userId,aId);
            rsi.saveLiked2Redis(userId,aId,l == null?0 : l.getValue());
            log.info("doesn't find like in redis" + (l==null? 0 : l.getValue()));
        }

        if(rsi.findLikeByUserIdAndAId(userId,aId) == 0 && value == 1){
            rsi.saveLiked2Redis(userId,aId,1);
            rsi.incrementLikedCount(aId);
            log.info("like");
        }else if(rsi.findLikeByUserIdAndAId(userId,aId) == 1 && value == -1){
            rsi.saveLiked2Redis(userId,aId,0);
            rsi.decrementLikedCount(aId);
            log.info("unlike");
        }
        return  (Integer) rsi.getLikeCount(aId);
    }
}
