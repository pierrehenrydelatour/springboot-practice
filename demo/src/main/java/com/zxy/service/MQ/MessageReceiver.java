package com.zxy.service.MQ;

import com.alibaba.fastjson.JSONObject;
import com.zxy.model.Question;
import com.zxy.model.User;
import com.zxy.service.mappers.questionMapper;
import com.zxy.service.mappers.userMapper;
import com.zxy.service.search.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {
    @Autowired
    userMapper us;
    @Autowired
    questionMapper qm;
    @Autowired
    QuestionRepository qr;

    //message1 -> signup
    public void receiveMessage1(String message){
        System.out.println("receive message" + message);
        JSONObject user = JSONObject.parseObject(message);
        String email =(String) user.get("email");
        String password = (String) user.get("password");
        String url = (String) user.get("url");
        System.out.println("ready to write to sql-login");
        us.Insert(email,password,url);
    }

    //message2 -> poseaquestion
    public void receiveMessage2(String message){
        JSONObject json = JSONObject.parseObject(message);
        String title =(String) json.get("title");
        String text = (String) json.get("text");
        int id =(int) json.get("user");
        System.out.println("ready to write sql - question");
        qm.poseQuestion(title,text,id);
        Question q = qm.getQuestionByCentent(id,title,text);
        qr.save(q);
    }
}
