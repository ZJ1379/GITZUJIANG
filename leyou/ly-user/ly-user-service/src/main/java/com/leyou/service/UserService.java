package com.leyou.service;

import com.leyou.dao.UserMapper;
import com.leyou.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public Boolean check(String data, Integer type) {
        Boolean result=false;
        User user=new User();

        if(type==1){
            user.setUsername(data);
        }else if(type==2){
            user.setPhone(data);
        }
        User user1 = userMapper.selectOne(user);
        if(user1==null){
            return true;
        }
        return result;
    }

    public void insertUser(User user) {
        //盐值
        String uuid = UUID.randomUUID().toString().substring(0,32);

        String password=this.getPws(user.getPassword(),uuid);
        user.setPassword(password);
        user.setCreated(new Date());
        user.setSalt(uuid);
        userMapper.insert(user);
    }

    /**
     * 通过原生密码+盐值生成加密后的密码
     * @param password
     * @param salt
     * @return
     */
    public String getPws(String password,String salt){

        //如何使用md5加密
        String md5Hex = DigestUtils.md5Hex(password + salt);

        return md5Hex;
    }

    public User findUser(String username) {
        User user = new User();
        user.setUsername(username);
        return userMapper.selectOne(user);
    }
}
