package com.supermarket.service;

import com.supermarket.domain.Bill;

import java.util.List;

public interface BillService {

    /**
     * 根据条件查询用户列表
     *
     * @param productName 产品名称
     * @param providerId  供应商ID
     * @return 用户列表
     */
    List<Bill> getBillListByPage(String productName, Integer providerId);

}
