package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BatchSettle extends TrxInfo {

    @NotEmpty
    @Size(min = 6, max = 6)
    private String batchNo; // 批次号

    private String trxCount; // 交易总笔数

    private String trxAmtSum; // 交易总金额

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getTrxCount() {
        return trxCount;
    }

    public void setTrxCount(String trxCount) {
        this.trxCount = trxCount;
    }

    public String getTrxAmtSum() {
        return trxAmtSum;
    }

    public void setTrxAmtSum(String trxAmtSum) {
        this.trxAmtSum = trxAmtSum;
    }
}
