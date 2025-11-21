package com.supermarket.service;

import com.supermarket.dao.BillMapper;
import com.supermarket.domain.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillMapper billMapper;

    @Override
    public List<Bill> getBillListByPage(String productName, Integer providerId) {
        return billMapper.getBillListByPage(productName, providerId);
    }

}
