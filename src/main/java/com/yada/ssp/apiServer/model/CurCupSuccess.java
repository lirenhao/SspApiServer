package com.yada.ssp.apiServer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "V_SUCCESS_CUR_CUP_LIST")
public class CurCupSuccess {

    @Id
    @Column
    private String lsId;
    @Column
    private String merchantId;
    @Column
    private String terminalId;
    @Column
    private String tranType;
    @Column
    private String batchNo;
    @Column
    private String tranCcyCode;
    @Column
    private BigDecimal discountAmt;
    @Column
    private BigDecimal costAmt;
    @Column
    private BigDecimal originalAmt;
    @Column
    private BigDecimal tranAmt;
    @Column
    private String merTraceNo;
    @Column
    private String originalMerTraceNo;
    @Column
    private String channelTraceNo;
    @Column
    private String debcreFlag;
    @Column
    private String channelId;

    public String getLsId() {
        return lsId;
    }

    public void setLsId(String lsId) {
        this.lsId = lsId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getTranCcyCode() {
        return tranCcyCode;
    }

    public void setTranCcyCode(String tranCcyCode) {
        this.tranCcyCode = tranCcyCode;
    }

    public BigDecimal getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(BigDecimal discountAmt) {
        this.discountAmt = discountAmt;
    }

    public BigDecimal getCostAmt() {
        return costAmt;
    }

    public void setCostAmt(BigDecimal costAmt) {
        this.costAmt = costAmt;
    }

    public BigDecimal getOriginalAmt() {
        return originalAmt;
    }

    public void setOriginalAmt(BigDecimal originalAmt) {
        this.originalAmt = originalAmt;
    }

    public BigDecimal getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(BigDecimal tranAmt) {
        this.tranAmt = tranAmt;
    }

    public String getMerTraceNo() {
        return merTraceNo;
    }

    public void setMerTraceNo(String merTraceNo) {
        this.merTraceNo = merTraceNo;
    }

    public String getOriginalMerTraceNo() {
        return originalMerTraceNo;
    }

    public void setOriginalMerTraceNo(String originalMerTraceNo) {
        this.originalMerTraceNo = originalMerTraceNo;
    }

    public String getChannelTraceNo() {
        return channelTraceNo;
    }

    public void setChannelTraceNo(String channelTraceNo) {
        this.channelTraceNo = channelTraceNo;
    }

    public String getDebcreFlag() {
        return debcreFlag;
    }

    public void setDebcreFlag(String debcreFlag) {
        this.debcreFlag = debcreFlag;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
