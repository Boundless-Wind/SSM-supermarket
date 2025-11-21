package com.supermarket.service;

import com.supermarket.dao.UserMapper;
import com.supermarket.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;  // 注入用户Mapper

    @Override
    public User getUserByUserCode(String userCode) {
        // 更推荐的方式：直接返回查询结果，由调用者判断是否为null
        return userMapper.getUserByUserCode(userCode);
    }

    @Override
    public List<User> getUserListByPage(String queryUserName, int queryUserRole) {
        return userMapper.getUserListByPage(queryUserName, queryUserRole);
    }

}
