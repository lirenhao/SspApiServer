package com.yada.ssp.apiServer.net;

import com.yada.ssp.apiServer.util.DateUtil;
import com.yada.ssp.apiServer.util.TlvPacker;

import java.nio.ByteBuffer;
import java.util.Map;

public class MockSspClient implements SspClient {

    @Override
    public ByteBuffer send(ByteBuffer req) {
        Map<String, String> reqData = TlvPacker.unPacker(new String(req.array()));
        String respData = "";
        switch (reqData.get("931")) {
            case "01":
                respData = qrCode(reqData);
                break;
            case "06":
                respData = scanPay(reqData);
                break;
            case "05":
                respData = refund(reqData);
                break;
            case "02":
                respData = query(reqData);
                break;
            default:

        }

        return ByteBuffer.wrap(respData.getBytes());
    }

    private String qrCode(Map<String, String> data) {
        data.put("039", "00");
        data.put("040", "Approved");
        data.put("065", "bankLsNo"); // bankLsNo
        data.put("066", "0002010102121531104000441234567810000000000066652045411530315654041.015802CN5925Test Merchant 123456789016003BBM6106111   6228051200000000702307087894561263041006"); // payLoad
        data.put("067", "180"); // timeout
        return TlvPacker.packer(data);
    }

    private String scanPay(Map<String, String> data) {
        data.put("039", "00");
        data.put("040", "Approved");
        data.put("070", "01"); // channelId
        data.put("074", "1000"); // originalAmt
        data.put("075", "10"); // costAmt
        data.put("076", "0"); // discountAmt
        data.put("077", "None"); // discountNote
        data.put("065", "bankLsNo"); // bankLsNo
        data.put("069", "channelTraceNo"); // channelTraceNo
        data.remove("066"); // payLoad
        return TlvPacker.packer(data);
    }

    private String refund(Map<String, String> data) {
        data.put("039", "00");
        data.put("040", "Approved");
        data.put("070", "01"); // channelId
        data.put("065", "bankLsNo"); // bankLsNo
        data.put("069", "channelTraceNo"); // channelTraceNo
        return TlvPacker.packer(data);
    }

    private String query(Map<String, String> data) {
        data.put("004", "1000"); // tranAmt
        data.put("018", "702"); // ccyCode
        data.put("039", "00"); // trxRespCode
        data.put("040", "Approved"); // trxRespDesc
        data.put("065", "bankLsNo"); // bankLsNo
        data.put("069", "channelTraceNo"); // channelTraceNo
        data.put("070", "01"); // channelId
        data.put("072", "originalMerTraceNo"); // originalMerTraceNo
        data.put("074", "1000"); // originalAmt
        data.put("075", "10"); // costAmt
        data.put("076", "0"); // discountAmt
        data.put("077", "None"); // discountNote
        return TlvPacker.packer(data);
    }
}