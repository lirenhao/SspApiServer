package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.*;
import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Refund extends TrxInfo {

    @NotNull
    @Digits(integer = 12, fraction = 0)
    private BigInteger tranAmt; // 交易金额 单位:分

    @NotEmpty
    @Size(min = 3, max = 3)
    private String ccyCode; // 交易币种

    private String channelId; // 交易渠道

    @NotEmpty
    @Size(min = 24, max = 64)
    private String merTraceNo; // 商户流水号

    @NotEmpty
    @Size(min = 24, max = 64)
    private String originalMerTraceNo; // 原消费交易的商户流水号

    private String bankLsNo; // 银行流水号

    private String channelTraceNo; // 二维码渠道对应系统的流水号

    public BigInteger getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(BigInteger tranAmt) {
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
