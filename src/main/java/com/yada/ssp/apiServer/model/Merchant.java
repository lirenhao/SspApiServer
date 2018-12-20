package com.yada.ssp.apiServer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "T_B_MERCHANT")
public class Merchant {

    @Id
    @Column(name = "MERCHANT_ID")
    private String merNo;

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }
}
