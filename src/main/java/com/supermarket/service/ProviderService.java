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
}
