package com.example.demo.common.config;

/**
 * @author duanxiaoduan
 * @version 2020/1/16
 */
public class MyClassLoader extends ClassLoader {


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        return super.findClass(name);
    }
}
