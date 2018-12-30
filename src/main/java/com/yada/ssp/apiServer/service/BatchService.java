package com.yada.ssp.apiServer.service;

import com.yada.ssp.apiServer.dao.CurCupSuccessDao;
import com.yada.ssp.apiServer.dao.CurCupqrcTranDao;
import com.yada.ssp.apiServer.dao.TermBatchDao;
import com.yada.ssp.apiServer.model.CurCupSuccess;
import com.yada.ssp.apiServer.model.TermBatch;
import com.yada.ssp.apiServer.model.TermBatchPK;
import com.yada.ssp.apiServer.util.DateUtil;
import com.yada.ssp.apiServer.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TermBatchDao termBatchDao;
    private final CurCupSuccessDao curCupSuccessDao;
    private final CurCupqrcTranDao curCupqrcTranDao;

    @Autowired
    public BatchService(TermBatchDao termBatchDao, CurCupSuccessDao curCupSuccessDao, CurCupqrcTranDao curCupqrcTranDao) {
        this.termBatchDao = termBatchDao;
        this.curCupSuccessDao = curCupSuccessDao;
        this.curCupqrcTranDao = curCupqrcTranDao;
    }

    /**
     * 获取当前批次
     *
     * @param info 获取批次信息参数
     * @param resp 返回信息报文
     */
    void getBatchNo(BatchNo info, Response<BatchNo> resp) {
        TermBatch termBatch = termBatchDao
                .findById(new TermBatchPK(info.getMerchantId(), info.getTerminalId())).orElse(null);
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

    /**
     * 批次结算
     *
     * @param info 批次结算参数
     * @param resp 返回信息报文
     */
    @Transactional
    void batchSettle(BatchSettle info, Response<BatchSettle> resp) {
        TermBatch termBatch = termBatchDao
                .findById(new TermBatchPK(info.getMerchantId(), info.getTerminalId())).orElse(null);
        if (termBatch != null) {
            // 查询批次号是否是当前批次号
            if (info.getBatchNo().compareTo(termBatch.getBatchNo()) == 0) { // 当前的批次
                // 更新清算日期,更新批次号
                updateBatchSettle(DateUtil.getCurDate(), termBatch);
                queryBatchSettle(info);
                resp.setMsgResponse(new MsgResponse("00", "Approved"));
            } else if (info.getBatchNo().compareTo(termBatch.getBatchNo()) < 0) { // 以前的批次
                queryBatchSettle(info);
                resp.setMsgResponse(new MsgResponse("00", "Approved"));
            } else { // 未来的批次
                resp.setMsgResponse(new MsgResponse("12", "Invalid transaction"));
                logger.warn("商户[{}]和终端[{}]的批次号[{}]大于当前批次号[{}]",
                        info.getMerchantId(), info.getTerminalId(), info.getBatchNo(), termBatch.getBatchNo());
            }
        } else {
            resp.setMsgResponse(new MsgResponse("91", "Issuer system error"));
            logger.warn("没有查询到商户[{}]和终端[{}]的批次号", info.getMerchantId(), info.getTerminalId());
        }
        resp.setTrxInfo(info);
    }

    /**
     * 更新t_l_cupqrc_tran_list清算日期,更新批次号
     *
     * @param settleDate 清算日期
     * @param termBatch  批次信息
     */
    void updateBatchSettle(String settleDate, TermBatch termBatch) {
        curCupqrcTranDao.updateSettleDate(settleDate, termBatch.getMerchantId(),
                termBatch.getTerminalId(), termBatch.getBatchNo());
        // TODO 如何更新批次号
        termBatchDao.updateBatchNo(termBatch.getBatchNo() + 1,
                termBatch.getMerchantId(), termBatch.getTerminalId());
    }

    /**
     * @param info 批次结算参数
     */
    void queryBatchSettle(BatchSettle info) {
        List<CurCupSuccess> list = curCupSuccessDao.findByMerchantIdAndTerminalIdAndBatchNo(
                info.getMerchantId(), info.getTerminalId(), info.getBatchNo());
        info.setTrxCount(list.size());

        BigDecimal discountAmtSum = list.stream()
                .map(item -> "2".equals(item.getDebcreFlag()) ? item.getDiscountAmt().negate() : item.getDiscountAmt())
                .reduce(new BigDecimal(0), BigDecimal::add)
                .multiply(new BigDecimal(100));
        info.setDiscountAmtSum(discountAmtSum.toBigInteger());

        BigDecimal costAmtSum = list.stream()
                .map(item -> "2".equals(item.getDebcreFlag()) ? item.getCostAmt().negate() : item.getCostAmt())
                .reduce(new BigDecimal(0), BigDecimal::add)
                .multiply(new BigDecimal(100));
        info.setCostAmtSum(costAmtSum.toBigInteger());

        BigDecimal originalAmtSum = list.stream()
                .map(item -> "2".equals(item.getDebcreFlag()) ? item.getOriginalAmt().negate() : item.getOriginalAmt())
                .reduce(new BigDecimal(0), BigDecimal::add)
                .multiply(new BigDecimal(100));
        info.setOriginalAmtSum(originalAmtSum.toBigInteger());

        BigDecimal trxAmtSum = list.stream()
                .map(item -> "2".equals(item.getDebcreFlag()) ? item.getTranAmt().negate() : item.getTranAmt())
                .reduce(new BigDecimal(0), BigDecimal::add)
                .multiply(new BigDecimal(100));
        info.setTrxAmtSum(trxAmtSum.toBigInteger());
    }

    /**
     * 批次交易查询
     *
     * @param info 批次查询参数
     * @param resp 返回信息报文
     */
    void batchTranQuery(BatchQuery info, Response<BatchQuery> resp) {
        TermBatch termBatch = termBatchDao
                .findById(new TermBatchPK(info.getMerchantId(), info.getTerminalId())).orElse(null);
        if (termBatch != null) {
            if (null != info.getBatchNo() && !"".equals(info.getBatchNo())
                    && termBatch.getBatchNo().compareTo(info.getBatchNo()) < 0) { // 未来的批次
                resp.setMsgResponse(new MsgResponse("12", "Invalid transaction"));
                logger.warn("商户[{}]和终端[{}]的批次号[{}]大于当前批次号[{}]",
                        info.getMerchantId(), info.getTerminalId(), info.getBatchNo(), termBatch.getBatchNo());
            } else {
                queryBatchTran(info, termBatch.getBatchNo());
                resp.setMsgResponse(new MsgResponse("00", "Approved"));
            }
        } else {
            resp.setMsgResponse(new MsgResponse("91", "Issuer system error"));
            logger.warn("没有查询到商户[{}]和终端[{}]的批次号", info.getMerchantId(), info.getTerminalId());
        }
        resp.setTrxInfo(info);
    }

    /**
     * @param info    批次查询参数
     * @param batchNo 当前批次号
     */
    void queryBatchTran(BatchQuery info, String batchNo) {
        List<CurCupSuccess> list = curCupSuccessDao.findByMerchantIdAndTerminalIdAndBatchNo(
                info.getMerchantId(), info.getTerminalId(),
                (null != info.getBatchNo() && !"".equals(info.getBatchNo())) ? info.getBatchNo() : batchNo);
        info.setTrxInfoDetail(list.stream().map(this::ccsToTid).collect(Collectors.toList()));
    }

    private TrxInfoDetail ccsToTid(CurCupSuccess ccs) {
        TrxInfoDetail tid = new TrxInfoDetail();
        tid.setTranAmt(ccs.getTranAmt().multiply(new BigDecimal(100)).toBigInteger());
        tid.setCcyCode(ccs.getTranCcyCode());
        DiscountDetail dd = new DiscountDetail();
        dd.setDiscountAmt(ccs.getDiscountAmt().multiply(new BigDecimal(100)).toBigInteger());
        tid.setDiscountDetails(Collections.singletonList(dd));
        tid.setOriginalAmt(ccs.getOriginalAmt().multiply(new BigDecimal(100)).toBigInteger());
        tid.setCostAmt(ccs.getCostAmt().multiply(new BigDecimal(100)).toBigInteger());
        tid.setChannelId(ccs.getChannelId());
        tid.setMerTraceNo(ccs.getMerTraceNo());
        tid.setOriginalMerTraceNo(ccs.getOriginalMerTraceNo());
        tid.setBankLsNo(ccs.getBatchNo());
        tid.setChannelTraceNo(ccs.getChannelTraceNo());
        return tid;
    }
}
