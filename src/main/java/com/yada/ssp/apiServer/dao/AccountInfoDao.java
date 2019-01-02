package com.yada.ssp.apiServer.dao;

import com.yada.ssp.apiServer.model.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountInfoDao extends JpaRepository<AccountInfo, String> {

    List<AccountInfo> findByOrgIdAndSettleDate(String orgId, String settleDate);
}