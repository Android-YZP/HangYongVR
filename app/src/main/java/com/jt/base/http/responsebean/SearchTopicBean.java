package com.jt.base.http.responsebean;

import java.util.List;

/**
 * Created by Smith on 2017/7/18.
 */

public class SearchTopicBean {


    /**
     * code : 0
     * msg : success
     * result : [{"id":62,"name":"娱乐直播1","type":2,"parentId":null,"status":1,"sort":1,"created":"2017-07-17 14:18:16","img":"9004f666-372c-4459-875c-912ba88f7e28.png","comment":"邓紫棋直播","parentName":null}]
     * page : {"total":1,"totalPage":1,"page":1,"count":5,"startDate":null,"endDate":null,"keywords":"直播","order":null,"asc":null,"partition":null,"status":null,"getAllRecord":false,"countAllRecord":true,"channelName":null,"vodStatus":null,"typeId":null,"topicId":null,"isall":null,"sourceNum":222,"topicImg":null,"intrduce":null,"startIndex":0}
     */

    private int code;
    private String msg;
    private PageBean page;
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

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class PageBean {
        /**
         * total : 1
         * totalPage : 1
         * page : 1
         * count : 5
         * startDate : null
         * endDate : null
         * keywords : 直播
         * order : null
         * asc : null
         * partition : null
         * status : null
         * getAllRecord : false
         * countAllRecord : true
         * channelName : null
         * vodStatus : null
         * typeId : null
         * topicId : null
         * isall : null
         * sourceNum : 222
         * topicImg : null
         * intrduce : null
         * startIndex : 0
         */

        private int total;
        private int totalPage;
        private int page;
        private int count;
        private Object startDate;
        private Object endDate;
        private String keywords;
        private Object order;
        private Object asc;
        private Object partition;
        private Object status;
        private boolean getAllRecord;
        private boolean countAllRecord;
        private Object channelName;
        private Object vodStatus;
        private Object typeId;
        private Object topicId;
        private Object isall;
        private int sourceNum;
        private Object topicImg;
        private Object intrduce;
        private int startIndex;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Object getStartDate() {
            return startDate;
        }

        public void setStartDate(Object startDate) {
            this.startDate = startDate;
        }

        public Object getEndDate() {
            return endDate;
        }

        public void setEndDate(Object endDate) {
            this.endDate = endDate;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public Object getOrder() {
            return order;
        }

        public void setOrder(Object order) {
            this.order = order;
        }

        public Object getAsc() {
            return asc;
        }

        public void setAsc(Object asc) {
            this.asc = asc;
        }

        public Object getPartition() {
            return partition;
        }

        public void setPartition(Object partition) {
            this.partition = partition;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public boolean isGetAllRecord() {
            return getAllRecord;
        }

        public void setGetAllRecord(boolean getAllRecord) {
            this.getAllRecord = getAllRecord;
        }

        public boolean isCountAllRecord() {
            return countAllRecord;
        }

        public void setCountAllRecord(boolean countAllRecord) {
            this.countAllRecord = countAllRecord;
        }

        public Object getChannelName() {
            return channelName;
        }

        public void setChannelName(Object channelName) {
            this.channelName = channelName;
        }

        public Object getVodStatus() {
            return vodStatus;
        }

        public void setVodStatus(Object vodStatus) {
            this.vodStatus = vodStatus;
        }

        public Object getTypeId() {
            return typeId;
        }

        public void setTypeId(Object typeId) {
            this.typeId = typeId;
        }

        public Object getTopicId() {
            return topicId;
        }

        public void setTopicId(Object topicId) {
            this.topicId = topicId;
        }

        public Object getIsall() {
            return isall;
        }

        public void setIsall(Object isall) {
            this.isall = isall;
        }

        public int getSourceNum() {
            return sourceNum;
        }

        public void setSourceNum(int sourceNum) {
            this.sourceNum = sourceNum;
        }

        public Object getTopicImg() {
            return topicImg;
        }

        public void setTopicImg(Object topicImg) {
            this.topicImg = topicImg;
        }

        public Object getIntrduce() {
            return intrduce;
        }

        public void setIntrduce(Object intrduce) {
            this.intrduce = intrduce;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(int startIndex) {
            this.startIndex = startIndex;
        }
    }

    public static class ResultBean {
        /**
         * id : 62
         * name : 娱乐直播1
         * type : 2
         * parentId : null
         * status : 1
         * sort : 1
         * created : 2017-07-17 14:18:16
         * img : 9004f666-372c-4459-875c-912ba88f7e28.png
         * comment : 邓紫棋直播
         * parentName : null
         */

        private int id;
        private String name;
        private int type;
        private Object parentId;
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
