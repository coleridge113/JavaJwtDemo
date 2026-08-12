package com.luna.jwt_demo.service;

import org.springframework.stereotype.Service;

import com.luna.jwt_demo.entity.UserInfo;

@Service
public class JwtService {

    public String addUser(UserInfo userInfo) {
        return "foo";
    }
}
