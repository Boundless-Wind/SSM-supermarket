package com.supermarket;

import com.supermarket.domain.Bill;
import com.supermarket.service.BillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BillTests extends BaseTests {

    @Autowired
    private BillService billService;

    /**
     * 测试订单列表
     */
    @Test
    public void testGetBillListByPage() {
        String productName = "碗";
        int providerId = 0;
        List<Bill> billList = billService.getBillListByPage(productName, providerId);
        for (Bill bill : billList) {
            System.out.println("ID:" + bill.getId() + " 订单帐号：" + bill.getBillCode() + " 订单姓名:" + bill.getProductName() + " 订单供货商:" + bill.getProvider().getProName());
        }
    }

}
