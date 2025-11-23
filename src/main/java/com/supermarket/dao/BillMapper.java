package com.supermarket.dao;

import com.supermarket.domain.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillMapper {

    /**
     * 根据条件查询订单列表
     *
     * @param productName 产品名称
     * @param providerId  供应商ID
     * @return 订单列表
     */
    List<Bill> getBillListByPage(@Param("productName") String productName, @Param("providerId") Integer providerId);

    /**
     * 添加订单
     *
     * @param bill 订单
     * @return 添加结果
     */
    // 建议增删改最好加上异常处理，因为增删改经常会有失败的时候.
    int add(Bill bill) throws Exception;

    /**
     * 根据订单ID查询订单
     *
     * @param id 订单ID
     * @return 订单
     */
    Bill getBillById(int id) throws Exception;

    /**
     * 修改订单
     *
     * @param bill 订单
     * @return 修改结果
     */
    int modify(Bill bill) throws Exception;

    /**
     * 删除订单
     *
     * @param id 订单ID
     * @return 删除结果
     */
    int deleteBillById(int id) throws Exception;

}
