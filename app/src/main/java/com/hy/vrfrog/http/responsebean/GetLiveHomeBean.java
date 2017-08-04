package com.hy.vrfrog.http.responsebean;

/**
 * Created by qwe on 2017/8/3.
 */

public class GetLiveHomeBean {
    private int code;
    private String msg;
    private String title;
    private String number;
    private String state;
    private String name;
    private String playState;
    private String headImg;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayState() {
        return playState;
    }

    public void setPlayState(String playState) {
        this.playState = playState;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
