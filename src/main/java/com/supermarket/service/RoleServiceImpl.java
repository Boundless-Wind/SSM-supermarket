package com.supermarket.service;

import com.supermarket.dao.RoleMapper;
import com.supermarket.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper; // 引用前面的RoleMapper

    @Override
    public List<Role> getRoleList() {
        return roleMapper.getRoleList();
    }

}
