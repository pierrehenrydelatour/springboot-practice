package com.zxy.controller.likes;

import com.alibaba.fastjson.JSONObject;
import com.zxy.annotation.LoginRequired;
import com.zxy.model.Answer;
import com.zxy.model.Like;
import com.zxy.model.User;
import com.zxy.service.likeServices.AllLikeService;
import com.zxy.service.likeServices.LikedServiceImpl;
import com.zxy.service.likeServices.RedisServiceImpl;
import com.zxy.service.mappers.answerMapper;
import com.zxy.service.mappers.likesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
public class LikeController {
    @Autowired
    AllLikeService als;
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
        return als.Like(aId,user,value);
    }

    @RequestMapping(value="/like",method = RequestMethod.POST)
    @LoginRequired
    @ResponseBody
    public String doLike(@RequestParam("commentId") int aId,HttpServletRequest req){
        User user = (User) req.getAttribute("currentUser");
        JSONObject jsonObject = new JSONObject();
        if(user == null){
            jsonObject.put("code",999);
            return jsonObject.toJSONString();
        }
        als.Like(aId,user,1);
        jsonObject.put("msg",String.valueOf(als.getLikeCount(aId)));
        jsonObject.put("code",0);
        return jsonObject.toJSONString();
    }

    @RequestMapping(value="/dislike",method = RequestMethod.POST)
    @LoginRequired
    @ResponseBody
    public String dodisLike(@RequestParam("commentId") int aId,HttpServletRequest req){
        User user = (User) req.getAttribute("currentUser");
        JSONObject jsonObject = new JSONObject();
        if(user == null){
            jsonObject.put("code",999);
            return jsonObject.toJSONString();
        }
        als.Like(aId,user,-1);
        jsonObject.put("msg",String.valueOf(als.getLikeCount(aId)));
        jsonObject.put("code",0);
        return jsonObject.toJSONString();
    }

}
