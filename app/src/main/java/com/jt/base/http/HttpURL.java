package com.jt.base.http;

/**
 * Created by m1762 on 2017/6/12.
 */

public class HttpURL {
    //服务器地址
    private static String Host = "http://192.168.1.99:8080/hyplatform/api/";
    public static String Token = "C47B1071";

    /**
     * 登录
     */
    public static String Login = Host + "login";
    /**
     * 注册
     */
    public static String Register = Host + "login/register";
    /**
     * 验证码
     */
    public static String SendYzm = Host + "login/sendYzm";
    /**
     * 上传头像
     */
    public static String UpdateHeadPic = Host +"user/update/android/img";
    /**
     * 更新密码
     */
    public static String UpdatePassWord = Host +"login/psw/update";

    /**
     * 更新密码的验证码
     */
    public static String RestPassWordYZM = Host +"login/sendYzm/psw";
    /**
     * 更新密码的验证码
     */
    public static String UpdateUserInfo= Host +"user/update";




}
