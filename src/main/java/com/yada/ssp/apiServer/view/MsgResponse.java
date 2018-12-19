package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MsgResponse {

    public MsgResponse(String respCode, String respDesc) {
        this.respCode = respCode;
        this.respDesc = respDesc;
    }

    @JsonProperty("respCode")
    private String respCode;
    @JsonProperty("respDesc")
    private String respDesc;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }
}
