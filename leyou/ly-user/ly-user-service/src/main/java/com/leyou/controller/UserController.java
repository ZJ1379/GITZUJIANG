package com.leyou.controller;

import com.leyou.pojo.User;
import com.leyou.service.UserService;
import com.leyou.utils.CodeUtils;
import com.mysql.cj.util.TimeUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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
        String code = CodeUtils.messageCode(6);
        //2.使用rabbitMQ发送短信phone code
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("code",code);
        amqpTemplate.convertAndSend("sms.changes","sms.send",map);

        //3.发送短信后存放redis，放验证码
        stringRedisTemplate.opsForValue().set("lysms_"+phone,code,5, TimeUnit.MINUTES);
    }

    /**
     *
     * 用户注册
     * @param user
     * @param code
     */
    @PostMapping("/register")
    public String register(@Valid User user, String code){
        String result="0";
         String redisCode = stringRedisTemplate.opsForValue().get("lysms_" + user.getPhone());

             if(redisCode.equals(code)){
                userService.insertUser(user);
                 result="1";
                }
         return result;
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

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
/*    @PostMapping("login")
    public Boolean login(@RequestParam("username") String username,@RequestParam("password") String password){
        Boolean result=false;
        User user=userService.findUser(username);
        if(user!=null){
            String salt = user.getSalt();
            String md5Hex = DigestUtils.md5Hex(password + salt);
            if(md5Hex.equals(user.getPassword())){
                  result=true;
            }
        }
        return result;
    }*/



}
