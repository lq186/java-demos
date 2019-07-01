package com.lq186.admin.util

import org.springframework.util.DigestUtils

final class PasswordUtils {

    def static md5DigestAsHex(String password, String salt) {
        byte[] bytesValue = new String("MD5:" + password + salt).getBytes("utf-8")
        return DigestUtils.md5DigestAsHex(bytesValue)
    }

    def static isMatched(String password, String salt, String passwordMd5) {
        return md5DigestAsHex(password, salt) == passwordMd5
    }
}
