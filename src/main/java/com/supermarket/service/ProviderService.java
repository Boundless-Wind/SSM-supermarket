package com.supermarket.service;

import com.supermarket.domain.Provider;

import java.util.List;

public interface ProviderService {

    /**
     * 根据条件查询供应商列表
     *
     * @param proName 供应商名称
     * @return 供应商列表
     */
    List<Provider> getProviderListByPage(String proName);

    /**
     * 添加供应商
     *
     * @param provider 供应商
     * @return 是否添加成功
     */
    boolean add(Provider provider);

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
    boolean modify(Provider provider);

    /**
     * 删除供应商
     *
     * @param id 供应商ID
     * @return 修改结果
     */
    boolean deleteProviderById(int id);

}
