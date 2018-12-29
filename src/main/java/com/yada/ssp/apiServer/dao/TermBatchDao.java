package com.yada.ssp.apiServer.dao;

import com.yada.ssp.apiServer.model.TermBatch;
import com.yada.ssp.apiServer.model.TermBatchPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermBatchDao extends JpaRepository<TermBatch, TermBatchPK> {
    
}