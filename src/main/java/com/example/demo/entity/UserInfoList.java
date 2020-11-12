package com.example.demo.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author duanxiaoduan
 * @version 2019/9/3
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@ToString
public class UserInfoList implements Serializable {

    private List<UserInfo> infoList;


}
