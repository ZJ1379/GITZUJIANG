package com.leyou.controller;

import com.leyou.pojo.User;
import com.leyou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *实现用户数据的校验，主要包括对：手机号、用户名的唯一性校验。
     * @param data
     * @param type
     * @return
     */
    @GetMapping("/check/{data}/{type}")
    public Boolean check(@PathVariable("data") String data,@PathVariable("type") Integer type){
        return userService.check(data,type);

    }

    /**
     * 根据用户输入的手机号，生成随机验证码，长度为6位，纯数字。并且调用短信服务，发送验证码到用户手机
     * @param phone
     */
    @PostMapping("/code")
    public void code(@RequestParam("phone") String phone){
            //1.生成6为随机验证码

            //2.调用短信服务发送短信验证码 phone code
    }

    /**
     *
     * 用户注册
     * @param user
     * @param code
     */
    @PostMapping("/register")
    public void register(User user,String code){

    }

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/query")
    public User query(@RequestParam("username") String username,@RequestParam("password") String password){
        User user = new User();
        return user;
    }

}
