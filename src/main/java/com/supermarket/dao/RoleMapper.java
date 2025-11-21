package com.supermarket.dao;

import com.supermarket.domain.Role;

import java.util.List;

public interface RoleMapper {

    /**
     * 获取角色列表
     *
     * @return 角色列表
     */
    List<Role> getRoleList();

}
