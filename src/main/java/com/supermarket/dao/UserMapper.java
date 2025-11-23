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

    /**
     * 添加用户
     *
     * @param user 用户
     * @return 添加结果
     */
    // 建议增删改最好加上异常处理，因为增删改经常会有失败的时候.
    int add(User user) throws Exception;

    /**
     * 根据用户ID查询用户
     *
     * @param id 用户ID
     * @return 用户
     */
    User getUserById(int id) throws Exception;

    /**
     * 修改用户
     *
     * @param user 用户
     * @return 修改结果
     */
    int modify(User user) throws Exception;

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 删除结果
     */
    int deleteUserById(int id) throws Exception;

}
