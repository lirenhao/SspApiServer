package com.yada.ssp.apiServer.view;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AccountFile extends TrxInfo {

    @NotEmpty
    @Size(min = 8, max = 8)
    private String settleDate; // 清算日期YYYYMMDD

    private String merchantId; // 商户号

    private String terminalId; // 终端号

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
}