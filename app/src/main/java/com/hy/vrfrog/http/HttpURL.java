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
//    private static String Host = "http://192.168.1.140:8080/hyplatform/api/";
//    private static String Host = "http://118.89.246.194:8080/api/";


//        private static String Root_Host = "http://123.206.67.115:8080";
    private static String Root_Host = "http://118.89.246.194:8080";
    private static String Host = Root_Host + "/api/";

    //        public static String IV_HOST = Root_Host+"/head/";
//    public static String IV_HOST = Root_Host+"/api/";
    public static String IV_HOST = Root_Host + "/img/live/";
    public static String IV_CHARGE_HOST = Root_Host + "/img/chargr/";
    public static String IV_GIFT_HOST = Root_Host + "/img/gift/";
    public static String IV_PERSON_HOST = Root_Host + "/img/person/";
    public static String IV_USER_HOST = Root_Host + "/img/user/";
    public static String IV_HEAD_HOST = Root_Host + "/img/head/";
    public static String IV_APK_HOST = Root_Host + "/img/apk/";
    public static String NOR_IV_HOST = Root_Host + "/head/bf2156e0-ab0a-4199-a0ed-12a4ce4e7c5c.png";
    public static String APK_HOST = Root_Host + "/head/apk/";
    public static String Token = "C47B1071";
    public static String SourceNum = "222";


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
    public static String UpdateHeadPic = Host + "user/update/android/img";

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
    public static String UpdatePersonRoom = Host + "personLvb/getRoomByUid";

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
//    public static String UpdateVersion = Host + "login/search/version";
    public static String UpdateVersion = Host + "free/search/version";

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
     * 首页数据
     */
    public static String Resource = Host + "login/get/resource";
    /**
     * 首页推荐数据 http://192.168.1.141:8080/hyplatform/api/
     */
    public static String VodByCommendTopic = Host + "login/get/vodByCommendTopic";

    /**
     * 首页推荐数据 http://192.168.1.141:8080/hyplatform/api/
     */
    public static String AllLive = Host + "login/get/allLive";

    /**
     * 充值
     */
    public static String Add = Host + "charge/android/add";

    /**
     * 打赏
     */
    public static String Pay = Host + "charge/pay";

    /**
     * 获取充值规则列表
     */
    public static String Get = Host + "charge/get";

    /**
     * 获得用户余额
     */
    public static String Remain = Host + "charge/remain/get";

    /**
     * 获得用户审核状态
     */
    public static String ChannelStatus = Host + "login/user/channelStatus/get";


    /**
     * 获取消费记录
     */
    public static String RechargeRecode = Host + "charge/history/get";

    /**
     * 创建直播间
     */
    public static String createRoom = Host + "personLvb/create";


    /**
     * 编辑直播间
     */
    public static String editRoom = Host + "personLvb/updatePersonRoom";

    /**
     * 编辑直播间
     */
    public static String getGift = Host + "free/gift/get";

    /**
     * 获取直播间收获的蛙豆
     */
    public static String getRoomMoney = Host + "charge/money/get";


    /**
     * 获取直播间是否要收费
     */
    public static String payStatus = Host + "charge/live/pay/status";


}
