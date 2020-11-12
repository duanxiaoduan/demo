package com.example.demo.service;

import com.example.demo.service.base.AbstractService;

import java.util.Optional;

/**
 * @author duanxiaoduan
 * @version 2020/1/2
 */
public interface ShortUrlService<T> extends AbstractService {

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
     * findByShortUrl
     *
     * @param shortUrl
     * @return
     */
    Optional<T> findByShortUrl(final String shortUrl);
}
