package com.hy.vrfrog.http.responsebean;

/**
 * Created by 姚中平 on 2017/8/22.
 */

public class PayStatus {

    /**
     * code : 0
     * msg : success
     * result : true
     * page : null
     * created : null
     */

    private int code;
    private String msg;
    private boolean result;
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

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
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
