package com.yada.ssp.apiServer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yada.ssp.apiServer.dao.ApiOrgDao;
import com.yada.ssp.apiServer.model.ApiOrg;
import com.yada.ssp.apiServer.model.Merchant;
import com.yada.ssp.apiServer.util.SignUtil;
import com.yada.ssp.apiServer.view.*;
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
@SpringBootTest(classes = ApiService.class)
public class ApiServiceTest {

    private final String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKAUZV+tjiNBKhlBZbKBnzeugpdYPhh5PbHanjV0aQ+LF7vetPYhbTiCVqA3a+Chmge44+prlqd3qQCYra6OYIe7oPVq4mETa1c/7IuSlKJgxC5wMqYKxYydb1eULkrs5IvvtNddx+9O/JlyM5sTPosgFHOzr4WqkVtQ71IkR+HrAgMBAAECgYAkQLo8kteP0GAyXAcmCAkA2Tql/8wASuTX9ITD4lsws/VqDKO64hMUKyBnJGX/91kkypCDNF5oCsdxZSJgV8owViYWZPnbvEcNqLtqgs7nj1UHuX9S5yYIPGN/mHL6OJJ7sosOd6rqdpg6JRRkAKUV+tmN/7Gh0+GFXM+ug6mgwQJBAO9/+CWpCAVoGxCA+YsTMb82fTOmGYMkZOAfQsvIV2v6DC8eJrSa+c0yCOTa3tirlCkhBfB08f8U2iEPS+Gu3bECQQCrG7O0gYmFL2RX1O+37ovyyHTbst4s4xbLW4jLzbSoimL235lCdIC+fllEEP96wPAiqo6dzmdH8KsGmVozsVRbAkB0ME8AZjp/9Pt8TDXD5LHzo8mlruUdnCBcIo5TMoRG2+3hRe1dHPonNCjgbdZCoyqjsWOiPfnQ2Brigvs7J4xhAkBGRiZUKC92x7QKbqXVgN9xYuq7oIanIM0nz/wq190uq0dh5Qtow7hshC/dSK3kmIEHe8z++tpoLWvQVgM538apAkBoSNfaTkDZhFavuiVl6L8cWCoDcJBItip8wKQhXwHp0O3HLg10OEd14M58ooNfpgt+8D8/8/2OOFaR0HzA+2Dm";
    private final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgFGVfrY4jQSoZQWWygZ83roKXWD4YeT2x2p41dGkPixe73rT2IW04glagN2vgoZoHuOPqa5and6kAmK2ujmCHu6D1auJhE2tXP+yLkpSiYMQucDKmCsWMnW9XlC5K7OSL77TXXcfvTvyZcjObEz6LIBRzs6+FqpFbUO9SJEfh6wIDAQAB";

    @MockBean
    private ApiOrgDao apiOrgDao;
    @MockBean
    private SspService sspService;
    @MockBean
    private BatchService batchService;
    @MockBean
    private TranService tranService;
    @Autowired
    private ApiService apiService;

    private ApiService.Callback<ApiTest> callback = (info, resp) -> {
        Assert.assertEquals("00000000", resp.getCertificateSignature().getSignature());
        Assert.assertNull(resp.getTrxInfo());

        resp.setTrxInfo(info);
        resp.setMsgResponse(new MsgResponse("00", "Approved"));
    };

