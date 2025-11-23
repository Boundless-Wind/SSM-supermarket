package com.supermarket.dao;

import com.supermarket.domain.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderMapper {

    /**
     * 根据条件查询供应商列表
     *
     * @param proName 供应商名称，为空时查询所有供应商
     * @return 供应商列表
     */
    List<Provider> getProviderListByPage(@Param("proName") String proName);

    /**
     * 添加供应商
     *
     * @param provider 供应商
     * @return 添加结果
     */
    int add(Provider provider) throws Exception;

    /**
     * 根据供应商ID查询供应商
     *
     * @param id 供应商ID
     * @return 供应商
     */
    Provider getProviderById(int id);

    /**
     * 修改供应商
     *
     * @param provider 供应商
     * @return 修改结果
     */
    int modify(Provider provider) throws Exception;

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 删除结果
     */
    int deleteProviderById(int id) throws Exception;

}
