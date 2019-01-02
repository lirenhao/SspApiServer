package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotEmpty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MsgInfo {

    @NotEmpty
    private String versionNo; // 版本号

    @NotEmpty
    private String timeStamp; // 时间戳

    @NotEmpty
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

    @Override
    public String toString() {
        return "MsgInfo{" + "versionNo='" + versionNo + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", orgId='" + orgId + '\'' +
                '}';
    }
}
