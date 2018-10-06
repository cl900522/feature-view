package com.acme.dubbo.provider;

import com.acme.dubbo.inter.UserService;

public class UserServiceProvider implements UserService {

    public String getMessage(String name) {
        return "I am " + name;
    }

}
