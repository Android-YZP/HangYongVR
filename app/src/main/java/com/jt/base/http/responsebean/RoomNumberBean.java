package com.jt.base.http.responsebean;

/**
 * Created by Smith on 2017/6/29.
 */

public class RoomNumberBean {


    /**
     * code : 0
     * msg : success
     * result : 7
     * page : null
     */

    private int code;
    private String msg;
    private int result;
    private Object page;

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

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }
}
