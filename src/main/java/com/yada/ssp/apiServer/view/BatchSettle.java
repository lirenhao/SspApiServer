package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BatchSettle extends TrxInfo {

    @NotEmpty
    @Size(min = 6, max = 6)
    private String batchNo; // 批次号

    private int trxCount; // 交易总笔数

    private BigInteger discountAmtSum; // 折扣总金额

    private BigInteger costAmtSum; // 成本总金额

    private BigInteger originalAmtSum; // 机构总金额

    private BigInteger trxAmtSum; // 交易总金额

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public int getTrxCount() {
        return trxCount;
    }

    public void setTrxCount(int trxCount) {
        this.trxCount = trxCount;
    }

    public BigInteger getDiscountAmtSum() {
        return discountAmtSum;
    }

    public void setDiscountAmtSum(BigInteger discountAmtSum) {
        this.discountAmtSum = discountAmtSum;
    }

    public BigInteger getCostAmtSum() {
        return costAmtSum;
    }

    public void setCostAmtSum(BigInteger costAmtSum) {
        this.costAmtSum = costAmtSum;
    }

    public BigInteger getOriginalAmtSum() {
        return originalAmtSum;
    }

    public void setOriginalAmtSum(BigInteger originalAmtSum) {
        this.originalAmtSum = originalAmtSum;
    }

    public BigInteger getTrxAmtSum() {
        return trxAmtSum;
    }

    public void setTrxAmtSum(BigInteger trxAmtSum) {
        this.trxAmtSum = trxAmtSum;
    }
}
