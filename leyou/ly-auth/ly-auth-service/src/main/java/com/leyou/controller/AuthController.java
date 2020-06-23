package com.leyou.controller;

import com.leyou.client.UserClient;
import com.leyou.common.JwtUtils;
import com.leyou.common.UserInfo;
import com.leyou.config.JwtProperties;
import com.leyou.pojo.User;
import com.leyou.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    @Autowired
    private UserClient userClient;

    @Autowired
    JwtProperties jwtProperties;



    /*
     * 根据用户名和密码登录
     * */
    @PostMapping("login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request, HttpServletResponse response){
        System.out.println("查询用户："+username+"======"+password);

        String result = "1";

        //获取用户信息
        User queryUser = userClient.query(username, password);

        try {
            if(queryUser!=null){
                //把用户信息根据公钥和私钥生成token
                String token = JwtUtils.generateToken(new UserInfo(queryUser.getId(), queryUser.getUsername())
                        , jwtProperties.getPrivateKey(),jwtProperties.getExpire() * 60);

                //存入cookies
                CookieUtils.setCookie(request,response,jwtProperties.getCookieName(),token,jwtProperties.getExpire()*60);
            }

            result="0";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 当cookie失效之后重新设置cookie
     */
    @GetMapping("verify")
    public Object verify(@CookieValue(value = "token",required = false) String token, HttpServletRequest request, HttpServletResponse response){

        System.out.println("verify====="+token);
        UserInfo userInfo =new UserInfo();
        try {
            //从token信息中解析获取用户信息
            userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());

            //防止过期，重新设置token
            token = JwtUtils.generateToken(new UserInfo(userInfo.getId(), userInfo.getUsername()),
                    jwtProperties.getPrivateKey(), jwtProperties.getExpire());

            //返回token
            CookieUtils.setCookie(request,response,jwtProperties.getCookieName(),token,jwtProperties.getExpire()*60);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return userInfo;
    }
}
