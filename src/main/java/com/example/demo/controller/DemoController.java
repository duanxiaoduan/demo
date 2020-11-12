package com.example.demo.controller;

import com.example.demo.dao.MongoDBDaoTest;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;
import com.example.demo.util.OptionalBean;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author duanxiaoduan
 * @version 2018/3/28
 */
@RequestMapping("/demo/")
@RestController
public class DemoController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private MongoDBDaoTest mongoDBDaoTest;

    @RequestMapping(value = "user", method = {RequestMethod.PUT, RequestMethod.POST})
    public UserInfo save(@RequestBody UserInfo userInfo) {
        return (UserInfo) userInfoService.save(userInfo).get();
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public UserInfo get(@PathVariable long id) {
        return (UserInfo) userInfoService.findById(id).get();
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public UserInfo getUser(@RequestParam(name = "id", required = true) long id) {
        UserInfo userInfo = new UserInfo();
        final String s = OptionalBean.ofNullable(userInfo).getBean(UserInfo::getUserName).get();
        return (UserInfo) userInfoService.findById(id).orElse(null);
    }

    @ApiOperation  (value = "删除用户", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "user/{id}", method = RequestMethod.DELETE)
    public Boolean delete(@PathVariable long id) {
        userInfoService.delete(id);
        return true;
    }

    @RequestMapping(value = "mongo/user", method = {RequestMethod.PUT, RequestMethod.POST})
    public void saveByMongo(@RequestBody UserInfo userInfo) {
        mongoDBDaoTest.save(userInfo);
    }

    @RequestMapping(value = "mongo/user/{id}", method = {RequestMethod.PUT, RequestMethod.POST})
    public UserInfo getByIdFromMongo(@PathVariable String id) {
        return mongoDBDaoTest.findById(UserInfo.class, id);
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //反射获取对
        final Constructor<UserInfo> ctor = UserInfo.class.getDeclaredConstructor();
        final Class<?>[] parameterTypes = ctor.getParameterTypes();
        if (!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers()) || !ctor.isAccessible()) {
            ctor.setAccessible(true);
        }
        //https://baidu.com
        UserInfo userInfo = ctor.newInstance(parameterTypes);
        System.out.println(userInfo);
        System.out.println(getUserInfo(userInfo));
        System.out.println(userInfo);
        System.out.println(new BigDecimal(0).compareTo(BigDecimal.ZERO));
    }

    private static UserInfo getUserInfo(UserInfo userInfo) {
        userInfo.setUserName("zhangsan");
        userInfo.setId(9L);
        return userInfo;
    }


}
