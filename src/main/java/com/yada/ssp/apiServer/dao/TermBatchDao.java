package com.yada.ssp.apiServer.dao;

import com.yada.ssp.apiServer.model.TermBatch;
import com.yada.ssp.apiServer.model.TermBatchPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TermBatchDao extends JpaRepository<TermBatch, TermBatchPK> {

    @Modifying
    @Transactional
    @Query("update TermBatch t set t.batchNo = ?1 where t.merchantId = ?2 and t.terminalId = ?3")
    void updateBatchNo(String batchNo, String merchantId, String terminalId);
}