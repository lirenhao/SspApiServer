package com.yada.ssp.apiServer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_ORG_MER_ACCOUNTFILE")
public class AccountInfo {

    @Id
    @Column
    private String lsId;
    @Column
    private String orgId;
    @Column
    private String settleDate;
    @Column
    private String merchantId;
    @Column
    private String terminalId;
    @Column
    private String tranDate;
    @Column
    private String tranTime;
    @Column
    private String tranType;
    @Column
    private String originalAmt;
    @Column
    private String discountAmt;
    @Column
    private String costAmt;
    @Column
    private String tranAmt;
    @Column
    private String fee;
    @Column
    private String settleAmt;
    @Column
    private String merTraceNo;
    @Column
    private String originalMerTraceNo;
    @Column
    private String bankLsNo;
    @Column
    private String batchNo;

    public String getLsId() {
        return lsId;
    }

    public void setLsId(String lsId) {
        this.lsId = lsId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
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

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getTranTime() {
        return tranTime;
    }

    public void setTranTime(String tranTime) {
        this.tranTime = tranTime;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getOriginalAmt() {
        return originalAmt;
    }

    public void setOriginalAmt(String originalAmt) {
        this.originalAmt = originalAmt;
    }

    public String getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(String discountAmt) {
        this.discountAmt = discountAmt;
    }

    public String getCostAmt() {
        return costAmt;
    }

    public void setCostAmt(String costAmt) {
        this.costAmt = costAmt;
    }

    public String getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(String tranAmt) {
        this.tranAmt = tranAmt;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getSettleAmt() {
        return settleAmt;
    }

    public void setSettleAmt(String settleAmt) {
        this.settleAmt = settleAmt;
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

    public String getBankLsNo() {
        return bankLsNo;
    }

    public void setBankLsNo(String bankLsNo) {
        this.bankLsNo = bankLsNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    @Override
    public String toString() {
        return merchantId + '|' +
                terminalId + '|' +
                tranDate + '|' +
                tranTime + '|' +
                tranType + '|' +
                originalAmt + '|' +
                discountAmt + '|' +
                costAmt + '|' +
                tranAmt + '|' +
                fee + '|' +
                settleAmt + '|' +
                merTraceNo + '|' +
                originalMerTraceNo + '|' +
                bankLsNo + '|' +
                batchNo + '|' +
                "\r\n";
    }
}
