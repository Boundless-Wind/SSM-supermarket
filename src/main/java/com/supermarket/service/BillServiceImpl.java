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

    @Override
    public boolean add(Bill bill) {
        try {
            return billMapper.add(bill) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Bill getBillById(int id) {
        try {
            return billMapper.getBillById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean modify(Bill bill) {
        try {
            return billMapper.modify(bill) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteBillById(int id) {
        try {
            return billMapper.deleteBillById(id) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
