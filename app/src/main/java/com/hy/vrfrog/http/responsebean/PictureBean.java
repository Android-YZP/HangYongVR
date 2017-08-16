package com.hy.vrfrog.http.responsebean;

/**
 * Created by qwe on 2017/8/16.
 */

public class PictureBean {

    /**
     * code : 0
     * msg : success
     * result : {"path":"/root/img/head/1502869178769certificate.jpg"}
     * page : null
     * created : null
     */

    private int code;
    private String msg;
    private ResultBean result;
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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
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

    public static class ResultBean {
        /**
         * path : /root/img/head/1502869178769certificate.jpg
         */

        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
