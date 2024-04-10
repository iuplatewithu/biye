package com.lantu;

import com.lantu.modulName.entity.User;
import com.lantu.modulName.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class XAdminApplicationTests {

    @Resource
    public UserMapper userMapper;
    @Test
    void textMapper() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

}
