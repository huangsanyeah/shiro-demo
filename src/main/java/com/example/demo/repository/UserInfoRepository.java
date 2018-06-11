package com.example.demo.repository;

import com.example.demo.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;


public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {

    public UserInfo findByUsername(String username);

    public UserInfo save(UserInfo userInfo);
}
