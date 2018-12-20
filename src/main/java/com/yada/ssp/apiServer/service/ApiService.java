package com.yada.ssp.apiServer.service;

import com.yada.ssp.apiServer.dao.ApiOrgDao;
import com.yada.ssp.apiServer.model.ApiOrg;
import com.yada.ssp.apiServer.model.Merchant;
import com.yada.ssp.apiServer.net.SspClient;
import com.yada.ssp.apiServer.util.SignUtil;
import com.yada.ssp.apiServer.util.TlvPacker;
import com.yada.ssp.apiServer.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApiService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ApiOrgDao apiOrgDao;
    private final SspClient sspClient;

    @Value("${signature.private-key}")
    private String privateKey;

    @Autowired
    public ApiService(ApiOrgDao apiOrgDao, SspClient sspClient) {
        this.apiOrgDao = apiOrgDao;
        this.sspClient = sspClient;
    }

    interface Callback<T extends TrxInfo> {
        void handle(T info, Response<T> resp);
    }

    <T extends TrxInfo> Response<T> handle(Request<T> req, Callback<T> callback) {
        CertificateSignature signature = new CertificateSignature();
        signature.setSignature("00000000");

        Response<T> resp = new Response<>();
        resp.setMsgInfo(req.getMsgInfo());
        resp.setCertificateSignature(signature);

        String sign = req.getCertificateSignature().getSignature();
        // 在signature标签填充“00000000”
        req.setCertificateSignature(signature);

        ApiOrg apiOrg = apiOrgDao.findById(req.getMsgInfo().getOrgId()).orElse(new ApiOrg());
        if (apiOrg.getMerchants().stream().map(Merchant::getMerNo).collect(Collectors.toSet()).contains(req.getTrxInfo().getMerchantId())) {
            // 请求信息验签
            if (apiOrg.getPublicKey() != null && SignUtil.verify(req, sign, apiOrg.getPublicKey())) {
                // 回调处理
                callback.handle(req.getTrxInfo(), resp);
            } else {
                resp.setTrxInfo(req.getTrxInfo());
                resp.setMsgResponse(new MsgResponse("A0", "Signature verification fails"));
                logger.warn("交易[{}]验签失败", req.getMsgInfo().toString());
            }
        } else {
            resp.setTrxInfo(req.getTrxInfo());
            resp.setMsgResponse(new MsgResponse("A0", "Merchant No. Error"));
            logger.warn("机构商户不匹配[{}]", req.getMsgInfo().toString());
        }
        // 响应信息签名
        signature.setSignature(SignUtil.sign(resp, privateKey));
        resp.setCertificateSignature(signature);
        return resp;
    }

    public Response<UpdateBatch> updateBatch(Request<UpdateBatch> req) {
        return handle(req, (info, resp) -> {
            // TODO 与SSP交互
        });
    }

    public Response<BatchSettle> batchSettle(Request<BatchSettle> req) {
        return handle(req, (info, resp) -> {
            // TODO 与SSP交互
        });
    }

    public Response<QrCode> qrCode(Request<QrCode> req) {
        return handle(req, (info, resp) -> {
            Map<String, String> reqMap = new HashMap<>();
            reqMap.put("931", "01");
            reqMap.put("004", info.getTranAmt());
            reqMap.put("018", info.getCcyCode());
            reqMap.put("041", info.getTerminalId());
            reqMap.put("042", info.getMerchantId());
            reqMap.put("068", info.getMerTraceNo());
            reqMap.put("070", info.getChannelId());
            String reqStr = TlvPacker.packer(reqMap);
            try {
                logger.info("获取付款码的请求报文是[{}]", reqStr);
                // 发起生成二维码请求
                ByteBuffer respBuffer = sspClient.send(ByteBuffer.wrap(reqStr.getBytes()));
                Map<String, String> respMap = TlvPacker.unPacker(new String(respBuffer.array()));
                resp.setMsgResponse(new MsgResponse(respMap.get("039"), respMap.get("040")));
                if ("00".equals(respMap.get("039"))) {
                    info.setBankLsNo(respMap.get("065"));
                    info.setPayLoad(respMap.get("066"));
                    // TODO 超时时间
                } else {
                    logger.warn("获取付款码失败,返回码是[{}],提示信息是[{}]", respMap.get("039"), respMap.get("040"));
                }
            } catch (IOException e) {
                resp.setMsgResponse(new MsgResponse("91", "Issuer system error"));
                logger.warn("获取付款码异常,请求报文是[{}],异常信息是[{}]", reqStr, e.getMessage());
            }
            resp.setTrxInfo(info);
        });
    }

    public Response<ScanPay> scanPay(Request<ScanPay> req) {
        return handle(req, (info, resp) -> {
            // TODO 与SSP交互
        });
    }

    public Response<Refund> refund(Request<Refund> req) {
        return handle(req, (info, resp) -> {
            // TODO 与SSP交互
        });
    }

    public Response<Query> query(Request<Query> req) {
        return handle(req, (info, resp) -> {
            // TODO 与SSP交互
        });
    }

    public Response<Notify> notify(Request<Notify> req) {
        return handle(req, (info, resp) -> {
            // TODO 与SSP交互
        });
    }

    public Response<BatchQuery> batchQuery(Request<BatchQuery> req) {
        return handle(req, (info, resp) -> {
            // TODO 与SSP交互
        });
    }
}
