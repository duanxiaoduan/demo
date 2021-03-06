package com.example.demo.service;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.base.AbstractService;
import org.slf4j.Logger;

import java.util.Optional;

/**
 * @author duanxiaoduan
 * @version 2018/3/29
 */
public interface UserInfoService<T> extends AbstractService {

    /**
     * findById
     *
     * @param id
     * @return
     */
    Optional<T> findById(final Long id);

    /**
     * save
     *
     * @param t
     * @return
     */
    Optional<T> save(T t);

    /**
     * delete
     *
     * @param id
     */
    void delete(final Long id);

    /**
     * sqrt
     * @param a
     * @return
     */
    default double sqrt(int a) {
        return Math.sqrt(a);
    }

}
