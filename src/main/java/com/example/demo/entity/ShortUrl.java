package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author duanxiaoduan
 * @version 2020/1/2
 */
@Entity
@Table(name = "tb_short_url")
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@ToString
public class ShortUrl {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * urlHash
     */
    @Column(name = "url_hash")
    private long urlHash;

    /**
     * shortUrl
     */
    @Column(name = "short_url")
    private String shortUrl;

    /**
     * url
     */
    @Column(name = "url")
    private String url;

    /**
     * time
     */
    @Column(name = "time")
    private long time;
}
