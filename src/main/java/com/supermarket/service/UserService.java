package com.supermarket.service;

import com.supermarket.domain.User;

import java.util.List;

public interface UserService {

    /**
     * 根据用户账号查询用户
     *
     * @param userCode 用户账号
     * @return User
     */
    User getUserByUserCode(String userCode);

    /**
     * 根据条件查询用户列表
     *
     * @param queryUserName 用户名称
     * @param queryUserRole 用户角色
     * @return 用户列表
     */
    List<User> getUserListByPage(String queryUserName, int queryUserRole);

}
