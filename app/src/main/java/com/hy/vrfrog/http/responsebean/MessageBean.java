package com.hy.vrfrog.http.responsebean;

/**
 * Created by 姚中平 on 2017/8/18.
 */

public class MessageBean {

    /**
     * giftCount :
     * userAction : 1
     * nickName : 18913826225
     * userId : FCF21687B8233FF26003FC2AB9EE6717
     * headPic : http://123.206.67.115:8080 /head/38093494-ee2a-4f69-b01f-25141b09ce93.png
     * msg : 对方尴尬局面！对方尴尬的事情
     */

    private String giftCount;
    private int userAction;
    private String nickName;
    private String userId;
    private String headPic;
    private String msg;

    public String getGiftCount() {
        return giftCount;
    }

    public void setGiftCount(String giftCount) {
        this.giftCount = giftCount;
    }

    public int getUserAction() {
        return userAction;
    }

    public void setUserAction(int userAction) {
        this.userAction = userAction;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
