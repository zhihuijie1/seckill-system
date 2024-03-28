package com.chengcheng.seckill.utils;


import org.apache.commons.codec.digest.DigestUtils;

public class MD5 {
    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    public static final String salt = "1a2b3c4d";

    //inputPassToFromPass
    public static String inputPassToFromPass(String inputPass) {
        //从盐里面挑几个数字掺进去
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        System.out.println("str   " + str);
        System.out.println("ceshi: " + md5("123"));
        return md5(str);
    }

    //formPassToDBPass
    public static String formPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        //String str = salt.charAt(2) + salt.charAt(4) + salt.charAt(6) + formPass + salt.charAt(8);
        return md5(str);
    }

    //inputPassToDBPass
    public static String inputPassToDBPass(String inputPass, String salt) {
        String formPass = inputPassToFromPass(inputPass);
        String dbPass = formPassToDBPass(formPass, salt);
        return dbPass;
    }

    public static void main(String[] args) {
        String formPass = inputPassToFromPass("123456");
        System.out.println("第一次加密"+"   "+formPass);
        System.out.println("第2次加密"+"   "+formPassToDBPass(formPass, "1a2b3c4d"));
        System.out.println("一次进行2次" + "  " + inputPassToDBPass("123456", "1a2b3c4d"));
    }
}
