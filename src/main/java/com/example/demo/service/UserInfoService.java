package com.example.demo.service;

import com.example.demo.entity.UserInfo;

/**
 * @author duanxiaoduan
 * @version 2018/3/29
 */
public interface UserInfoService {

    /**
     * findById
     * @param id
     * @return
     */
    UserInfo findById(final Long id);

    /**
     * save
     * @param userInfo
     * @return
     */
    UserInfo save(UserInfo userInfo);

    /**
     * delete
     * @param id
     */
    void delete(final Long id);
}
