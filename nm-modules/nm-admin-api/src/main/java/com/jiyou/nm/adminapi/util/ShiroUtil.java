package com.jiyou.nm.adminapi.util;

import com.jiyou.nm.model.entity.WasUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;

public class ShiroUtil {

    /**
     * 获取当前登陆用户
     *
     * @return
     */
    public static WasUser getCurrentUser() {
        WasUser user = (WasUser) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

    /**
     * @param salt     盐值
     * @param password 密码原值
     * @return
     */
    public static String buildPassHash(String salt, String password) {
        String hashAlgorithmName = "SHA-1";//加密方式
        int hashIterations = 2;//加密2次
        Object result = new SimpleHash(hashAlgorithmName, password, salt, hashIterations);
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(buildPassHash("admin", "jiyou820"));
    }
}
