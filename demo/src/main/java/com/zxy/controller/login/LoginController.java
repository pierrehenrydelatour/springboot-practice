package com.zxy.controller.login;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.zxy.annotation.LoginRequired;
import com.zxy.service.MQ.SendMessageService;
import com.zxy.service.mappers.userMapper;
import com.zxy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@Controller
public class LoginController {

    @Autowired
    userMapper us;
    @Autowired
    SendMessageService sms;

    @RequestMapping("/")
    public String index(HttpServletRequest request,Model model){
        Cookie[] cks = request.getCookies();
        String token = "";
        if(cks != null) {
            for (Cookie ck : cks) {
                if (ck.getName().equals("token")) {
                    token = ck.getValue();
                }
            }
        }
        String idString = "";
        if(token != null && token != ""){
            try {
                idString = JWT.decode(token).getAudience().get(0);
                User user = us.findById(Integer.parseInt(idString));
                JWTVerifier ver = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                ver.verify(token);
                model.addAttribute("user",user);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "index";
    }

    @RequestMapping("/login")
    public String isLogin(@RequestParam String email, @RequestParam String password,
                          HttpServletResponse resp, Model model){
        User user = us.findByMail(email);
        if(user == null){
            model.addAttribute("msg","sorry, user does not exist!");
            return "login";
        }else if(!password.equals(user.getPassword())){
            model.addAttribute("msg","sorry, password incorrect");
            return "login";
        }
        else {
            Cookie ck = new Cookie("token",user.getToken());
            ck.setPath("/");
            ck.setMaxAge(60*60);
            resp.addCookie(ck);
            model.addAttribute("user",user);
            return "index";
        }
    }

    @RequestMapping("/afterpose")
    @LoginRequired
    public String afterPose(HttpServletRequest req,Model model){
        User user =(User) req.getAttribute("currentUser");
        model.addAttribute("user",user);
        return "index";
    }

    @RequestMapping("/signup")
    public String signup(@RequestParam String email, @RequestParam String password
    ,Model model,HttpServletResponse resp){
        User user = us.findByMail(email);
        if(user != null){
          model.addAttribute("msg","user already exists");
          return "login";
        } else{
            String url = String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000));
            JSONObject mes = new JSONObject();

            //send message
            mes.put("email",email);
            mes.put("password",password);
            mes.put("url",url);
            sms.sendMessage("signup",mes.toJSONString());
            System.out.println("send signup message");
            /*us.Insert(email,password,url);*/

            user = us.findByMail(email);
            if(user != null) {
                Cookie ck = new Cookie("token", user.getToken());
                ck.setPath("/");
                ck.setMaxAge(60 * 60);
                resp.addCookie(ck);
                model.addAttribute("user", user);
            }
            model.addAttribute("msg","registered successfully! pls log in again");
            return "login";
        }
    }

    @RequestMapping("/test")
    @ResponseBody
    @LoginRequired
    public String test(HttpServletRequest request){
        User user = (User) request.getAttribute("currentUser");
        return "success"+user.getEmail();
    }

    @RequestMapping("/logout")
    @LoginRequired
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cks =request.getCookies();
        if(cks!=null){
            for(Cookie ck : cks){
                if(ck.getName().equals("token")){
                    ck.setMaxAge(0);
                    response.addCookie(ck);
                }
            }
        }
        return "index";
    }

    @RequestMapping("/reglogin")
    public String regLogin(Model model){
        System.out.println("reach /reglogin");
        return "login";
    }

}
