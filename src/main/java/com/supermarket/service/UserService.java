package com.supermarket.service;

import com.supermarket.domain.User;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 根据用户账号查询用户
     * @param userCode 用户账号
     * @return User
     */
    User getUserByUserCode(String userCode);
}
