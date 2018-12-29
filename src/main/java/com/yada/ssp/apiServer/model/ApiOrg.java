package com.yada.ssp.apiServer.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "T_B_MERAPI_ORG")
public class ApiOrg {

    @Id
    @Column
    private String orgId;

    @Column
    private String orgName;

    @Column
    private String publicKey;

    @OneToMany
    @JoinTable(name = "T_B_APIORG_MERLIST", joinColumns = @JoinColumn(name = "ORG_ID"), inverseJoinColumns = @JoinColumn(name = "MERCHANT_ID"))
    private Set<Merchant> merchants = new HashSet<>();

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Set<Merchant> getMerchants() {
        return merchants;
    }

    public void setMerchants(Set<Merchant> merchants) {
        this.merchants = merchants;
    }
}
