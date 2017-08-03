package com.hy.vrfrog.http.responsebean;

/**
 * Created by Smith on 2017/7/15.
 */

public class TopicImgBean {


    /**
     * code : 0
     * msg : success
     * result : {"id":62,"name":"娱乐直播","type":2,"parentId":46,"status":1,"sort":1,"created":"2017-07-13 10:39:26","img":"e8b5ba07-8f63-47b5-9183-7bd1b6587c55.jpg","comment":"邓紫棋直播","parentName":null}
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
         * id : 62
         * name : 娱乐直播
         * type : 2
         * parentId : 46
         * status : 1
         * sort : 1
         * created : 2017-07-13 10:39:26
         * img : e8b5ba07-8f63-47b5-9183-7bd1b6587c55.jpg
         * comment : 邓紫棋直播
         * parentName : null
         */

        private int id;
        private String name;
        private int type;
        private int parentId;
        private int status;
        private int sort;
        private String created;
        private String img;
        private String comment;
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

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
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
