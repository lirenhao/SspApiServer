package com.yada.ssp.apiServer.service;

import com.yada.ssp.apiServer.dao.AccountInfoDao;
import com.yada.ssp.apiServer.model.AccountInfo;
import com.yada.ssp.apiServer.view.AccountFile;
import com.yada.ssp.apiServer.view.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TranService {

    private final AccountInfoDao accountInfoDao;

    @Autowired
    public TranService(AccountInfoDao accountInfoDao) {
        this.accountInfoDao = accountInfoDao;
    }

    void accountFile(AccountFile info, Response<AccountFile> resp) {
        String orgId = resp.getMsgInfo().getOrgId();
        String settleDate = info.getSettleDate();
        info.setAccInfoDetails(accountInfoDao.findByOrgIdAndSettleDate(orgId, settleDate)
                .stream().map(AccountInfo::toString).collect(Collectors.toList()));
        resp.setTrxInfo(info);
    }
}
