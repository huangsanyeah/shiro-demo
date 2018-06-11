package com.example.demo.web;

import com.example.demo.config.Exception.IncorrectCaptchaException;
import com.example.demo.entity.Response.Response;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;



@Controller
public class HomeController {

    @RequestMapping("/logoutTest")
    public String logout() {
        return "logoutTest";
    }

    @RequestMapping({"/","/index"})
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Map<String, Object> map) throws Exception {

        System.out.println("HomeController.login");

        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        Object exception = request.getAttribute("shiroLoginFailure");
//        String exception = (String) request.getAttribute("shiroLoginFailure");
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.isInstance(exception)) {
                System.out.println("账户不存在");
                msg = "账户不存在或密码不正确";
            } else if (IncorrectCredentialsException.class.isInstance(exception)) {
                System.out.println("密码不正确");
                msg = "账户不存在或密码不正确";
            } else if (IncorrectCaptchaException.class.isInstance(exception)) {
                System.out.println("验证码不正确");
                msg = "验证码不正确";
            } else {
                System.out.println("其他异常");
                msg = "其他异常";
            }
        }

        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理.
        return "login";
    }


/*
    @RequestMapping(value = "/loginUser", method = RequestMethod.GET)
    @ResponseBody
    public Response loginUser(@RequestParam String username,
                              @RequestParam String password) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        //log.info("用户名：" + username + ";" + "密码：" + password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException uae) {
            //log.info("账户名有误");
            return new Response(false, "账户名有误", null);
        } catch (IncorrectCredentialsException ice) {
            //log.info("密码与账户名不匹配");
            return new Response(false, "密码与账户名不匹配", null);
        } catch (DisabledAccountException e) {
            return new Response(false, "该账户已禁用，请联系管理员", null);
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            //log.error("用户登录异常，请稍后重试,异常信息:{}", ae.getMessage());
            return new Response(false,"登录异常", null);
        }
        return new Response(true, "用户登录成功", null);
    }*/
}
