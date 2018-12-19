package com.yada.ssp.apiServer.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "T_B_MERAPI_ORG")
public class ApiOrg {

    @Id
    @Column
    private String orgId;

    @Column
    private String orgName;

    @Column
    private String publicKey;

    @OneToMany
    @JoinTable
    private List<String> merchants;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
