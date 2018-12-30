package com.yada.ssp.apiServer.service;

import com.yada.ssp.apiServer.dao.CurCupSuccessDao;
import com.yada.ssp.apiServer.dao.CurCupqrcTranDao;
import com.yada.ssp.apiServer.dao.TermBatchDao;
import com.yada.ssp.apiServer.model.CurCupSuccess;
import com.yada.ssp.apiServer.model.TermBatch;
import com.yada.ssp.apiServer.model.TermBatchPK;
import com.yada.ssp.apiServer.view.BatchNo;
import com.yada.ssp.apiServer.view.BatchQuery;
import com.yada.ssp.apiServer.view.BatchSettle;
import com.yada.ssp.apiServer.view.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BatchService.class)
public class BatchServiceTest {

    @MockBean
    private TermBatchDao termBatchDao;
    @MockBean
    private CurCupSuccessDao curCupSuccessDao;
    @MockBean
    private CurCupqrcTranDao curCupqrcTranDao;
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

    @Test
    public void testBatchSettleNull() {
        Mockito.when(termBatchDao.findById(Mockito.any(TermBatchPK.class)))
                .thenReturn(Optional.empty());

        BatchSettle batchSettle = new BatchSettle();
        Response<BatchSettle> resp = new Response<>();
        batchService.batchSettle(batchSettle, resp);

        Assert.assertEquals("91", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Issuer system error", resp.getMsgResponse().getRespDesc());
    }

    @Test
    public void testBatchSettleCurBatchNo() {
        TermBatch termBatch = new TermBatch();
        termBatch.setBatchNo("000001");
        Mockito.when(termBatchDao.findById(Mockito.any(TermBatchPK.class)))
                .thenReturn(Optional.of(termBatch));

        BatchSettle batchSettle = new BatchSettle();
        batchSettle.setBatchNo("000001");
        Response<BatchSettle> resp = new Response<>();
        batchService.batchSettle(batchSettle, resp);

        Assert.assertEquals("00", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Approved", resp.getMsgResponse().getRespDesc());
    }

    @Test
    public void testBatchSettleAgoBatchNo() {
        TermBatch termBatch = new TermBatch();
        termBatch.setBatchNo("000002");
        Mockito.when(termBatchDao.findById(Mockito.any(TermBatchPK.class)))
                .thenReturn(Optional.of(termBatch));

        BatchSettle batchSettle = new BatchSettle();
        batchSettle.setBatchNo("000001");
        Response<BatchSettle> resp = new Response<>();
        batchService.batchSettle(batchSettle, resp);

        Assert.assertEquals("00", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Approved", resp.getMsgResponse().getRespDesc());
    }

    @Test
    public void testBatchSettleFutBatchNo() {
        TermBatch termBatch = new TermBatch();
        termBatch.setBatchNo("000001");
        Mockito.when(termBatchDao.findById(Mockito.any(TermBatchPK.class)))
                .thenReturn(Optional.of(termBatch));

        BatchSettle batchSettle = new BatchSettle();
        batchSettle.setBatchNo("000002");
        Response<BatchSettle> resp = new Response<>();
        batchService.batchSettle(batchSettle, resp);

        Assert.assertEquals("12", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Invalid transaction", resp.getMsgResponse().getRespDesc());
    }

    @Test
    public void testQueryBatchSettle() {
        CurCupSuccess ccs1 = new CurCupSuccess();
        ccs1.setDiscountAmt(new BigDecimal(0.01));
        ccs1.setCostAmt(new BigDecimal(0.02));
        ccs1.setOriginalAmt(new BigDecimal(0.03));
        ccs1.setTranAmt(new BigDecimal(0.04));
        ccs1.setDebcreFlag("1");

        CurCupSuccess ccs2 = new CurCupSuccess();
        ccs2.setDiscountAmt(new BigDecimal(0.01));
        ccs2.setCostAmt(new BigDecimal(0.02));
        ccs2.setOriginalAmt(new BigDecimal(0.03));
        ccs2.setTranAmt(new BigDecimal(0.04));
        ccs2.setDebcreFlag("2");

        CurCupSuccess ccs3 = new CurCupSuccess();
        ccs3.setDiscountAmt(new BigDecimal(0.01));
        ccs3.setCostAmt(new BigDecimal(0.01));
        ccs3.setOriginalAmt(new BigDecimal(0.01));
        ccs3.setTranAmt(new BigDecimal(0.01));
        ccs3.setDebcreFlag("2");

        Mockito.when(curCupSuccessDao.findByMerchantIdAndTerminalIdAndBatchNo(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Arrays.asList(ccs1, ccs2, ccs3));

        BatchSettle info = new BatchSettle();
        info.setMerchantId("123456789012345");
        info.setTerminalId("12345678");
        info.setBatchNo("000001");
        batchService.queryBatchSettle(info);

        Assert.assertEquals(3, info.getTrxCount());
        Assert.assertEquals("-1", info.getDiscountAmtSum().toString());
        Assert.assertEquals("-1", info.getCostAmtSum().toString());
        Assert.assertEquals("-1", info.getOriginalAmtSum().toString());
        Assert.assertEquals("-1", info.getTrxAmtSum().toString());
    }

    @Test
    public void testBatchTranQueryNull() {
        Mockito.when(termBatchDao.findById(Mockito.any(TermBatchPK.class)))
                .thenReturn(Optional.empty());

        BatchQuery batchQuery = new BatchQuery();
        Response<BatchQuery> resp = new Response<>();
        batchService.batchTranQuery(batchQuery, resp);

        Assert.assertEquals("91", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Issuer system error", resp.getMsgResponse().getRespDesc());
    }

    @Test
    public void testBatchTranQueryFutBatchNo() {
        TermBatch termBatch = new TermBatch();
        termBatch.setBatchNo("000001");
        Mockito.when(termBatchDao.findById(Mockito.any(TermBatchPK.class)))
                .thenReturn(Optional.of(termBatch));

        BatchQuery batchQuery = new BatchQuery();
        batchQuery.setBatchNo("000002");
        Response<BatchQuery> resp = new Response<>();
        batchService.batchTranQuery(batchQuery, resp);

        Assert.assertEquals("12", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Invalid transaction", resp.getMsgResponse().getRespDesc());
    }

    @Test
    public void testBatchTranQuerySuccess() {
        TermBatch termBatch = new TermBatch();
        termBatch.setBatchNo("000002");
        Mockito.when(termBatchDao.findById(Mockito.any(TermBatchPK.class)))
                .thenReturn(Optional.of(termBatch));

        BatchQuery bq1 = new BatchQuery();
        Response<BatchQuery> resp1 = new Response<>();
        batchService.batchTranQuery(bq1, resp1);
        Assert.assertEquals("00", resp1.getMsgResponse().getRespCode());
        Assert.assertEquals("Approved", resp1.getMsgResponse().getRespDesc());
        Mockito.verify(curCupSuccessDao, Mockito.timeout(1))
                .findByMerchantIdAndTerminalIdAndBatchNo(null, null, "000002");

        BatchQuery bq2 = new BatchQuery();
        bq2.setBatchNo("");
        Response<BatchQuery> resp2 = new Response<>();
        batchService.batchTranQuery(bq2, resp2);
        Assert.assertEquals("00", resp2.getMsgResponse().getRespCode());
        Assert.assertEquals("Approved", resp2.getMsgResponse().getRespDesc());

        BatchQuery bq3 = new BatchQuery();
        bq3.setBatchNo("000001");
        Response<BatchQuery> resp3 = new Response<>();
        batchService.batchTranQuery(bq3, resp3);
        Assert.assertEquals("00", resp3.getMsgResponse().getRespCode());
        Assert.assertEquals("Approved", resp3.getMsgResponse().getRespDesc());

        BatchQuery bq4 = new BatchQuery();
        bq4.setBatchNo("000002");
        Response<BatchQuery> resp4 = new Response<>();
        batchService.batchTranQuery(bq4, resp4);
        Assert.assertEquals("00", resp4.getMsgResponse().getRespCode());
        Assert.assertEquals("Approved", resp4.getMsgResponse().getRespDesc());

        Mockito.verify(curCupSuccessDao, Mockito.times(4))
                .findByMerchantIdAndTerminalIdAndBatchNo(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    public void testQueryBatchTran() {
        BatchQuery bq1 = new BatchQuery();
        batchService.queryBatchTran(bq1, "000001");
        Mockito.verify(curCupSuccessDao, Mockito.times(1))
                .findByMerchantIdAndTerminalIdAndBatchNo(null, null, "000001");

        BatchQuery bq2 = new BatchQuery();
        bq2.setBatchNo("");
        batchService.queryBatchTran(bq2, "000002");
        Mockito.verify(curCupSuccessDao, Mockito.times(1))
                .findByMerchantIdAndTerminalIdAndBatchNo(null, null, "000002");

        BatchQuery bq3 = new BatchQuery();
        bq3.setBatchNo("000003");
        batchService.queryBatchTran(bq3, "000004");
        Mockito.verify(curCupSuccessDao, Mockito.times(1))
                .findByMerchantIdAndTerminalIdAndBatchNo(null, null, "000003");
    }
}