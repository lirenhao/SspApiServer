package com.yada.ssp.apiServer.service;

import com.yada.ssp.apiServer.dao.ApiOrgDao;
import com.yada.ssp.apiServer.model.ApiOrg;
import com.yada.ssp.apiServer.model.Merchant;
import com.yada.ssp.apiServer.util.DateUtil;
import com.yada.ssp.apiServer.util.SignUtil;
import com.yada.ssp.apiServer.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ApiService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ApiOrgDao apiOrgDao;
    private final SspService sspService;

    @Value("${signature.private-key}")
    private String privateKey;

    @Autowired
    public ApiService(ApiOrgDao apiOrgDao, SspService sspService) {
        this.apiOrgDao = apiOrgDao;
        this.sspService = sspService;
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
        resp.getMsgInfo().setTimeStamp(DateUtil.getCurDateTime());
        // 响应信息签名
        signature.setSignature(SignUtil.sign(resp, privateKey));
        resp.setCertificateSignature(signature);
        return resp;
    }

    public Response<BatchNo> batchNo(Request<BatchNo> req) {
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
        return handle(req, sspService::qrCode);
    }

    public Response<ScanPay> scanPay(Request<ScanPay> req) {
        return handle(req, (info, resp) -> {
            // TODO 与SSP交互
        });
    }

    public Response<Refund> refund(Request<Refund> req) {
        return handle(req, sspService::refund);
    }

    public Response<Query> query(Request<Query> req) {
        return handle(req, sspService::query);
    }

    public Response<BatchQuery> batchQuery(Request<BatchQuery> req) {
        return handle(req, (info, resp) -> {
            // TODO 与SSP交互
        });
    }
}
