package com.hy.vrfrog.http.responsebean;

/**
 * Created by qwe on 2017/8/19.
 */

public class GiveRewardBean {

    /**
     * code : -1
     * msg : 蛙豆不足
     */

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
