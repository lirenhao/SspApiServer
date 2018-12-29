package com.yada.ssp.apiServer.dao;

import com.yada.ssp.apiServer.model.CurCupqrcTran;
import com.yada.ssp.apiServer.util.DateUtil;
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
public class CurCupqrcTranDaoTest {

    @Autowired
    private CurCupqrcTranDao curCupqrcTranDao;

    @Before
    public void setUp() {
        CurCupqrcTran cct = new CurCupqrcTran();
        cct.setLsId("1");
        cct.setMerchantId("123456789012345");
        cct.setTerminalId("12345678");
        cct.setBatchNo("000001");
        curCupqrcTranDao.save(cct);
    }

    @After
    public void tearDown() {
        curCupqrcTranDao.deleteAll();
    }

    @Test
    public void testUpdateSettleDate() {
        curCupqrcTranDao.updateSettleDate(DateUtil.getCurDate(),
                "123456789012345", "12345678", "000001");
        CurCupqrcTran tran = curCupqrcTranDao.findById("1").orElse(new CurCupqrcTran());

        Assert.assertEquals(DateUtil.getCurDate(), tran.getLocalSettleDate());
    }
}