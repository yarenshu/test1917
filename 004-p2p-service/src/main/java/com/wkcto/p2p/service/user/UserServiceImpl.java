package com.wkcto.p2p.service.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.wkcto.p2p.constant.Constants;
import com.wkcto.p2p.mapper.user.UserMapper;
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
@Service(interfaceClass = UserService.class,version = "1.0.0",timeout = 15000)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public Integer queryAllUserCount() {

        Integer allUserCount = (Integer) redisTemplate.opsForValue().get(Constants.ALLUSERCOUNT);

        if (!ObjectUtils.allNotNull(allUserCount)){
            synchronized (this){

                allUserCount = (Integer) redisTemplate.opsForValue().get(Constants.ALLUSERCOUNT);

                if (!ObjectUtils.allNotNull(allUserCount)){

                    allUserCount = userMapper.selectAllUserCount();

                    redisTemplate.opsForValue().set(Constants.ALLUSERCOUNT, allUserCount, 15, TimeUnit.MINUTES);

                }
            }
        }



        return allUserCount;
    }
}
