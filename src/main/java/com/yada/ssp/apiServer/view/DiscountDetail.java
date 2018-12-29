package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DiscountDetail {

    private BigInteger discountAmt;

    private String discountNote;

    public BigInteger getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(BigInteger discountAmt) {
        this.discountAmt = discountAmt;
    }

    public String getDiscountNote() {
        return discountNote;
    }

    public void setDiscountNote(String discountNote) {
        this.discountNote = discountNote;
    }
}
