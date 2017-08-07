package com.hy.vrfrog.vrplayer.im;

/**
 * Created by 姚中平 on 2017/8/2.
 */

public class TimConfig {
    public static final int Appid = 1400037231;  //用户点击更多或者标题传过来的数据
    public static final String AccountType = "14461"; //用户划过来的数据,详情界面展示推荐页
    public static final String Identifier = "admin";  //测试数据
    public static final String Identifier2 = "lijie";  //测试数据
    public static final String GroupID = "@TGS#aDU53Y3EB";  //测试数据
    public static final String UserSign = "eJxlz8lOwzAUBdB9viLKNghsx24Lu7YMcYbSQsW0sUzsELdkaOKEAOLfKaESlnjbc6*u3qdl27azjm6PeZKUbaGZfq*kY5-ZDnCO-rCqlGBcM68W-1D2laol46mW9YCQEIIAMDNKyEKrVB0SXOSqMLgRWzZs-PbxvuyNkQfNiHoZML54nNPVnN6TLKV5Hzeblk5hhG8uP1zuj5ZuhF873YQNzgjcnFyjN5pNw1Nx3t4FTyjsatE-7wJ3Fkf*dgWCdCZ4sntYLvzWW1-5ZWdMapXLw0MAktEEjyeGdrJuVFkMAbR3iDzwc471ZX0D3ghcuQ__";  //测试数据
    public static final String UserSign2 = "eJxlj01Pg0AURff8CsLamPkEYtJFa5oGhJqRGnRFKLziA0pHOrWtxv*uYhNJvNtzbm7uh2XbtrOKkuu8KHaHzmTmrMGxb2yHOFd-UGsss9xkvC--QThp7CHLNwb6AVIpJSNk7GAJncENXowWa4QR3pdNNmz89sV3mXuM07GC1QDjuboNFu-hi4yWED29HvGQi6QK5*FqPXsOchamfu16j83DtD35vjvF2V3hVkrWelvdp6L1jqmKz4tl0tGWNYWGIA45XysdFKAmk9GkwS1cDhHqSl*I8aU36Pe46waBESop4*QnjvVpfQHv9lyp";

    //直播端右下角listview显示type
    public static final int TEXT_TYPE           = 0;
    public static final int MEMBER_ENTER        = 1;
    public static final int MEMBER_EXIT         = 2;
    public static final int PRAISE              = 3;

    public static final int LOCATION_PERMISSION_REQ_CODE = 1;
    public static final int WRITE_PERMISSION_REQ_CODE    = 2;

    /**
     * IM 互动消息类型
     */
    public static final int IMCMD_PAILN_TEXT    = 1;   // 文本消息
    public static final int IMCMD_ENTER_LIVE    = 2;   // 用户加入直播
    public static final int IMCMD_EXIT_LIVE     = 3;   // 用户退出直播
    public static final int IMCMD_PRAISE        = 4;   // 点赞消息
    public static final int IMCMD_DANMU         = 5;   // 弹幕消息
}
