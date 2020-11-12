package com.example.demo.service.impl;

import com.example.demo.dao.ShortUrlDao;
import com.example.demo.entity.ShortUrl;
import com.example.demo.service.ShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author duanxiaoduan
 * @version 2020/1/2
 */
@Slf4j
@Component
public class ShortUrlServiceImpl implements ShortUrlService<ShortUrl> {

    @Resource
    private ShortUrlDao shortUrlDao;
    /**
     * findById
     *
     * @param id
     * @return
     */
    @Override
    public Optional findById(Long id) {
        return shortUrlDao.findById(id);
    }

    /**
     * save
     *
     * @param o
     * @return
     */
    @Override
    public Optional<ShortUrl> save(ShortUrl o) {
        ShortUrl shortUrl = shortUrlDao.findByUrlHash(o.getUrlHash());
        if (null != shortUrl) {
            if (shortUrl.getUrl().equals(o.getUrl())) {
                return Optional.of(shortUrl);
            } else {
                long urlHash = o.getUrlHash();
                while (null == shortUrlDao.findByUrlHash(++urlHash)) {
                    o.setUrlHash(urlHash);
                }
            }
        }
        return Optional.of(shortUrlDao.save(o));
    }

    /**
     * findByShortUrl
     *
     * @param shortUrl
     * @return
     */
    @Override
    public Optional findByShortUrl(String shortUrl) {
        ShortUrl byShortUrl = shortUrlDao.findByShortUrl(shortUrl);
        return Optional.of(null == byShortUrl ? new ShortUrl() : byShortUrl);
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
