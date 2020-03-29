package com.wkcto.p2p.service;

import com.wkcto.p2p.model.loan.LoanInfo;

import java.util.List;
import java.util.Map;

/**
 * 雅人叔
 * 2020/2/21
 */
public interface LoanInfoService {
    Double queryHistoryAverageRate();

    List<LoanInfo> queryLoanInfoListByProductType(Map<String, Object> map);
}
