package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotEmpty;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MsgInfo {

    @NotEmpty
    @JsonProperty("VersionNo")
    private String versionNo; // 版本号

    @NotEmpty
    @JsonProperty("TimeStamp")
    private String timeStamp; // 时间戳

    @NotEmpty
    @JsonProperty("TranId")
    private String tranId; // 交易类型ID

    @NotEmpty
    @JsonProperty("OrgId")
    private String orgId; // 机构ID

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
