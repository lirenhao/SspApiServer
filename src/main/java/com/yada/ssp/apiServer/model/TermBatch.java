package com.yada.ssp.apiServer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity(name = "T_B_TERMINAL_BATCH")
@IdClass(TermBatchPK.class)
public class TermBatch {

    @Id
    @Column
    private String merchantId;
    @Id
    @Column
    private String terminalId;
    @Column
    private String batchNo;

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
}
