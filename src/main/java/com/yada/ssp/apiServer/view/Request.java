package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.Valid;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Request <T extends TrxInfo> {

    @Valid
    @JsonProperty("msgInfo")
    private MsgInfo msgInfo;

    @Valid
    @JsonProperty("trxInfo")
    private T trxInfo;

    @Valid
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

    public CertificateSignature getCertificateSignature() {
        return certificateSignature;
    }

    public void setCertificateSignature(CertificateSignature certificateSignature) {
        this.certificateSignature = certificateSignature;
    }
}
