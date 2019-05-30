package com.zxy.demo.controller.login;

import com.alibaba.fastjson.JSONObject;
import com.zxy.demo.annotation.LoginRequired;
import com.zxy.demo.service.mappers.userMapper;
import com.zxy.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginService {

    @Autowired
    userMapper us;

    @RequestMapping("/login")
    @ResponseBody
    public JSONObject isLogin(@RequestParam String email, @RequestParam String password, HttpServletResponse resp){
        User user = us.findByMail(email);
        JSONObject jsonObject = new JSONObject();
        if(user == null){
            jsonObject.put("message","user does not exists");
        }else if(!password.equals(user.getPassword())){
            jsonObject.put("message","incorrect password");
        }
        else {
            Cookie ck = new Cookie("token",user.getToken());
            ck.setPath("/");
            ck.setMaxAge(600);
            resp.addCookie(ck);
            jsonObject.put("email",user.getEmail());
            jsonObject.put("password",user.getPassword());
            jsonObject.put("token",user.getToken());
        }
        return jsonObject;
    }

    @RequestMapping("/signup")
    @ResponseBody
    public JSONObject signup(@RequestParam String email, @RequestParam String password){
        JSONObject jsonObject = new JSONObject();
        User user = us.findByMail(email);
        if(user != null){
          jsonObject.put("message","user already exists");
        } else{
            us.Insert(email,password);
            jsonObject.put("email",email);
            jsonObject.put("password",password);
        }
        return jsonObject;
    }

    @RequestMapping("/test")
    @ResponseBody
    @LoginRequired
    public String test(HttpServletRequest request){
        User user = (User) request.getAttribute("currentUser");
        return "success"+user.getEmail();
    }

    @RequestMapping("/logout")
    @ResponseBody
    public void logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cks =request.getCookies();
        if(cks!=null){
            for(Cookie ck : cks){
                if(ck.getName().equals("token")){
                    ck.setMaxAge(0);
                    response.addCookie(ck);
                }
            }
        }
    }
}
