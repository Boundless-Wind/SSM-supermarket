package com.supermarket;

import com.supermarket.domain.Provider;
import com.supermarket.service.ProviderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProviderTests {

    @Autowired
    private ProviderService roleService;

    /**
     * 测试获取供应商列表
     */
    @Test
    void testGetProviderList() {
        List<Provider> providerList = roleService.getProviderListByPage("北京");
        for (Provider provider : providerList) {
            System.out.println("供应商ID: " + provider.getId() + "，供应商代码: " + provider.getProCode() + "，供应商名称:" + provider.getProName());
        }
    }

}
