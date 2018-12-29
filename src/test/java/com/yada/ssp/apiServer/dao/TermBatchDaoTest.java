package com.yada.ssp.apiServer.dao;

import com.yada.ssp.apiServer.model.TermBatch;
import com.yada.ssp.apiServer.model.TermBatchPK;
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
public class TermBatchDaoTest {

    @Autowired
    private TermBatchDao termBatchDao;

    @Before
    public void setUp() {
        TermBatch cct = new TermBatch();
        cct.setMerchantId("123456789012345");
        cct.setTerminalId("12345678");
        cct.setBatchNo("000001");
        termBatchDao.save(cct);
    }

    @After
    public void tearDown() {
        termBatchDao.deleteAll();
    }

    @Test
    public void testUpdateSettleDate() {
        termBatchDao.updateBatchNo("000002", "123456789012345", "12345678");
        TermBatch tran = termBatchDao.findById(new TermBatchPK("123456789012345", "12345678")).orElse(new TermBatch());

        Assert.assertEquals("000002", tran.getBatchNo());
    }
}