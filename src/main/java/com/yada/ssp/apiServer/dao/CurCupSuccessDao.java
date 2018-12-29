package com.yada.ssp.apiServer.dao;

import com.yada.ssp.apiServer.model.CurCupSuccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurCupSuccessDao extends JpaRepository<CurCupSuccess, String> {

    List<CurCupSuccess> findByMerchantIdAndTerminalIdAndBatchNo(String merchantId, String terminalId, String batchNo);
}