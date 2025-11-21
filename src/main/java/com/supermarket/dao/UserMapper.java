package com.supermarket.dao;

import com.supermarket.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

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
     * @param userName 用户名称
     * @param userRole 用户角色
     * @return 用户列表
     */
    List<User> getUserListByPage(@Param("userName") String userName, @Param("userRole") Integer userRole);

}
