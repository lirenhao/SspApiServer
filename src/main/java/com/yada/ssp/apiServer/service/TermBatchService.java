package com.yada.ssp.apiServer.service;

import com.yada.ssp.apiServer.dao.TermBatchDao;
import com.yada.ssp.apiServer.model.TermBatch;
import com.yada.ssp.apiServer.model.TermBatchPK;
import com.yada.ssp.apiServer.view.BatchNo;
import com.yada.ssp.apiServer.view.MsgResponse;
import com.yada.ssp.apiServer.view.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TermBatchService {

    private final TermBatchDao termBatchDao;

    @Autowired
    public TermBatchService(TermBatchDao termBatchDao) {
        this.termBatchDao = termBatchDao;
    }

    /**
     * 获取当前批次
     *
     * @param info 获取批次信息参数
     * @param resp 返回信息报文
     */
    public void getBatchNo(BatchNo info, Response<BatchNo> resp) {
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
        }
    }
}
