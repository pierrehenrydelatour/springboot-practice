package com.zxy.controller.QandA;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxy.Utils.FilterUtil;
import com.zxy.annotation.LoginRequired;
import com.zxy.model.Answer;
import com.zxy.model.ViewObject;
import com.zxy.service.MQ.SendMessageService;
import com.zxy.service.likeServices.AllLikeService;
import com.zxy.service.mappers.answerMapper;
import com.zxy.service.mappers.questionMapper;
import com.zxy.service.mappers.userMapper;
import com.zxy.model.Question;
import com.zxy.model.User;
import com.zxy.service.search.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    questionMapper qm;
    @Autowired
    userMapper um;
    @Autowired
    answerMapper am;
    @Autowired
    AllLikeService als;
    @Autowired
    QuestionRepository qr;
    @Autowired
    SendMessageService sms;

    @RequestMapping(value = "/question/posequestion",method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public String poseAQuestion(@RequestParam("title") String title, @RequestParam("content") String text, HttpServletRequest req){
        System.out.println("reach here");
        JSONObject jsonObject = new JSONObject();
        User user = (User) req.getAttribute("currentUser");
        if(user == null){
            jsonObject.put("code",999);
            return jsonObject.toJSONString();
        }
        //过滤内容
        text = FilterUtil.filter(text);
        title = FilterUtil.filter(title);
        //sms
        JSONObject mes = new JSONObject();
        mes.put("text",text);
        mes.put("title",title);
        mes.put("user",user.getId());
        sms.sendMessage("poseq",mes.toJSONString());

        /*qm.poseQuestion(title,text,user.getId());
        Question q = qm.getQuestionByCentent(user.getId(),title,text);
        qr.save(q);*/

        return jsonObject.toJSONString();
    }

    @RequestMapping("/question/myquestions")
    @LoginRequired
    public String getAllQuestions(HttpServletRequest req, Model model){
        User user = (User) req.getAttribute("currentUser");
        model.addAttribute("user",user);
        List<Question> questionsList = qm.getQuestionByUser(user.getId());
        model.addAttribute("vos",questionsList);
        return "profile";
    }

    @RequestMapping(value="/questionByUser/{userId}",method = RequestMethod.GET)
    public String getAllQuestionsByUser(Model model,@PathVariable("userId") int userId){
        User user = um.findById(userId);
        model.addAttribute("user",user);
        List<Question> questionsList = qm.getQuestionByUser(user.getId());
        model.addAttribute("vos",questionsList);
        return "profile";
    }

    @RequestMapping(value="/question/{qId}",method = RequestMethod.GET)
    @LoginRequired
    public String getQuestion(Model model, @PathVariable("qId") int qId,HttpServletRequest req){
        User us =(User) req.getAttribute("currentUser");
        model.addAttribute("user",us);
        Question question = qm.getQuestionByQId(qId);
        model.addAttribute("question",question);
        List<Answer> answers = am.getAnswersByQuestion(qId);
        List<ViewObject> vos = new LinkedList<>();
        for(Answer answer : answers){
            Integer likeStatus = als.getLikeStatus(answer.getaId(),us);
            ViewObject vo = new ViewObject();
            vo.setLikeStatus(likeStatus);
            vo.setAnswer(answer);
            User user = um.findById(answer.getUserId());
            vo.setUser(user);
            Integer count = als.getLikeCount(answer.getaId());
            vo.setLikes(count);
            vos.add(vo);
        }
        model.addAttribute("vos",vos);
        return "detail";
    }

    @RequestMapping("/question/deletequestion")
    public void deleteQuestion(int qId){
        qm.deleteQuestion(qId);
    }
}
