package com.yada.ssp.apiServer.service;

import com.yada.ssp.apiServer.dao.CurCupqrcTranDao;
import com.yada.ssp.apiServer.model.CurCupqrcTran;
import com.yada.ssp.apiServer.view.AccInfoDetail;
import com.yada.ssp.apiServer.view.AccountFile;
import com.yada.ssp.apiServer.view.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TranService {

    private final CurCupqrcTranDao curCupqrcTranDao;

    @Autowired
    public TranService(CurCupqrcTranDao curCupqrcTranDao) {
        this.curCupqrcTranDao = curCupqrcTranDao;
    }

    void accountFile(AccountFile info, Response<AccountFile> resp) {
        String orgId = resp.getMsgInfo().getOrgId();
        // TODO 获取商户号
        String settleDate = info.getSettleDate();
        info.setAccInfoDetails(curCupqrcTranDao.
                findByLocalSettleDateAndMerchantIdIn(settleDate, new String[]{}).stream().
                map(this::tranToAcc).collect(Collectors.toList()));
        resp.setTrxInfo(info);
    }

    private String tranToAcc(CurCupqrcTran tran) {
        AccInfoDetail aid = new AccInfoDetail();
        aid.setMerchantId(tran.getMerchantId());
        aid.setTerminalId(tran.getTerminalId());
        aid.setBatchNo(tran.getBatchNo());
        return aid.toString();
    }
}
