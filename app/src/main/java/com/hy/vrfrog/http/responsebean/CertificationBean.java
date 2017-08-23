package com.hy.vrfrog.http.responsebean;

/**
 * Created by qwe on 2017/8/21.
 */

public class CertificationBean {

    /**
     * code : 110
     * msg : 你已拥有直播间,可以正常直播不允许再次创建
     * result : null
     * page : null
     * created : null
     */

    private int code;
    private String msg;
    private Object result;
    private Object page;
    private Object created;

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

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }

    public Object getCreated() {
        return created;
    }

    public void setCreated(Object created) {
        this.created = created;
    }
}
