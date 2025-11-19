package com.supermarket.dao;

import com.supermarket.domain.User;

/**
 * 用户Mapper
 */
public interface UserMapper {

    /**
     * 根据用户账号查询用户
     * @param userCode 用户账号
     * @return User
     */
    User getUserByUserCode(String userCode);
}
