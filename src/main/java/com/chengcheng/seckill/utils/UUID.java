package com.chengcheng.seckill.utils;

public class UUID {
    public static String uuid() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }
}
