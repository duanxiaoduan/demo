package com.example.demo.dao;

import com.example.demo.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author duanxiaoduan
 * @version 2018/3/29
 */
@Repository
public interface UserInfoDao extends CrudRepository<UserInfo, Long>{

}
