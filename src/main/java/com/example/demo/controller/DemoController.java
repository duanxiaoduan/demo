package com.example.demo.controller;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author duanxiaoduan
 * @version 2018/3/28
 */
@RequestMapping("/demo/")
@RestController
public class DemoController {

    @Resource
    private UserInfoService userInfoService;

    @RequestMapping(value = "user", method = { RequestMethod.PUT, RequestMethod.POST })
    public UserInfo save(@RequestBody UserInfo userInfo) {
        return userInfoService.save(userInfo);
    }

    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public UserInfo get(@PathVariable long id) {
        return userInfoService.findById(id);
    }

    @RequestMapping(value = "user/{id}", method = RequestMethod.DELETE)
    public Boolean delete(@PathVariable long id) {
        userInfoService.delete(id);
        return true;
    }
}
