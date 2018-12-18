package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Refund extends TrxInfo {

    @NotEmpty
    @Size(min = 1, max = 12)
    @JsonProperty("TranAmt")
    private String tranAmt;

    @NotEmpty
    @JsonProperty("CcyCode")
    private String ccyCode;

    @JsonProperty("ChannelId")
    private String channelId;

    @NotEmpty
    @Size(min = 24, max = 64)
    @JsonProperty("MerTraceNo")
    private String merTraceNo;

    @NotEmpty
    @Size(min = 24, max = 64)
    @JsonProperty("OriginalMerTraceNo")
    private String originalMerTraceNo;

    @JsonProperty("BankLsNo")
    private String bankLsNo;

    @JsonProperty("ChannelTraceNo")
    private String channelTraceNo;

    public String getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(String tranAmt) {
        this.tranAmt = tranAmt;
    }

    public String getCcyCode() {
        return ccyCode;
    }

    public void setCcyCode(String ccyCode) {
        this.ccyCode = ccyCode;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
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

    public String getChannelTraceNo() {
        return channelTraceNo;
    }

    public void setChannelTraceNo(String channelTraceNo) {
        this.channelTraceNo = channelTraceNo;
    }
}
