package com.hy.vrfrog.http.responsebean;

/**
 * Created by 姚中平 on 2017/8/23.
 */

public class CheckNumBean {


    /**
     * code : 0
     * msg : success
     * result : {"id":1,"name":"bg","url":"apk_putBG.jpg","versionNum":"1.0.2.3","uid":null,"size":1,"status":1,"createDate":null,"updateDate":null,"remark":"aefaw哈哈哈哈孙松军咕咕咕咕一怒你头疼吞吞吐吐拖拖拖拖","username":null}
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
         * id : 1
         * name : bg
         * url : apk_putBG.jpg
         * versionNum : 1.0.2.3
         * uid : null
         * size : 1
         * status : 1
         * createDate : null
         * updateDate : null
         * remark : aefaw哈哈哈哈孙松军咕咕咕咕一怒你头疼吞吞吐吐拖拖拖拖
         * username : null
         */

        private int id;
        private String name;
        private String url;
        private String versionNum;
        private Object uid;
        private int size;
        private int status;
        private Object createDate;
        private Object updateDate;
        private String remark;
        private Object username;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVersionNum() {
            return versionNum;
        }

        public void setVersionNum(String versionNum) {
            this.versionNum = versionNum;
        }

        public Object getUid() {
            return uid;
        }

        public void setUid(Object uid) {
            this.uid = uid;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public Object getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(Object updateDate) {
            this.updateDate = updateDate;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Object getUsername() {
            return username;
        }

        public void setUsername(Object username) {
            this.username = username;
        }
    }
}
