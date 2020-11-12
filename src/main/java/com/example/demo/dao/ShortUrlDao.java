package com.example.demo.dao;

import com.example.demo.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author duanxiaoduan
 * @version 2020/1/2
 */
@Repository
public interface ShortUrlDao  extends JpaRepository<ShortUrl, Long> {

    ShortUrl findByShortUrl(String shortUrl);

    ShortUrl findByUrlHash(final long urlHash);

}
