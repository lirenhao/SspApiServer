package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class AccountFile extends TrxInfo {

    @NotEmpty
    @Size(min = 8, max = 8)
    private String settleDate; // 清算日期YYYYMMDD

    @JsonIgnore
    private List<AccInfoDetail> accInfoDetails;

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public List<AccInfoDetail> getAccInfoDetails() {
        return accInfoDetails;
    }

    public void setAccInfoDetails(List<AccInfoDetail> accInfoDetails) {
        this.accInfoDetails = accInfoDetails;
    }
}