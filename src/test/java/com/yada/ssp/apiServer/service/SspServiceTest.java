package com.yada.ssp.apiServer.service;

import com.yada.ssp.apiServer.net.SspClient;
import com.yada.ssp.apiServer.view.QrCode;
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
    public void qrCode() throws IOException {
        Mockito.when(sspClient.send(Mockito.any(ByteBuffer.class)))
                .thenReturn(ByteBuffer.wrap("0390002300400013MessageFormat".getBytes()))
                .thenReturn(ByteBuffer.wrap("".getBytes()))
                .thenThrow(new IOException("i/o error"));

        QrCode info = new QrCode();
        info.setTranAmt(BigInteger.valueOf(100));
        info.setCcyCode(702);
        Response<QrCode> resp = new Response<>();
        sspService.qrCode(info, resp);

        Assert.assertEquals(info, resp.getTrxInfo());
        Assert.assertEquals("30", resp.getMsgResponse().getRespCode());
        Assert.assertEquals("MessageFormat", resp.getMsgResponse().getRespDesc());
    }

    @Test
    public void refund() {
    }

    @Test
    public void query() {
    }
}