package com.example.demo.service;

import com.example.demo.entity.UserInfo;


public interface UserInfoService {

    public UserInfo findByUsername(String username);
}
