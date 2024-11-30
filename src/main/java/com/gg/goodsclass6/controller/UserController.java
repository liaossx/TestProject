package com.gg.goodsclass6.controller;


import com.gg.goodsclass6.entity.User;
import com.gg.goodsclass6.helpers.Verify;

import com.gg.goodsclass6.povos.ResultPovo;
import com.gg.goodsclass6.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private  JavaMailSender  javaMailSender;

    @Autowired
    private UserService  userService;
    /*书写一个用户注册*/
    /*我要一个完整的用户登录功能*/




    @PostMapping("/verifyloginname")
    @ResponseBody
    public  Boolean  verifyloginname(String loginname){
        /*Alt +  Enter  红色*/
        return  userService.verifyLoginname(loginname);

    }

    @PostMapping("/verifyemail")
    @ResponseBody
    public   Boolean   verifyemail(String  email){
        return  userService.verifyEmail(email);
    }

    /*生成验证码*/
    @GetMapping("/createVerifyCode")
    public  void  createVerifyCode(HttpServletRequest request, HttpServletResponse response){
        try {
            /*1.生成图片给浏览器  2.Session中放真值*/
            Verify.getVirify(request, response) ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /*校验验证码*/
    @PostMapping("/verifyVerifyCode")
    @ResponseBody
    public Boolean  verifyVerifyCode(String  verifyCode, HttpSession  session){
        /*从Session中获取真值*/
        String localVerifyCode = (String) session.getAttribute("vCode");
        /*和用户输入的值比较*/
        /*equalsIgnoreCase  不区分大小写的比较字符串*/
        if(localVerifyCode.equalsIgnoreCase(verifyCode)){
            return true;
        }
        return  false ;
    }

    /*用户注册*/
    /*用户注册*/
   /*用户注册*/
    @GetMapping("/regist")
    public  String  regist(User user, Model model){
        /*往request中 放值  model*/

        boolean   res =  userService.regist(user);
        if(res){
            /*发邮件*/
                /*1.制作信*/
                    /*from,to,subject,content */
                String to = user.getEmail();
                /*创建一个专门读java 标准配置文件的 工具类对象*/
            Properties  prop =  new Properties();
            /*一次性把这个文件读到内存里*/
            try {
                prop.load(this.getClass().getClassLoader().
                        getResourceAsStream("email.properties"));
                String  from =prop.getProperty("from");//642542344@qq.com
                String  subject = prop.getProperty("subject");//来自网上书城邮件
                String  content = prop.getProperty("content");//点击链接<a> ....激活
                //当前的content 缺少激活码，  目前 {0}  {1}  {2}占位
                //把{0}替换成真正的用户激活码，在user中
                /*
                * String str = "abc{0}defg{1}123{2}456"
                * str = MessageFormat.format(str,"zhangsna","lisi","wangwu");
                * str =>  abczahngsandefglisi123wangwu456
                * */
                content= MessageFormat.format(content,user.getActivationcode());
                /*信*/
                SimpleMailMessage mail =  new SimpleMailMessage();
                mail.setFrom(from);
                mail.setTo(to);
                mail.setSubject(subject);
                mail.setText(content);
                /*2.发出去*/
                /*发*/
                javaMailSender.send(mail);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            model.addAttribute("msg","注册成功，请尽快到邮箱中激活");
            model.addAttribute("code","success");
        }else{
            model.addAttribute("msg","注册失败，请重试");
            model.addAttribute("code","error");
        }
        /*转发    /jsps/msg.jsp */
        return  "jsps/msg";
    }

    /*用户激活*/
    ///goods/user/activation?activationCode
    @RequestMapping("/activation")
    public  String  activation(String activationCode,Model model){
        /*
        * 结果
        *       一、 成功    对号 、激活成功
        *       二、 失败   错号 、激活失败请重试（服务原因）
        *                         错误的激活码
        *
        *   封装一个消息类。
        * */
        ResultPovo povo =  userService.activation(activationCode);
        if(povo.getRes()){
            //成功
            model.addAttribute("code","success");

        }else{
            model.addAttribute("code","error");
        }
        model.addAttribute("msg", povo.getMsg());

        return "jsps/msg";

    }

//登录
    @PostMapping("/login")
    public String login(User user, Model model, HttpSession session){
    User u = userService.login(user);
    if(u==null){
        model.addAttribute("msg","用户名或者密码错误");
        return "jsps/user/login";

    }else{
        if(u.getStatus()==0){
            model.addAttribute("msg","用户未激活，请先激活");
            return "jsps/user/login";
        }else{
            //登录成功，把用户信息保存到session中，跳转到主页面
            session.setAttribute("user",u);
            return "redirect:/jsps/main.jsp";
        }
        }
    }

//登出
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/jsps/main.jsp";
    }


//验证原密码是否一致
@PostMapping("/validateLoginPass")
@ResponseBody
public Boolean validateLoginPass(@RequestParam("loginPass") String loginPass, HttpSession session) {
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null) {
        return false; // 用户未登录
    }
    return userService.validatePassword(currentUser.getUid(), loginPass);
}

    @PostMapping("/changePassword")
    @ResponseBody
    public Map<String, String> changePassword(
            @RequestParam("newpass") String newpass,
            @RequestParam("reloginpass") String reloginpass,
            @RequestParam("verifyCode") String verifyCode,
            HttpSession session) {

        Map<String, String> result = new HashMap<>();
        String loginname = (String) session.getAttribute("loginname");
        String sessionVerifyCode = (String) session.getAttribute("vCode");

        // 校验验证码
        if (!verifyCode.equalsIgnoreCase(sessionVerifyCode)) {
            result.put("status", "error");
            result.put("msg", "验证码错误！");
            return result;
        }

        // 校验两次新密码是否一致
        if (!newpass.equals(reloginpass)) {
            result.put("status", "error");
            result.put("msg", "两次密码输入不一致！");
            return result;
        }

        // 调用服务层修改密码
        boolean isChanged = userService.changePassword(loginname, newpass);
        if (isChanged) {
            result.put("status", "success");
            result.put("msg", "密码修改成功！");
        } else {
            result.put("status", "error");
            result.put("msg", "密码修改失败，请稍后重试！");
        }

        return result;
    }
    }














