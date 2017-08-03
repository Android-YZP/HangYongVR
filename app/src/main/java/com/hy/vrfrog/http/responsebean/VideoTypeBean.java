package com.hy.vrfrog.http.responsebean;

import java.util.List;

/**
 * Created by Smith on 2017/7/3.
 */

public class VideoTypeBean {

    /**
     * code : 0
     * msg : success
     * result : [{"id":11,"name":"3214","type":1,"parentId":null,"status":1,"sort":5,"created":"2017-06-27 22:52:05","img":null,"comment":null,"parentName":null},{"id":14,"name":"巅峰非得浮动幅度","type":1,"parentId":null,"status":1,"sort":7,"created":"2017-06-27 22:37:43","img":null,"comment":null,"parentName":null},{"id":33,"name":"地煞符新的","type":1,"parentId":null,"status":1,"sort":12,"created":"2017-06-27 22:37:45","img":null,"comment":null,"parentName":null},{"id":51,"name":"435435","type":1,"parentId":null,"status":1,"sort":13,"created":"2017-06-27 22:48:33","img":null,"comment":null,"parentName":null},{"id":1,"name":"游戏","type":1,"parentId":null,"status":1,"sort":14,"created":"2017-06-27 22:37:47","img":null,"comment":null,"parentName":null},{"id":52,"name":"阿萨德vvv","type":1,"parentId":null,"status":1,"sort":15,"created":"2017-06-28 19:06:15","img":null,"comment":null,"parentName":null},{"id":67,"name":"踩踩踩踩踩","type":1,"parentId":null,"status":1,"sort":16,"created":"2017-06-28 19:06:02","img":null,"comment":null,"parentName":null}]
     * page : null
     */

    private int code;
    private String msg;
    private Object page;
    private List<ResultBean> result;

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

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 11
         * name : 3214
         * type : 1
         * parentId : null
         * status : 1
         * sort : 5
         * created : 2017-06-27 22:52:05
         * img : null
         * comment : null
         * parentName : null
         */

        private int id;
        private String name;
        private int type;
        private Object parentId;
        private int status;
        private int sort;
        private String created;
        private Object img;
        private Object comment;
        private Object parentName;

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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getParentId() {
            return parentId;
        }

        public void setParentId(Object parentId) {
            this.parentId = parentId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public Object getImg() {
            return img;
        }

        public void setImg(Object img) {
            this.img = img;
        }

        public Object getComment() {
            return comment;
        }

        public void setComment(Object comment) {
            this.comment = comment;
        }

        public Object getParentName() {
            return parentName;
        }

        public void setParentName(Object parentName) {
            this.parentName = parentName;
        }
    }
}
