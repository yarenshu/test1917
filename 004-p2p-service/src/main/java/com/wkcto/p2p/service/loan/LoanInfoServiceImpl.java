package com.wkcto.p2p.service.loan;

import com.alibaba.dubbo.config.annotation.Service;
import com.wkcto.p2p.mapper.loan.LoanInfoMapper;
import com.wkcto.p2p.model.loan.LoanInfo;
import com.wkcto.p2p.service.LoanInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.zookeeper.data.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 雅人叔
 * 2020/2/21
 */
@Component
@Service(interfaceClass = LoanInfoService.class,version = "1.0.0",timeout = 15000)
public class LoanInfoServiceImpl implements LoanInfoService {

    @Autowired
    private LoanInfoMapper loanInfoMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public Double queryHistoryAverageRate() {
        Double historyAverageRate = (Double)redisTemplate.opsForValue().get("historyAverageRate");

        if (!ObjectUtils.allNotNull(historyAverageRate)){
            historyAverageRate = (Double)redisTemplate.opsForValue().get("historyAverageRate");

            if (!ObjectUtils.allNotNull(historyAverageRate)){
                historyAverageRate = loanInfoMapper.selectHistoryAverageRate();

                redisTemplate.opsForValue().set("historyAverageRate", historyAverageRate, 15, TimeUnit.MINUTES);

            }
        }



        return historyAverageRate;
    }

    @Override
    public List<LoanInfo> queryLoanInfoListByProductType(Map<String, Object> map) {



        return loanInfoMapper.selectLoanInfoListByProductType(map);
    }
}
