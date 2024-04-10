package com.lantu.modulName.service;

import com.lantu.modulName.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaoliu
 * @since 2023-04-09
 */
public interface IUserService extends IService<User> {

    public Map<String, Object> login(User user);

    Map<String, Object> getUserInfo(String token);

    void logout(String token);
}
