package com.yada.ssp.apiServer.dao;

import com.yada.ssp.apiServer.model.ApiOrg;
import com.yada.ssp.apiServer.model.Merchant;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiOrgDaoTest {

    @Autowired
    private MerchantDao merchantDao;
    @Autowired
    private ApiOrgDao apiOrgDao;

    @Before
    public void before() {
        Merchant merchant = new Merchant();
        merchant.setMerNo("123456789012345");
        merchantDao.save(merchant);

        ApiOrg apiOrg = new ApiOrg();
        apiOrg.setOrgId("0001");
        apiOrg.setPublicKey("publicKey");
        apiOrg.getMerchants().add(merchant);
        apiOrgDao.save(apiOrg);
    }

    @After
    public void after() {
        apiOrgDao.deleteAll();
        merchantDao.deleteAll();
    }

    @Test
    public void findByOrgIdAndMerchants_MerNo() {
        ApiOrg apiOrg = apiOrgDao.findByOrgIdAndMerchants_MerNo("0001", "123456789012345").orElse(new ApiOrg());
        Assert.assertEquals("0001", apiOrg.getOrgId());
        Assert.assertEquals("publicKey", apiOrg.getPublicKey());
    }
}