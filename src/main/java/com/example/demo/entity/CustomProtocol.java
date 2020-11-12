package com.example.demo.entity;

import lombok.*;

/**
 * @author duanxiaoduan
 * @version 2019-06-02
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@ToString
public class CustomProtocol {

    private String id;

    private long time;

}
