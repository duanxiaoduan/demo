package com.example.demo.service.impl;

import com.example.demo.dao.UserInfoDao;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static com.example.demo.util.LazyString.lazy;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author duanxiaoduan
 * @version 2018/3/29
 */
@Component
public class UserInfoServiceImpl implements UserInfoService<UserInfo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Resource
    private UserInfoDao userInfoDao;

    /**
     * findById
     *
     * @param id
     * @return
     */
    @Override
    public Optional<UserInfo> findById(final Long id) {
        getLogger().debug("findById, id : {}", lazy(()-> id));
        return userInfoDao.findById(id);
    }

    /**
     * save
     * @param userInfo
     * @return
     */
    @Override
    public Optional<UserInfo> save(UserInfo userInfo) {
        Assert.notNull(userInfo, "userInfo must is not null");
        getLogger().info("save, {}", lazy(userInfo::toString));
        return Optional.of(userInfoDao.save(userInfo));
    }

    /**
     * delete
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        userInfoDao.deleteById(id);
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }
}
