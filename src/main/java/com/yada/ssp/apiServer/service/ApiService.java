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
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Service
@Validated
public class ApiService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ApiOrgDao apiOrgDao;
    private final SspService sspService;
    private final BatchService batchService;
    private final TranService tranService;

    @Autowired
    public ApiService(ApiOrgDao apiOrgDao, SspService sspService, BatchService batchService, TranService tranService) {
        this.apiOrgDao = apiOrgDao;
        this.sspService = sspService;
        this.batchService = batchService;
        this.tranService = tranService;
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

        ApiOrg apiOrg = apiOrgDao.findById(req.getMsgInfo().getOrgId()).orElse(new ApiOrg());
        if (req.getTrxInfo() instanceof AccountFile ||
                apiOrg.getMerchants().stream().map(Merchant::getMerNo).collect(Collectors.toSet())
                        .contains(req.getTrxInfo().getMerchantId())) {
            String sign = req.getCertificateSignature().getSignature();
            // 在signature标签填充“00000000”
            String data = req.getData().replace(sign, "00000000");
            // 请求信息验签
            if (apiOrg.getPublicKey() != null && SignUtil.verify(data, sign, apiOrg.getPublicKey())) {
                // 回调处理
                callback.handle(req.getTrxInfo(), resp);
            } else {
                resp.setTrxInfo(req.getTrxInfo());
                resp.setMsgResponse(new MsgResponse("A0", "Signature verification failed"));
                logger.warn("交易[{}]验签失败", req.getMsgInfo().toString());
            }
        } else {
            resp.setTrxInfo(req.getTrxInfo());
            resp.setMsgResponse(new MsgResponse("03", "Invalid merchant"));
            logger.warn("机构商户不匹配[{}]", req.getMsgInfo().toString());
        }
        resp.getMsgInfo().setTimeStamp(DateUtil.getCurDateTime());
        // 响应信息签名
        if (apiOrg.getPrivateKey() != null) {
            signature.setSignature(SignUtil.sign(resp, apiOrg.getPrivateKey()));
        }
        resp.setCertificateSignature(signature);
        return resp;
    }

    public Response<BatchNo> batchNo(@Valid Request<BatchNo> req) {
        return handle(req, batchService::getBatchNo);
    }

    public Response<BatchSettle> batchSettle(@Valid Request<BatchSettle> req) {
        return handle(req, batchService::batchSettle);
    }

    public Response<QrCode> qrCode(@Valid Request<QrCode> req) {
        return handle(req, sspService::qrCode);
    }

    public Response<ScanPay> scanPay(@Valid Request<ScanPay> req) {
        return handle(req, sspService::scanPay);
    }

    public Response<Refund> refund(@Valid Request<Refund> req) {
        return handle(req, sspService::refund);
    }

    public Response<Query> query(@Valid Request<Query> req) {
        return handle(req, sspService::query);
    }

    public Response<BatchQuery> batchQuery(@Valid Request<BatchQuery> req) {
        return handle(req, batchService::batchTranQuery);
    }

    /**
     * 对账文件下载不验证商户号、终端号
     *
     * @param req     请求参数
     * @param msgInfo 基本信息
     * @return 对账文件数据
     */
    public Response<AccountFile> accountFile(Request<AccountFile> req, @Valid MsgInfo msgInfo) {
        return handle(req, tranService::accountFile);
    }
}
