package com.yada.ssp.apiServer.net;

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
        return "";
    }

    private String refund(Map<String, String> data) {
        return "";
    }

    private String query(Map<String, String> data) {
        return "";
    }
}