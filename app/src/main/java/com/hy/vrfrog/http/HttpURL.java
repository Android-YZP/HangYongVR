package com.hy.vrfrog.http;

/**
 * Created by m1762 on 2017/6/12.
 */

public class HttpURL {
    //服务器地址
//    private static String Host = "http://192.168.1.100:8080/hyplatform/api/";
    //    private static String Host = "http://192.168.1.133:8080/hyplatform/api/";
//    private static String Host = "http://192.168.1.135:8080/hyplatform/api/";
//    private static String Host = "http://192.168.1.99:8080/hyplatform/api/";
    private static String Host = "http://192.168.1.132:8080/hyplatform/api/";
//    private static String Host = "http://118.89.246.194:808
// 0/api/";
//    private static String Host = "http://123.206.67.115:8080/api/";

    public static String Token = "C47B1071";
    public static String SourceNum = "222";
//        public static String IV_HOST = "http://118.89.246.194:8080/head/";
    public static String IV_HOST = "http://123.206.67.115:8080/head/";
    public static String NOR_IV_HOST = "http://118.89.246.194:8080/head/bf2156e0-ab0a-4199-a0ed-12a4ce4e7c5c.png";
//    public static String IV_HOST = "http://192.168.1.132:8080/head/";
    //    public static String APK_HOST = "http://118.89.246.194:8080/head/apk/";
    public static String APK_HOST = "";

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
    public static String UpdateHeadPic = "http://192.168.1.100:8080/api/" + "user/update/android/img";

    /**
     * 更新密码
     */
    public static String UpdatePassWord = Host + "login/psw/update";

    /**
     * 更新密码的验证码
     */
    public static String RestPassWordYZM = Host + "login/sendYzm/psw";
    /**
     * 更新用户信息
     */
    public static String UpdateUserInfo = Host + "user/update";

    /**
     * 更新个人直播封面
     */
    public static String UpdatePersonLvbImg = Host + "personLvb/update/img";

    /**
     * 房间列表
     */
    public static String RoomList = Host + "login/getApiRooms";
    /**
     * 房间在线人数
     */
    public static String RoomOnLineNumber = Host + "login/edit/online";

    /**
     * 获取话题类型
     */
//    public static String VideoType = Host + "login/get/types";
    public static String VideoType = Host + "free/videoType/get";
    /**
     * 获取话题
     */
    public static String GetVideoTopic = Host + "login/get/topics";

    /**
     * 获取话题
     */
    public static String VidByType = Host + "login/get/vodByType";

    public static String vodByCommendTopic = Host + "login/get/vodByCommendTopic";
    /**
     * 获取视频
     */
    public static String vodByTopic = Host + "login/get/vodByTopic";
    /**
     * 获取话题图片
     */
    public static String TopicImg = Host + "login/get/topic";


    /**
     * 获取话题的視頻
     */
    public static String UpdateVersion = Host + "login/search/version";

    /**
     * 搜索话题的視頻和类型
     */
    public static String vod = Host + "login/search/vod";
    public static String Topic = Host + "login/search/topic";
    /**
     * 添加用户的观看历史
     */
    public static String History = Host + "history/put";

    /**
     * 用户的获取观看历史
     */
    public static String GetHistory = Host + "history/gets";

    /**
     * 用户的删除观看历史
     */
    public static String DeleteHistory = Host + "history/delete";


    /**
     *首页数据
     */
    public static String Resource = Host + "login/get/resource";
    /**
     *首页推荐数据 http://192.168.1.141:8080/hyplatform/api/
     */
    public static String VodByCommendTopic = Host+"login/get/vodByCommendTopic";

    /**
     *首页推荐数据 http://192.168.1.141:8080/hyplatform/api/
     */
    public static String AllLive = Host+"login/get/allLive";

   /**
     * 创建直播间
     */
    public static String createRoom = Host+"personLvb/create";


    /**
     * 编辑直播间
     */
    public static String editRoom = Host+"personLvb/updatePersonRoom";



}
