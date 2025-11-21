package com.supermarket.dao;

import com.supermarket.domain.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillMapper {

    /**
     * 根据条件查询用户列表
     *
     * @param productName 产品名称
     * @param providerId  供应商ID
     * @return 用户列表
     */
    List<Bill> getBillListByPage(@Param("productName") String productName, @Param("providerId") Integer providerId);

}
