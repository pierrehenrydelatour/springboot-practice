package com.zxy.demo.controller.QandA;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxy.demo.annotation.LoginRequired;
import com.zxy.demo.service.mappers.questionMapper;
import com.zxy.demo.service.mappers.userMapper;
import com.zxy.demo.model.Question;
import com.zxy.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionService {

    @Autowired
    questionMapper qm;
    @Autowired
    userMapper um;

    @RequestMapping("/question/posequestion")
    @ResponseBody
    @LoginRequired
    public JSONObject poseAQuestion(@RequestParam String title, @RequestParam String text, HttpServletRequest req){
        JSONObject jsonObject = new JSONObject();
        User user = (User) req.getAttribute("currentUser");
        qm.poseQuestion(title,text,user.getId());
        Question q = qm.getQuestionByCentent(user.getId(),title,text);
        jsonObject.put("id",q.getId());
        jsonObject.put("title",title);
        jsonObject.put("content",text);
        jsonObject.put("author",user.getEmail());
        return jsonObject;
    }

    @RequestMapping("/question/myquestions")
    @ResponseBody
    @LoginRequired
    public JSONObject getAllQuestions(HttpServletRequest req){
        User user = (User) req.getAttribute("currentUser");
        List<Question> questionsList = qm.getQuestionByUser(user.getId());
        JSONObject jsonObject = new JSONObject();
        if(questionsList != null){
            jsonObject.put("author",user.getEmail());
            JSONArray ja = new JSONArray();
            for(Question q : questionsList){
                JSONObject jo = new JSONObject();
                jo.put("id",q.getId());
                jo.put("title",q.getTitle());
                jo.put("content",q.getText());
                ja.add(jo);
            }
            jsonObject.put("questions",ja);
        }
        return jsonObject;
    }

    @RequestMapping("/question/userquestions")
    @ResponseBody
    public JSONObject getQuestions(@RequestParam int userId){
        User user = um.findById(userId);
        List<Question> questionsList = qm.getQuestionByUser(userId);
        JSONObject jsonObject = new JSONObject();
        if(questionsList != null){
            jsonObject.put("author",user.getEmail());
            JSONArray ja = new JSONArray();
            for(Question q : questionsList){
                JSONObject jo = new JSONObject();
                jo.put("id",q.getId());
                jo.put("title",q.getTitle());
                jo.put("content",q.getText());
                ja.add(jo);
            }
            jsonObject.put("questions",ja);
        }
        return jsonObject;
    }

    @RequestMapping("/question/deletequestion")
    public void deleteQuestion(int qId){
        qm.deleteQuestion(qId);
    }
}
