package com.supermarket.service;

import com.supermarket.domain.Bill;

import java.util.List;

public interface BillService {

    /**
     * 根据条件查询订单列表
     *
     * @param productName 产品名称
     * @param providerId  供应商ID
     * @return 订单列表
     */
    List<Bill> getBillListByPage(String productName, Integer providerId);

    /**
     * 添加订单
     *
     * @param bill 订单
     * @return 是否添加成功
     */
    boolean add(Bill bill);

    /**
     * 根据订单ID查询订单
     *
     * @param id 订单ID
     * @return 订单
     */
    Bill getBillById(int id);

    /**
     * 修改订单
     *
     * @param bill 订单
     * @return 修改结果
     */
    boolean modify(Bill bill);

    /**
     * 删除订单
     *
     * @param id 订单ID
     * @return 修改结果
     */
    boolean deleteBillById(int id);

}
