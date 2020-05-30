package com.hl.shangou.util.password;

/**
 * creator：杜夫人
 * date: 2020/5/28
 */
public class PasswordUtil {
    private final static String SALT = "shangou";

    public static String encodePassword(String password) {
        String salt = "{{" + SALT + "}}";// 盐值准备好
        MD5Code md5Code = new MD5Code();
        String md5ofStr = md5Code.getMD5ofStr(salt + password);
        for (int i = 0; i < 3; i++) {
            md5ofStr = md5Code.getMD5ofStr(md5ofStr);
        }
        return md5ofStr;
    }

//    123456  得到密码      2984C92C08A43223DD724C832107D051
//    public static void main(String[] args) {
//        String s = encodePassword("123456");
//
//        System.err.println(s);
//    }
}
