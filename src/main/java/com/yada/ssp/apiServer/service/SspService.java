package com.yada.ssp.apiServer.service;

import com.yada.ssp.apiServer.net.SspClient;
import com.yada.ssp.apiServer.util.TlvPacker;
import com.yada.ssp.apiServer.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class SspService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SspClient sspClient;

    @Autowired
    public SspService(SspClient sspClient) {
        this.sspClient = sspClient;
    }

    void qrCode(QrCode info, Response<QrCode> resp) {
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("931", "01");
        reqMap.put("004", info.getTranAmt().toString());
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
                info.setExpTime(Integer.parseInt(respMap.get("067")));
            } else {
                logger.warn("获取付款码失败,返回码是[{}],提示信息是[{}]", respMap.get("039"), respMap.get("040"));
            }
        } catch (IOException e) {
            resp.setMsgResponse(new MsgResponse("91", "Issuer system error"));
            logger.warn("获取付款码异常,请求报文是[{}],异常信息是[{}]", reqStr, e.getMessage());
        }
        resp.setTrxInfo(info);
    }

    void scanPay(ScanPay info, Response<ScanPay> resp) {
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("931", "06");
        reqMap.put("004", info.getTranAmt().toString());
        reqMap.put("018", info.getCcyCode());
        reqMap.put("041", info.getTerminalId());
        reqMap.put("042", info.getMerchantId());
        reqMap.put("068", info.getMerTraceNo());
        reqMap.put("066", info.getPayLoad());
        String reqStr = TlvPacker.packer(reqMap);
        try {
            logger.info("反扫交易的请求报文是[{}]", reqStr);
            ByteBuffer respBuffer = sspClient.send(ByteBuffer.wrap(reqStr.getBytes()));
            Map<String, String> respMap = TlvPacker.unPacker(new String(respBuffer.array()));
            resp.setMsgResponse(new MsgResponse(respMap.get("039"), respMap.get("040")));
            if ("00".equals(respMap.get("039"))) {
                DiscountDetail discountDetail = new DiscountDetail();
                discountDetail.setDiscountAmt(new BigInteger(respMap.get("076")));
                discountDetail.setDiscountNote(respMap.get("077"));
                info.setDiscountDetails(Collections.singletonList(discountDetail));
                info.setOriginalAmt(new BigInteger(respMap.get("074")));
                info.setCostAmt(new BigInteger(respMap.get("075")));
                info.setChannelId(respMap.get("070"));
                info.setBankLsNo(respMap.get("065"));
                info.setChannelTraceNo(respMap.get("069"));
            } else {
                logger.warn("反扫交易失败,返回码是[{}],提示信息是[{}]", respMap.get("039"), respMap.get("040"));
            }
        } catch (IOException e) {
            resp.setMsgResponse(new MsgResponse("91", "Issuer system error"));
            logger.warn("反扫交易异常,请求报文是[{}],异常信息是[{}]", reqStr, e.getMessage());
        }
        info.setPayLoad(null);
        resp.setTrxInfo(info);
    }

    void refund(Refund info, Response<Refund> resp) {
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("931", "05");
        reqMap.put("004", info.getTranAmt().toString());
        reqMap.put("018", info.getCcyCode());
        reqMap.put("041", info.getTerminalId());
        reqMap.put("042", info.getMerchantId());
        reqMap.put("068", info.getMerTraceNo());
        reqMap.put("071", info.getOriginalMerTraceNo());
        String reqStr = TlvPacker.packer(reqMap);
        try {
            logger.info("发起退货的请求报文是[{}]", reqStr);
            ByteBuffer respBuffer = sspClient.send(ByteBuffer.wrap(reqStr.getBytes()));
            Map<String, String> respMap = TlvPacker.unPacker(new String(respBuffer.array()));
            resp.setMsgResponse(new MsgResponse(respMap.get("039"), respMap.get("040")));
            if ("00".equals(respMap.get("039"))) {
                info.setChannelId(respMap.get("070"));
                info.setBankLsNo(respMap.get("065"));
                info.setChannelTraceNo(respMap.get("069"));
            } else {
                logger.warn("获取付款码失败,返回码是[{}],提示信息是[{}]", respMap.get("039"), respMap.get("040"));
            }
        } catch (IOException e) {
            resp.setMsgResponse(new MsgResponse("91", "Issuer system error"));
            logger.warn("发起退货异常,请求报文是[{}],异常信息是[{}]", reqStr, e.getMessage());
        }
        resp.setTrxInfo(info);
    }

    void query(Query info, Response<Query> resp) {
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("931", "02");
        reqMap.put("041", info.getTerminalId());
        reqMap.put("042", info.getMerchantId());
        reqMap.put("068", info.getMerTraceNo());
        String reqStr = TlvPacker.packer(reqMap);
        try {
            logger.info("发起查询的请求报文是[{}]", reqStr);
            ByteBuffer respBuffer = sspClient.send(ByteBuffer.wrap(reqStr.getBytes()));
            Map<String, String> respMap = TlvPacker.unPacker(new String(respBuffer.array()));
            resp.setMsgResponse(new MsgResponse("00", "Approved"));

            info.setTrxRespCode(respMap.get("039"));
            info.setTrxRespDesc(respMap.get("040"));
            if ("00".equals(respMap.get("039"))) {
                info.setTranAmt(new BigInteger(respMap.get("004")));
                info.setCcyCode(respMap.get("018"));
                DiscountDetail discountDetail = new DiscountDetail();
                discountDetail.setDiscountAmt(new BigInteger(respMap.get("076")));
                discountDetail.setDiscountNote(respMap.get("077"));
                info.setDiscountDetails(Collections.singletonList(discountDetail));
                info.setOriginalAmt(new BigInteger(respMap.get("074")));
                info.setCostAmt(new BigInteger(respMap.get("075")));
                info.setChannelId(respMap.get("070"));
                info.setOriginalMerTraceNo(respMap.get("072"));
                info.setBankLsNo(respMap.get("065"));
                info.setChannelTraceNo(respMap.get("069"));
            } else {
                logger.warn("发起查询失败,返回码是[{}],提示信息是[{}]", respMap.get("039"), respMap.get("040"));
            }
        } catch (IOException e) {
            resp.setMsgResponse(new MsgResponse("91", "Issuer system error"));
            logger.warn("发起查询异常,请求报文是[{}],异常信息是[{}]", reqStr, e.getMessage());
        }
        resp.setTrxInfo(info);
    }
}
