package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ScanPay extends TrxInfo {

    @NotNull
    @Digits(integer = 12, fraction = 0)
    private BigInteger tranAmt; // 交易金额 单位:分

    private List<DiscountDetail> discountDetails; // 折扣信息

    private BigInteger originalAmt; // 机构金额

    private BigInteger costAmt; // 成本金额

    @NotEmpty
    @Size(min = 3, max = 3)
    private String ccyCode; // 交易币种

    private String channelId; // 交易渠道

    @NotEmpty
    @Size(min = 24, max = 64)
    private String merTraceNo; // 商户流水号

    private String bankLsNo; // 银行流水号

    @NotEmpty
    @Size(min = 1, max = 512)
    private String payLoad; // 客户付款码

    private String couponInfo; // 优惠券，暂不使用

    private String channelTraceNo; // 二维码渠道对应系统的流水号

    public BigInteger getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(BigInteger tranAmt) {
        this.tranAmt = tranAmt;
    }

    public List<DiscountDetail> getDiscountDetails() {
        return discountDetails;
    }

    public void setDiscountDetails(List<DiscountDetail> discountDetails) {
        this.discountDetails = discountDetails;
    }

    public BigInteger getOriginalAmt() {
        return originalAmt;
    }

    public void setOriginalAmt(BigInteger originalAmt) {
        this.originalAmt = originalAmt;
    }

    public BigInteger getCostAmt() {
        return costAmt;
    }

    public void setCostAmt(BigInteger costAmt) {
        this.costAmt = costAmt;
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

    public String getCouponInfo() {
        return couponInfo;
    }

    public void setCouponInfo(String couponInfo) {
        this.couponInfo = couponInfo;
    }

    public String getChannelTraceNo() {
        return channelTraceNo;
    }

    public void setChannelTraceNo(String channelTraceNo) {
        this.channelTraceNo = channelTraceNo;
    }
}
