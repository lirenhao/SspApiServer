package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MsgInfo {

    @NotEmpty
    @JsonProperty("versionNo")
    private String versionNo; // 版本号

    @NotEmpty
    @JsonProperty("timeStamp")
    private String timeStamp; // 时间戳

    @NotEmpty
    @JsonProperty("orgId")
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
