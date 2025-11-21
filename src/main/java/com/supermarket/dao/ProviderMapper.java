package com.supermarket.dao;

import com.supermarket.domain.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderMapper {

    /**
     * 根据条件查询供应商列表
     *
     * @param proName 供应商名称
     * @return 供应商列表
     */
    List<Provider> getProviderListByPage(@Param("proName") String proName);
}
