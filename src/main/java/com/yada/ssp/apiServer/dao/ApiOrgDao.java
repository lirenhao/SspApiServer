package com.yada.ssp.apiServer.dao;

import com.yada.ssp.apiServer.model.ApiOrg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiOrgDao extends JpaRepository<ApiOrg, String> {
}
