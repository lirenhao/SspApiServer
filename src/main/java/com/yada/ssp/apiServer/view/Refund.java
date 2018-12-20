package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Refund extends TrxInfo {

    @NotEmpty
    @Pattern(regexp = "^\\d{1,12}$")
    @JsonProperty("tranAmt")
    private String tranAmt;

    @NotEmpty
    @JsonProperty("ccyCode")
    private String ccyCode;

    @JsonProperty("channelId")
    private String channelId;

    @NotEmpty
    @Size(min = 24, max = 64)
    @JsonProperty("merTraceNo")
    private String merTraceNo;

    @NotEmpty
    @Size(min = 24, max = 64)
    @JsonProperty("originalMerTraceNo")
    private String originalMerTraceNo;

    @JsonProperty("bankLsNo")
    private String bankLsNo;

    @JsonProperty("channelTraceNo")
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
