package com.example.demo.service.impl;

import com.example.demo.dao.UserInfoDao;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author duanxiaoduan
 * @version 2018/3/29
 */
@Component
public class UserInfoServiceImpl implements UserInfoService{

    @Resource
    private UserInfoDao userInfoDao;

    /**
     * findById
     *
     * @param id
     * @return
     */
    @Override
    public UserInfo findById(final Long id) {
        return userInfoDao.findById(id).get();
    }

    /**
     * save
     * @param userInfo
     * @return
     */
    @Override
    public UserInfo save(UserInfo userInfo) {
        return userInfoDao.save(userInfo);
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
}
