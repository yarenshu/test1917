package com.wkcto.p2p.service.loan;

import com.alibaba.dubbo.config.annotation.Service;
import com.wkcto.p2p.constant.Constants;
import com.wkcto.p2p.mapper.loan.BidInfoMapper;
import com.wkcto.p2p.mapper.loan.LoanInfoMapper;
import com.wkcto.p2p.service.BidInfoService;
import com.wkcto.p2p.service.LoanInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 雅人叔
 * 2020/2/21
 */
@Component
@Service(interfaceClass = BidInfoService.class,version = "1.0.0",timeout = 15000)
public class BidInfoServiceImpl implements BidInfoService {

    @Autowired
    private BidInfoMapper bidInfoMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public Long queryAllBidMoney() {
        Long allBidMoney = (Long) redisTemplate.opsForValue().get(Constants.ALLBIDMONEY);

        if (!ObjectUtils.allNotNull(allBidMoney)){
            synchronized (this){
                allBidMoney = (Long) redisTemplate.opsForValue().get(Constants.ALLBIDMONEY);
                if (!ObjectUtils.allNotNull(allBidMoney)){
                    allBidMoney = bidInfoMapper.selectAllBidMoney();

                    redisTemplate.opsForValue().set(Constants.ALLBIDMONEY, allBidMoney, 15, TimeUnit.MINUTES);
                }
            }
        }

        return allBidMoney;
    }
}
