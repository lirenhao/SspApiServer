package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BatchQuery extends TrxInfo {

    private String batchNo; // 批次号 (选填)

    private List<TrxInfoDetail> trxInfoDetail;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public List<TrxInfoDetail> getTrxInfoDetail() {
        return trxInfoDetail;
    }

    public void setTrxInfoDetail(List<TrxInfoDetail> trxInfoDetail) {
        this.trxInfoDetail = trxInfoDetail;
    }
}
