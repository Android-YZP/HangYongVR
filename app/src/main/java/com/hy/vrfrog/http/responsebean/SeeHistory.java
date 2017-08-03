package com.hy.vrfrog.http.responsebean;

/**
 * Created by Smith on 2017/7/18.
 */

public class SeeHistory {
    private String persent;
    private String img;
    private String name;
    private int playType;
    private int playerMode;
    private String url;
    private String desc;
    private int positon;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPositon() {
        return positon;
    }

    public void setPositon(int positon) {
        this.positon = positon;
    }

    public String getPersent() {
        return persent;
    }

    public void setPersent(String persent) {
        this.persent = persent;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayType() {
        return playType;
    }

    public void setPlayType(int playType) {
        this.playType = playType;
    }

    public int getPlayerMode() {
        return playerMode;
    }

    public void setPlayerMode(int playerMode) {
        this.playerMode = playerMode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
