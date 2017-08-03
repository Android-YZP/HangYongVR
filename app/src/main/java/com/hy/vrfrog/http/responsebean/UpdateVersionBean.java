package com.hy.vrfrog.http.responsebean;

/**
 * Created by Smith on 2017/7/15.
 */

public class UpdateVersionBean {

    /**
     * code : 0
     * msg : success
     * result : {"id":11,"sourceNum":222,"versionNum":9876,"url":"app-release.apk","introduce":"343","createDate":"2017-07-15 12:47:13"}
     * page : null
     */

    private int code;
    private String msg;
    private ResultBean result;
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

    public static class ResultBean {
        /**
         * id : 11
         * sourceNum : 222
         * versionNum : 9876
         * url : app-release.apk
         * introduce : 343
         * createDate : 2017-07-15 12:47:13
         */

        private int id;
        private int sourceNum;
        private int versionNum;
        private String url;
        private String introduce;
        private String createDate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSourceNum() {
            return sourceNum;
        }

        public void setSourceNum(int sourceNum) {
            this.sourceNum = sourceNum;
        }

        public int getVersionNum() {
            return versionNum;
        }

        public void setVersionNum(int versionNum) {
            this.versionNum = versionNum;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