    @Test
    public void testHandleNotKey() throws JsonProcessingException {
        Mockito.when(apiOrgDao.findById(Mockito.anyString())).thenReturn(Optional.empty());

        CertificateSignature certSign = new CertificateSignature();
        certSign.setSignature("00000000");

        Request<ApiTest> req = new Request<>();
        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setOrgId("0001");
        req.setMsgInfo(msgInfo);

        ApiTest trxInfo = new ApiTest();
        trxInfo.setMerchantId("123456789012345");
        req.setTrxInfo(trxInfo);

        req.setCertificateSignature(certSign);
        CertificateSignature reqSign = new CertificateSignature();
        reqSign.setSignature(SignUtil.sign(req, privateKey));
        req.setCertificateSignature(reqSign);
        req.setData(SignUtil.objToJson(req));

        Response<ApiTest> resp = apiService.handle(req, callback);
        Assert.assertEquals(msgInfo, resp.getMsgInfo());
        Assert.assertEquals(trxInfo, resp.getTrxInfo());
        Assert.assertEquals("03", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Invalid merchant", resp.getMsgResponse().getRespDesc());
        Assert.assertEquals("00000000", resp.getCertificateSignature().getSignature());
    }

    @Test
    public void testHandleVerifyFailed() throws JsonProcessingException {
        ApiOrg orgKey = new ApiOrg();
        orgKey.setOrgId("0001");
        orgKey.setPublicKey(publicKey);
        orgKey.setPrivateKey(privateKey);
        Merchant mer = new Merchant();
        mer.setMerNo("123456789012345");
        orgKey.getMerchants().add(mer);
        Mockito.when(apiOrgDao.findById(Mockito.anyString())).thenReturn(Optional.of(orgKey));

        CertificateSignature certSign = new CertificateSignature();
        certSign.setSignature("00000000");

        Request<ApiTest> req = new Request<>();
        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setOrgId("0001");
        req.setMsgInfo(msgInfo);

        ApiTest trxInfo = new ApiTest();
        trxInfo.setMerchantId("123456789012345");
        req.setTrxInfo(trxInfo);

        CertificateSignature reqSign = new CertificateSignature();
        reqSign.setSignature(SignUtil.sign(req, privateKey));
        req.setCertificateSignature(reqSign);
        req.setData(SignUtil.objToJson(req));

        Response<ApiTest> resp = apiService.handle(req, callback);
        Assert.assertEquals(msgInfo, resp.getMsgInfo());
        Assert.assertEquals(trxInfo, resp.getTrxInfo());
        Assert.assertEquals("A0", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Signature verification failed", resp.getMsgResponse().getRespDesc());
        String respSign = resp.getCertificateSignature().getSignature();
        resp.setCertificateSignature(certSign);
        Assert.assertTrue(SignUtil.verify(resp, respSign, publicKey));
    }

    @Test
    public void testHandleSuccess() throws JsonProcessingException {
        ApiOrg orgKey = new ApiOrg();
        orgKey.setOrgId("0001");
        orgKey.setPublicKey(publicKey);
        orgKey.setPrivateKey(privateKey);
        Merchant mer = new Merchant();
        mer.setMerNo("123456789012345");
        orgKey.getMerchants().add(mer);
        Mockito.when(apiOrgDao.findById(Mockito.anyString())).thenReturn(Optional.of(orgKey));

        CertificateSignature certSign = new CertificateSignature();
        certSign.setSignature("00000000");

        Request<ApiTest> req = new Request<>();
        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setOrgId("0001");
        req.setMsgInfo(msgInfo);

        ApiTest trxInfo = new ApiTest();
        trxInfo.setMerchantId("123456789012345");
        req.setTrxInfo(trxInfo);

        req.setCertificateSignature(certSign);
        CertificateSignature reqSign = new CertificateSignature();
        reqSign.setSignature(SignUtil.sign(req, privateKey));
        req.setCertificateSignature(reqSign);
        req.setData(SignUtil.objToJson(req));

        Response<ApiTest> resp = apiService.handle(req, callback);
        Assert.assertEquals(msgInfo, resp.getMsgInfo());
        Assert.assertEquals(trxInfo, resp.getTrxInfo());
        Assert.assertEquals("00", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("Approved", resp.getMsgResponse().getRespDesc());
        String respSign = resp.getCertificateSignature().getSignature();
        resp.setCertificateSignature(certSign);
        Assert.assertTrue(SignUtil.verify(resp, respSign, publicKey));
    }
}