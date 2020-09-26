package com.atguigu.security.service;

import com.atguigu.security.util.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyPasswordEncoder implements PasswordEncoder {

    public String encode(CharSequence rawPassword) {
        return MD5Util.digest(rawPassword.toString());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
