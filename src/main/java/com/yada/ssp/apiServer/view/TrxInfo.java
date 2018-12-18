package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TrxInfo {

    @NotEmpty
    @Size(min = 15, max = 15)
    @JsonProperty("MerchantId")
    private String merchantId; // 商户号

    @NotEmpty
    @Size(min = 8, max = 8)
    @JsonProperty("TerminalId")
    private String terminalId; // 终端号

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
