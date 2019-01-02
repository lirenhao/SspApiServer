package com.yada.ssp.apiServer.service;

import com.yada.ssp.apiServer.dao.AccountInfoDao;
import com.yada.ssp.apiServer.view.AccountFile;
import com.yada.ssp.apiServer.view.MsgInfo;
import com.yada.ssp.apiServer.view.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TranService.class)
public class TranServiceTest {

    @MockBean
    private AccountInfoDao accountInfoDao;
    @Autowired
    private TranService tranService;

    @Test
    public void testAccountFile() {
        AccountFile info = new AccountFile();
        info.setSettleDate("settleDate");

        Response<AccountFile> resp = new Response<>();
        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setOrgId("orgId");
        resp.setMsgInfo(msgInfo);

        tranService.accountFile(info, resp);

        Assert.assertEquals("00", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Approved", resp.getMsgResponse().getRespDesc());
        Assert.assertEquals(info, resp.getTrxInfo());

        Mockito.verify(accountInfoDao, Mockito.times(1))
                .findByOrgIdAndSettleDate("orgId", "settleDate");
    }
}