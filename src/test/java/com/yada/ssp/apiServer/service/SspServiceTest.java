package com.yada.ssp.apiServer.service;

import com.yada.ssp.apiServer.net.MockSspClient;
import com.yada.ssp.apiServer.net.SspClient;
import com.yada.ssp.apiServer.util.TlvPacker;
import com.yada.ssp.apiServer.view.*;
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
import java.util.HashMap;
import java.util.Map;

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
        Response<QrCode> resp = new Response<>();
        sspService.qrCode(info, resp);

        Assert.assertEquals(info, resp.getTrxInfo());
        Assert.assertEquals("30", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("MessageFormat", resp.getMsgResponse().getRespDesc());
    }

    @Test
    public void testQrCodeSspSuccess() throws IOException {
        MockSspClient mockSsp = new MockSspClient();
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("931", "01");
        reqMap.put("004", "1000");
        reqMap.put("018", "702");
        reqMap.put("041", "12345678");
        reqMap.put("042", "123456789012345");
        reqMap.put("068", "201901021408230001");
        reqMap.put("070", "01");
        Mockito.when(sspClient.send(Mockito.any(ByteBuffer.class)))
                .thenReturn(mockSsp.send(ByteBuffer.wrap(TlvPacker.packer(reqMap).getBytes())));

        QrCode info = new QrCode();
        info.setTranAmt(BigInteger.valueOf(1000));
        info.setCcyCode("702");
        info.setMerchantId("123456789012345");
        info.setTerminalId("12345678");
        info.setChannelId("01");
        info.setMerTraceNo("201901021408230001");
        Response<QrCode> resp = new Response<>();
        sspService.qrCode(info, resp);

        Assert.assertEquals(info, resp.getTrxInfo());
        Assert.assertEquals("00", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Approved", resp.getMsgResponse().getRespDesc());
        Assert.assertEquals("bankLsNo", resp.getTrxInfo().getBankLsNo());
        Assert.assertEquals("0002010102121531104000441234567810000000000066652045411530315654041.015802CN5925Test Merchant 123456789016003BBM6106111   6228051200000000702307087894561263041006", resp.getTrxInfo().getPayLoad());
        Assert.assertEquals(180, resp.getTrxInfo().getExpTime());
    }

    @Test
    public void testScanPayException() throws IOException {
        Mockito.when(sspClient.send(Mockito.any(ByteBuffer.class)))
                .thenThrow(new IOException("i/o error"));

        ScanPay info = new ScanPay();
        info.setTranAmt(BigInteger.valueOf(100));
        Response<ScanPay> resp = new Response<>();
        sspService.scanPay(info, resp);

        Assert.assertEquals(info, resp.getTrxInfo());
        Assert.assertEquals("91", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Issuer system error", resp.getMsgResponse().getRespDesc());
    }

    @Test
    public void testScanPayFailed() throws IOException {
        Mockito.when(sspClient.send(Mockito.any(ByteBuffer.class)))
                .thenReturn(ByteBuffer.wrap("0390002300400013MessageFormat".getBytes()));

        ScanPay info = new ScanPay();
        info.setTranAmt(BigInteger.valueOf(100));
        Response<ScanPay> resp = new Response<>();
        sspService.scanPay(info, resp);

        Assert.assertEquals(info, resp.getTrxInfo());
        Assert.assertEquals("30", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("MessageFormat", resp.getMsgResponse().getRespDesc());
    }

    @Test
    public void testScanPaySuccess() throws IOException {
        MockSspClient mockSsp = new MockSspClient();
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("931", "06");
        reqMap.put("004", "1000");
        reqMap.put("041", "12345678");
        reqMap.put("042", "123456789012345");
        reqMap.put("068", "201901021408230001");
        reqMap.put("066", "payLoad");
        Mockito.when(sspClient.send(Mockito.any(ByteBuffer.class)))
                .thenReturn(mockSsp.send(ByteBuffer.wrap(TlvPacker.packer(reqMap).getBytes())));

        ScanPay info = new ScanPay();
        info.setTerminalId("12345678");
        info.setMerchantId("123456789012345");
        info.setTranAmt(BigInteger.valueOf(1000));
        info.setCcyCode("702");
        info.setMerTraceNo("201901021408230001");
        info.setPayLoad("payLoad");
        Response<ScanPay> resp = new Response<>();
        sspService.scanPay(info, resp);

        Assert.assertEquals(info, resp.getTrxInfo());
        Assert.assertEquals("00", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Approved", resp.getMsgResponse().getRespDesc());
        Assert.assertEquals("1000", info.getTranAmt().toString());
        Assert.assertEquals("702", info.getCcyCode());
        Assert.assertEquals("1000", info.getOriginalAmt().toString());
        Assert.assertEquals("10", info.getCostAmt().toString());
        Assert.assertEquals("01", info.getChannelId());
        Assert.assertEquals("bankLsNo", info.getBankLsNo());
        Assert.assertEquals("channelTraceNo", info.getChannelTraceNo());
    }

    @Test
    public void testRefundException() throws IOException {
        Mockito.when(sspClient.send(Mockito.any(ByteBuffer.class)))
                .thenThrow(new IOException("i/o error"));

        Refund info = new Refund();
        info.setTranAmt(BigInteger.valueOf(100));
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
        Response<Refund> resp = new Response<>();
        sspService.refund(info, resp);

        Assert.assertEquals(info, resp.getTrxInfo());
        Assert.assertEquals("30", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("MessageFormat", resp.getMsgResponse().getRespDesc());
    }

    @Test
    public void testRefundSspSuccess() throws IOException {
        MockSspClient mockSsp = new MockSspClient();
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("931", "05");
        reqMap.put("004", "1000");
        reqMap.put("018", "702");
        reqMap.put("041", "12345678");
        reqMap.put("042", "123456789012345");
        reqMap.put("068", "201901021408230002");
        reqMap.put("071", "201901021408230001");
        Mockito.when(sspClient.send(Mockito.any(ByteBuffer.class)))
                .thenReturn(mockSsp.send(ByteBuffer.wrap(TlvPacker.packer(reqMap).getBytes())));

        Refund info = new Refund();
        info.setTranAmt(BigInteger.valueOf(1000));
        info.setCcyCode("702");
        info.setTerminalId("12345678");
        info.setMerchantId("123456789012345");
        info.setMerTraceNo("201901021408230002");
        info.setOriginalMerTraceNo("201901021408230001");
        Response<Refund> resp = new Response<>();
        sspService.refund(info, resp);

        Assert.assertEquals(info, resp.getTrxInfo());
        Assert.assertEquals("00", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Approved", resp.getMsgResponse().getRespDesc());
        Assert.assertEquals("bankLsNo", resp.getTrxInfo().getBankLsNo());
        Assert.assertEquals("channelTraceNo", resp.getTrxInfo().getChannelTraceNo());
    }

    @Test
    public void testQueryException() throws IOException {
        Mockito.when(sspClient.send(Mockito.any(ByteBuffer.class)))
                .thenThrow(new IOException("i/o error"));

        Query info = new Query();
        info.setTranAmt(BigInteger.valueOf(100));
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
        Response<Query> resp = new Response<>();
        sspService.query(info, resp);

        Assert.assertEquals(info, resp.getTrxInfo());
        Assert.assertEquals("00", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Approved", resp.getMsgResponse().getRespDesc());
        Assert.assertEquals("30", resp.getTrxInfo().getTrxRespCode());
        Assert.assertEquals("MessageFormat", resp.getTrxInfo().getTrxRespDesc());
    }

    @Test
    public void testQuerySspSuccess() throws IOException {
        MockSspClient mockSsp = new MockSspClient();
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("931", "02");
        reqMap.put("041", "12345678");
        reqMap.put("042", "123456789012345");
        reqMap.put("068", "201901021408230001");
        Mockito.when(sspClient.send(Mockito.any(ByteBuffer.class)))
                .thenReturn(mockSsp.send(ByteBuffer.wrap(TlvPacker.packer(reqMap).getBytes())));

        Query info = new Query();
        info.setTerminalId("12345678");
        info.setMerchantId("123456789012345");
        info.setMerTraceNo("201901021408230001");
        Response<Query> resp = new Response<>();
        sspService.query(info, resp);

        Assert.assertEquals(info, resp.getTrxInfo());
        Assert.assertEquals("00", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Approved", resp.getMsgResponse().getRespDesc());
        Assert.assertEquals("1000", info.getTranAmt().toString());
        Assert.assertEquals("702", info.getCcyCode());
        Assert.assertEquals("1000", info.getOriginalAmt().toString());
        Assert.assertEquals("10", info.getCostAmt().toString());
        Assert.assertEquals("01", info.getChannelId());
        Assert.assertEquals("originalMerTraceNo", info.getOriginalMerTraceNo());
        Assert.assertEquals("bankLsNo", info.getBankLsNo());
        Assert.assertEquals("channelTraceNo", info.getChannelTraceNo());
        Assert.assertEquals("00", info.getTrxRespCode());
        Assert.assertEquals("Approved", info.getTrxRespDesc());
    }
}