package com.yada.ssp.apiServer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_L_CUR_CUPQRC_TRAN_LIST")
public class CurCupqrcTran {

    @Id
    @Column
    private String lsId;
    @Column
    private String merchantId;
    @Column
    private String terminalId;
    @Column
    private String batchNo;
    @Column
    private String localSettleDate;

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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getLocalSettleDate() {
        return localSettleDate;
    }

    public void setLocalSettleDate(String localSettleDate) {
        this.localSettleDate = localSettleDate;
    }
}
