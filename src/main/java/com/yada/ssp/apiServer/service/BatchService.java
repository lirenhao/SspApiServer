package com.yada.ssp.apiServer.service;

import com.yada.ssp.apiServer.dao.TermBatchDao;
import com.yada.ssp.apiServer.model.TermBatch;
import com.yada.ssp.apiServer.model.TermBatchPK;
import com.yada.ssp.apiServer.view.BatchNo;
import com.yada.ssp.apiServer.view.MsgResponse;
import com.yada.ssp.apiServer.view.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TermBatchDao termBatchDao;

    @Autowired
    public BatchService(TermBatchDao termBatchDao) {
        this.termBatchDao = termBatchDao;
    }

    /**
     * 获取当前批次
     *
     * @param info 获取批次信息参数
     * @param resp 返回信息报文
     */
    void getBatchNo(BatchNo info, Response<BatchNo> resp) {
        TermBatch termBatch = termBatchDao
                .findById(new TermBatchPK(info.getMerchantId(), info.getTerminalId()))
                .orElse(null);
        if (termBatch != null) {
            info.setBatchNo(termBatch.getBatchNo());
            resp.setTrxInfo(info);
            resp.setMsgResponse(new MsgResponse("00", "Approved"));
        } else {
            resp.setTrxInfo(info);
            resp.setMsgResponse(new MsgResponse("91", "Issuer system error"));
            logger.warn("没有查询到商户[{}]和终端[{}]的批次号", info.getMerchantId(), info.getTerminalId());
        }
    }
}
