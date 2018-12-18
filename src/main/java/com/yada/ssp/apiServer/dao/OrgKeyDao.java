package com.yada.ssp.apiServer.dao;

import com.yada.ssp.apiServer.model.OrgKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgKeyDao extends JpaRepository<OrgKey, String> {
}
