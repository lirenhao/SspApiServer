package com.yada.ssp.apiServer.service;

import com.yada.ssp.apiServer.dao.OrgKeyDao;
import com.yada.ssp.apiServer.model.OrgKey;
import com.yada.ssp.apiServer.net.SspClient;
import com.yada.ssp.apiServer.util.SignUtil;
import com.yada.ssp.apiServer.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrgKeyDao orgKeyDao;
    private final SspClient sspClient;

    @Value("${signature.private-key}")
    private String privateKey;

    @Autowired
    public ApiService(OrgKeyDao orgKeyDao, SspClient sspClient) {
        this.orgKeyDao = orgKeyDao;
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

        // 请求信息验签
        OrgKey orgKey = orgKeyDao.findById(req.getMsgInfo().getOrgId()).orElse(new OrgKey());
        if (orgKey.getPublicKey() != null && SignUtil.verify(req, sign, orgKey.getPublicKey())) {
            // 回调处理
            callback.handle(req.getTrxInfo(), resp);
        } else {
            resp.setTrxInfo(req.getTrxInfo());
            resp.setMsgResponse(new MsgResponse("A0", "Signature verification fails"));
            logger.warn("交易[{}]验签失败", req.getMsgInfo());
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
            // TODO 与SSP交互
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
