package com.yada.ssp.apiServer.service;

import com.yada.ssp.apiServer.dao.TermBatchDao;
import com.yada.ssp.apiServer.model.TermBatch;
import com.yada.ssp.apiServer.model.TermBatchPK;
import com.yada.ssp.apiServer.view.BatchNo;
import com.yada.ssp.apiServer.view.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BatchService.class)
public class BatchServiceTest {

    @MockBean
    private TermBatchDao termBatchDao;
    @Autowired
    private BatchService batchService;

    @Test
    public void testGetBatchNoNotNull() {
        TermBatch termBatch = new TermBatch();
        termBatch.setBatchNo("000001");
        Mockito.when(termBatchDao.findById(Mockito.any(TermBatchPK.class)))
                .thenReturn(Optional.of(termBatch));

        BatchNo batchNo = new BatchNo();
        Response<BatchNo> resp = new Response<>();
        batchService.getBatchNo(batchNo, resp);

        Assert.assertEquals("00", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Approved", resp.getMsgResponse().getRespDesc());
        Assert.assertEquals("000001", resp.getTrxInfo().getBatchNo());
    }

    @Test
    public void testGetBatchNoNull() {
        Mockito.when(termBatchDao.findById(Mockito.any(TermBatchPK.class)))
                .thenReturn(Optional.empty());

        BatchNo batchNo = new BatchNo();
        Response<BatchNo> resp = new Response<>();
        batchService.getBatchNo(batchNo, resp);

        Assert.assertEquals("91", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Issuer system error", resp.getMsgResponse().getRespDesc());
    }
}