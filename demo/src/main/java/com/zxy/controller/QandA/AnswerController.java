package com.zxy.controller.QandA;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxy.Utils.FilterUtil;
import com.zxy.annotation.LoginRequired;
import com.zxy.model.Answer;
import com.zxy.model.Question;
import com.zxy.service.mappers.answerMapper;
import com.zxy.service.mappers.questionMapper;
import com.zxy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AnswerController {

    @Autowired
    answerMapper am;

    @Autowired
    questionMapper qm;

    @RequestMapping(value="/addAnswer",method = RequestMethod.POST)
    @LoginRequired
    public String answerAQuestion(HttpServletRequest req, @RequestParam("content") String text,
                                  @RequestParam("questionId") int qId){

        System.out.println("questionId is:"+qId);
        //和谐
        text = FilterUtil.filter(text);

        User user = (User) req.getAttribute("currentUser");
        am.answer(qId,text,user.getId());

        return "redirect:/question/"+qId;
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
