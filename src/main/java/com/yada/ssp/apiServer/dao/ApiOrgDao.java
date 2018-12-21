package com.yada.ssp.apiServer.dao;

import com.yada.ssp.apiServer.model.ApiOrg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiOrgDao extends JpaRepository<ApiOrg, String> {

    Optional<ApiOrg> findByOrgIdAndMerchants_MerNo(String orgId, String merNo);
}