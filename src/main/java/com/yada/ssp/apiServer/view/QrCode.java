package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.*;
import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class QrCode extends TrxInfo {

    @NotNull
    @Digits(integer = 12, fraction = 0)
    private BigInteger tranAmt; // 交易金额 单位:分

    @NotNull
    @Digits(integer = 3, fraction = 0)
    private int ccyCode; // 交易币种

    @NotEmpty
    @Size(min = 2, max = 2)
    private String channelId; // 交易渠道

    @NotEmpty
    @Size(min = 24, max = 64)
    private String merTraceNo; // 商户流水号

    private String bankLsNo; // 银行流水号,交易成功时返回

    private String payLoad; // 二维码,交易成功时返回

    private String timeout; // 二维码过期时间 单位:秒

    public BigInteger getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(BigInteger tranAmt) {
        this.tranAmt = tranAmt;
    }

    public int getCcyCode() {
        return ccyCode;
    }

    public void setCcyCode(int ccyCode) {
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

    public String getBankLsNo() {
        return bankLsNo;
    }

    public void setBankLsNo(String bankLsNo) {
        this.bankLsNo = bankLsNo;
    }

    public String getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(String payLoad) {
        this.payLoad = payLoad;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
}
