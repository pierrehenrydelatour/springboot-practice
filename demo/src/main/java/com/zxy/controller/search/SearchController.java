package com.zxy.controller.search;

import com.zxy.annotation.LoginRequired;
import com.zxy.model.Question;
import com.zxy.model.User;
import com.zxy.model.ViewObject;
import com.zxy.service.mappers.questionMapper;
import com.zxy.service.mappers.userMapper;
import com.zxy.service.search.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    SearchService ss;

    @Autowired
    questionMapper qm;

    @Autowired
    userMapper um;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping(path = {"/search"}, method = {RequestMethod.GET})
    @LoginRequired
    public String search(Model model, @RequestParam("q") String keyword,
                         @RequestParam(value = "p",defaultValue = "1") int p, HttpServletRequest req) {
        try {
            int offset = (p-1)*10;
            User user = (User) req.getAttribute("currentUser");
            if(user!=null)
                model.addAttribute("user",user);
            List<Question> questionList = ss.testSearch(keyword,offset,10);
            List<ViewObject> vos = new ArrayList<>();
            for (Question question : questionList) {
                Question q = qm.getQuestionByQId(question.getId());
                ViewObject vo = new ViewObject();
                if (question.getText() != null) {
                    q.setText(question.getText());
                }
                if (question.getTitle() != null) {
                    q.setTitle(question.getTitle());
                }
                vo.setQuestion(q);
                vo.setUser(um.findById(q.getUserId()));
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("keyword", keyword);
        } catch (Exception e) {
            logger.error("搜索失败" + e.getMessage());
        }
        return "result";
    }
}
