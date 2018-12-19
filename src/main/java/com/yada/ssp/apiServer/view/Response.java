package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Response <T extends TrxInfo> {

    @JsonProperty("msgInfo")
    private MsgInfo msgInfo;

    @JsonProperty("trxInfo")
    private T trxInfo;

    @JsonProperty("msgResponse")
    private MsgResponse msgResponse;

    @JsonProperty("certificateSignature")
    private CertificateSignature certificateSignature;

    public MsgInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MsgInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    public T getTrxInfo() {
        return trxInfo;
    }

    public void setTrxInfo(T trxInfo) {
        this.trxInfo = trxInfo;
    }

    public MsgResponse getMsgResponse() {
        return msgResponse;
    }

    public void setMsgResponse(MsgResponse msgResponse) {
        this.msgResponse = msgResponse;
    }

    public CertificateSignature getCertificateSignature() {
        return certificateSignature;
    }

    public void setCertificateSignature(CertificateSignature certificateSignature) {
        this.certificateSignature = certificateSignature;
    }
}
