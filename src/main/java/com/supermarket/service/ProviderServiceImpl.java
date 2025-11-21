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
}
