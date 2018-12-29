package com.yada.ssp.apiServer.model;

import java.io.Serializable;

public class TermBatchPK implements Serializable {

    private String merchantId;
    private String terminalId;

    public TermBatchPK() {
    }

    public TermBatchPK(String merchantId, String terminalId) {
        this.merchantId = merchantId;
        this.terminalId = terminalId;
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
}
