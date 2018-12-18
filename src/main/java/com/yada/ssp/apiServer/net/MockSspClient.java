package com.yada.ssp.apiServer.net;

import com.yada.ssp.apiServer.util.TlvPacker;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class MockSspClient implements SspClient {

    @Override
    public ByteBuffer send(ByteBuffer req) {
        Map<String, String> reqData = TlvPacker.unPacker(new String(req.array()));
        Map<String, String> respData = new HashMap<>();
        respData.put("039", "00");
        respData.put("040", "Approved");
        switch (reqData.get("931")) {
            case "01":
                break;
            case "02":
                break;
            default:
                respData.put("039", "04");
                respData.put("040", "Pending");
        }

        return ByteBuffer.wrap(TlvPacker.packer(respData).getBytes());
    }
}