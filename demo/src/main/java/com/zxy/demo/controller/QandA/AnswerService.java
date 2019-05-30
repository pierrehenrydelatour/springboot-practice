package com.zxy.demo.controller.QandA;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxy.demo.annotation.LoginRequired;
import com.zxy.demo.model.Answer;
import com.zxy.demo.model.Question;
import com.zxy.demo.service.mappers.answerMapper;
import com.zxy.demo.service.mappers.questionMapper;
import com.zxy.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AnswerService {

    @Autowired
    answerMapper am;

    @Autowired
    questionMapper qm;

    @RequestMapping("/question/answer")
    @LoginRequired
    @ResponseBody
    public JSONObject answerAQuestion(HttpServletRequest req, @RequestParam int qId, @RequestParam String text){
        JSONObject jsonObject = new JSONObject();
        User user = (User) req.getAttribute("currentUser");
        am.answer(qId,text,user.getId());
        jsonObject.put("question",qId);
        jsonObject.put("answerer",user.getEmail());
        jsonObject.put("answer",text);
        return jsonObject;
    }

    @RequestMapping("/answers")
    @ResponseBody
    public JSONObject getAnswersByQId(@RequestParam int qId){
        JSONObject jsonObject = new JSONObject();
        List<Answer> list = am.getAnswersByQuestion(qId);
        Question q = qm.getQuestionByQId(qId);
        if(list != null){
            JSONArray ja = new JSONArray();
            for(Answer answer : list){
                JSONObject jo = new JSONObject();
                jo.put("id",answer.getaId());
                jo.put("author",answer.getUserId());
                jo.put("content",answer.getText());
                jo.put("likes",answer.getLike());
                ja.add(jo);
            }
            jsonObject.put("answers",ja);
        }
        return jsonObject;
    }
}
