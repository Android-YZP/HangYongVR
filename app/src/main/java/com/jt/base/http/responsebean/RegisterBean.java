package com.jt.base.http.responsebean;

/**
 * Created by m1762 on 2017/6/14.
 */

public class RegisterBean {

    /**
     * code : 0
     * msg : success
     * result : 注册成功!
     * page : null
     */

    private int code;
    private String msg;
    private String result;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }
}
