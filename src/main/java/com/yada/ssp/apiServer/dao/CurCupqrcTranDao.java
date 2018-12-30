package com.yada.ssp.apiServer.dao;

import com.yada.ssp.apiServer.model.CurCupqrcTran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CurCupqrcTranDao extends JpaRepository<CurCupqrcTran, String> {

    @Modifying
    @Transactional
    @Query("update CurCupqrcTran t set t.localSettleDate = ?1 where t.merchantId = ?2 and t.terminalId = ?3 and t.batchNo = ?4")
    void updateSettleDate(String settleDate, String merchantId, String terminalId, String batchNo);

    List<CurCupqrcTran> findByLocalSettleDateAndMerchantIdIn(String settleDate, String[] merNos);
}