package com.yada.ssp.apiServer.service;

import com.yada.ssp.apiServer.net.SspClient;
import com.yada.ssp.apiServer.view.QrCode;
import com.yada.ssp.apiServer.view.Query;
import com.yada.ssp.apiServer.view.Refund;
import com.yada.ssp.apiServer.view.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SspService.class)
public class SspServiceTest {

    @MockBean
    private SspClient sspClient;
    @Autowired
    private SspService sspService;

    @Test
    public void testQrCodeException() throws IOException {
        Mockito.when(sspClient.send(Mockito.any(ByteBuffer.class)))
                .thenThrow(new IOException("i/o error"));

        QrCode info = new QrCode();
        info.setTranAmt(BigInteger.valueOf(100));
        info.setCcyCode("702");
        Response<QrCode> resp = new Response<>();
        sspService.qrCode(info, resp);

        Assert.assertEquals(info, resp.getTrxInfo());
        Assert.assertEquals("91", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Issuer system error", resp.getMsgResponse().getRespDesc());
    }

    @Test
    public void testQrCodeSspFailed() throws IOException {
        Mockito.when(sspClient.send(Mockito.any(ByteBuffer.class)))
                .thenReturn(ByteBuffer.wrap("0390002300400013MessageFormat".getBytes()));

        QrCode info = new QrCode();
        info.setTranAmt(BigInteger.valueOf(100));
        info.setCcyCode("702");
        Response<QrCode> resp = new Response<>();
        sspService.qrCode(info, resp);

        Assert.assertEquals(info, resp.getTrxInfo());
        Assert.assertEquals("30", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("MessageFormat", resp.getMsgResponse().getRespDesc());
    }

    @Test
    public void testQrCodeSspSuccess() throws IOException {
        Mockito.when(sspClient.send(Mockito.any(ByteBuffer.class)))
                .thenReturn(ByteBuffer.wrap("9310002010040003101011000615390801200142018100918204301800031560390002000400008Approved041000878945612042001510000000000066606500262018100918204300000000702306601620002010102121531104000441234567810000000000066652045411530315654041.015802CN5925Test Merchant 123456789016003BBM6106111   62280512000000007023070878945612630410060670003180".getBytes()));

        QrCode info = new QrCode();
        info.setTranAmt(BigInteger.valueOf(100));
        info.setCcyCode("702");
        Response<QrCode> resp = new Response<>();
        sspService.qrCode(info, resp);

        Assert.assertEquals(info, resp.getTrxInfo());
        Assert.assertEquals("00", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Approved", resp.getMsgResponse().getRespDesc());
        Assert.assertEquals("20181009182043000000007023", resp.getTrxInfo().getBankLsNo());
        Assert.assertEquals("0002010102121531104000441234567810000000000066652045411530315654041.015802CN5925Test Merchant 123456789016003BBM6106111   6228051200000000702307087894561263041006", resp.getTrxInfo().getPayLoad());
        Assert.assertEquals("180", resp.getTrxInfo().getTimeout());
    }

    @Test
    public void testRefundException() throws IOException {
        Mockito.when(sspClient.send(Mockito.any(ByteBuffer.class)))
                .thenThrow(new IOException("i/o error"));

        Refund info = new Refund();
        info.setTranAmt(BigInteger.valueOf(100));
        info.setCcyCode("702");
        Response<Refund> resp = new Response<>();
        sspService.refund(info, resp);

        Assert.assertEquals(info, resp.getTrxInfo());
        Assert.assertEquals("91", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Issuer system error", resp.getMsgResponse().getRespDesc());
    }

    @Test
    public void testRefundSspFailed() throws IOException {
        Mockito.when(sspClient.send(Mockito.any(ByteBuffer.class)))
                .thenReturn(ByteBuffer.wrap("0390002300400013MessageFormat".getBytes()));

        Refund info = new Refund();
        info.setTranAmt(BigInteger.valueOf(100));
        info.setCcyCode("702");
        Response<Refund> resp = new Response<>();
        sspService.refund(info, resp);

        Assert.assertEquals(info, resp.getTrxInfo());
        Assert.assertEquals("30", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("MessageFormat", resp.getMsgResponse().getRespDesc());
    }

    @Test
    public void testRefundSspSuccess() {
        // TODO 测试SSP返回成功
    }

    @Test
    public void testQueryException() throws IOException {
        Mockito.when(sspClient.send(Mockito.any(ByteBuffer.class)))
                .thenThrow(new IOException("i/o error"));

        Query info = new Query();
        info.setTranAmt(BigInteger.valueOf(100));
        info.setCcyCode("702");
        Response<Query> resp = new Response<>();
        sspService.query(info, resp);

        Assert.assertEquals(info, resp.getTrxInfo());
        Assert.assertEquals("91", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Issuer system error", resp.getMsgResponse().getRespDesc());
    }

    @Test
    public void testQuerySspFailed() throws IOException {
        Mockito.when(sspClient.send(Mockito.any(ByteBuffer.class)))
                .thenReturn(ByteBuffer.wrap("0390002300400013MessageFormat".getBytes()));

        Query info = new Query();
        info.setTranAmt(BigInteger.valueOf(100));
        info.setCcyCode("702");
        Response<Query> resp = new Response<>();
        sspService.query(info, resp);

        Assert.assertEquals(info, resp.getTrxInfo());
        Assert.assertEquals("30", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("MessageFormat", resp.getMsgResponse().getRespDesc());
    }

    @Test
    public void testQuerySspSuccess() {
        // TODO 测试SSP返回成功
    }
}