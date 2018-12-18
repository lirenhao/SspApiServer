package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.Valid;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Request <T extends TrxInfo> {

    @Valid
    @JsonProperty("MsgInfo")
    private MsgInfo msgInfo;

    @Valid
    @JsonProperty("TrxInfo")
    private T trxInfo;

    @Valid
    @JsonProperty("CertificateSignature")
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
