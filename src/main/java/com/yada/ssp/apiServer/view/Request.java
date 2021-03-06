package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Request<T extends TrxInfo> {

    @Valid
    @NotNull
    private MsgInfo msgInfo;

    @Valid
    @NotNull
    private T trxInfo;

    @Valid
    @NotNull
    private CertificateSignature certificateSignature;

    @JsonIgnore
    @NotEmpty
    private String data; // 原始报文

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
