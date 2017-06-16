package com.jt.base.http.responsebean;

/**
 * Created by m1762 on 2017/6/16.
 */

public class ForgetYzmBean {

    /**
     * code : 110
     * msg : 用户不存在
     * result : null
     * page : null
     */

    private int code;
    private String msg;
    private Object result;
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
}
