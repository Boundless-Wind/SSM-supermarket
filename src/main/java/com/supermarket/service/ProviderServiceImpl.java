package com.supermarket.service;

import com.supermarket.dao.ProviderMapper;
import com.supermarket.domain.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public List<Provider> getProviderListByPage(String proName) {
        return providerMapper.getProviderListByPage(proName);
    }

    @Override
    public boolean add(Provider provider) {
        try {
            return providerMapper.add(provider) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Provider getProviderById(int id) {
        try {
            return providerMapper.getProviderById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean modify(Provider provider) {
        try {
            return providerMapper.modify(provider) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteProviderById(int id) {
        try {
            return providerMapper.deleteProviderById(id) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
