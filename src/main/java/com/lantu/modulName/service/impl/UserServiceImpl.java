package com.lantu.modulName.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lantu.modulName.entity.User;
import com.lantu.modulName.mapper.UserMapper;
import com.lantu.modulName.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoliu
 * @since 2023-04-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, Object> login(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());

        User loginUser = this.baseMapper.selectOne(wrapper);

        String key = null;
        //UUID随机 终极方案是jwt
        if (loginUser != null && passwordEncoder.matches(user.getPassword(),loginUser.getPassword())) {
            key = "user:" + UUID.randomUUID();
        }
        //存入redis
        loginUser.setPassword(null);
        redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);

        Map<String, Object> data = new HashMap<>();
        data.put("token", key);
        return data;
    }

//    @Override
//    public Map<String, Object> login(User user) {
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(User::getUsername, user.getUsername());
//        wrapper.eq(User::getPassword, user.getPassword());
//        User loginUser = this.baseMapper.selectOne(wrapper);
//
//        String key = null;
//        //UUID随机 终极方案是jwt
//        if (loginUser != null) {
//            key = "user:" + UUID.randomUUID();
//        }
//        //存入redis
//        loginUser.setPassword(null);
//        redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("token", key);
//        return data;
//    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        Object obj= redisTemplate.opsForValue().get(token);
        if(obj!=null){
            User loginUser=JSON.parseObject(JSON.toJSONString(obj),User.class);
            Map<String, Object> data=new HashMap<>();
            data.put("name",loginUser.getUsername());
            data.put("avatar",loginUser.getAvatar());

            List<String> roleList=this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles",roleList);
            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }
}
