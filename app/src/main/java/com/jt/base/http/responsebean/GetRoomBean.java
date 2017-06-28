package com.jt.base.http.responsebean;

import java.util.List;

/**
 * Created by Smith on 2017/6/19.
 */

public class GetRoomBean {


    /**
     * code : 0
     * msg : success
     * result : [{"id":null,"uid":null,"username":null,"channelName":"测试2D","isall":0,"status":null,"price":0,"img":"43631035-02cd-428d-8861-70688142d866.jpg","head":"0f4d349d-f0ed-4a43-910e-73f0d627dc6f.jpg","createDate":null,"alipay":null,"place":null,"outputsourcetype":null,"upstreamAddress":null,"rtmpDownstreamAddress":"rtmp://9250.liveplay.myqcloud.com/live/9250_34cfb6aeee6d4ee3b52ab77a0ac255a8","flvDownstreamAddress":"http://9250.liveplay.myqcloud.com/live/9250_34cfb6aeee6d4ee3b52ab77a0ac255a8.flv","hlsDownstreamAddress":null,"channelId":"9250_34cfb6aeee6d4ee3b52ab77a0ac255a8","playerpassword":null,"watermarkid":null,"outputrateOne":null,"outputrateTow":null,"outputrateThree":null,"maxnum":null,"clicknum":null,"typeId":1,"typeName":null,"topicId":7,"topicName":null,"img1":"754c8d2e-bb72-4d14-b686-08ee73215b91.jpg","size":null,"type":null,"sort":null},{"id":null,"uid":null,"username":null,"channelName":"测试全景","isall":2,"status":null,"price":10,"img":"70fcb353-91d1-47a0-a2b9-97634f30b4f0.jpg","head":"e224c53c-b2bc-49c5-a68b-66901ff5653a.jpg","createDate":null,"alipay":null,"place":null,"outputsourcetype":null,"upstreamAddress":null,"rtmpDownstreamAddress":"rtmp://9250.liveplay.myqcloud.com/live/9250_19e3ff51157143fd9f559b8980ace1c9","flvDownstreamAddress":"http://9250.liveplay.myqcloud.com/live/9250_19e3ff51157143fd9f559b8980ace1c9.flv","hlsDownstreamAddress":"http://9250.liveplay.myqcloud.com/9250_19e3ff51157143fd9f559b8980ace1c9.m3u8","channelId":"9250_19e3ff51157143fd9f559b8980ace1c9","playerpassword":null,"watermarkid":null,"outputrateOne":null,"outputrateTow":null,"outputrateThree":null,"maxnum":null,"clicknum":null,"typeId":1,"typeName":null,"topicId":9,"topicName":null,"img1":"c16366c6-d0dd-452e-b5a1-f5203e1409c7.jpg","size":null,"type":null,"sort":null}]
     * page : {"total":2,"totalPage":1,"page":1,"count":3,"startDate":null,"endDate":null,"keywords":null,"order":null,"asc":null,"partition":null,"status":null,"getAllRecord":false,"countAllRecord":true,"channelName":null,"startIndex":0}
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
         * total : 2
         * totalPage : 1
         * page : 1
         * count : 3
         * startDate : null
         * endDate : null
         * keywords : null
         * order : null
         * asc : null
         * partition : null
         * status : null
         * getAllRecord : false
         * countAllRecord : true
         * channelName : null
         * startIndex : 0
         */

        private int total;
        private int totalPage;
        private int page;
        private int count;
        private Object startDate;
        private Object endDate;
        private Object keywords;
        private Object order;
        private Object asc;
        private Object partition;
        private Object status;
        private boolean getAllRecord;
        private boolean countAllRecord;
        private Object channelName;
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

        public Object getKeywords() {
            return keywords;
        }

        public void setKeywords(Object keywords) {
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

        public int getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(int startIndex) {
            this.startIndex = startIndex;
        }
    }

    public static class ResultBean {
        /**
         * id : null
         * uid : null
         * username : null
         * channelName : 测试2D
         * isall : 0
         * status : null
         * price : 0
         * img : 43631035-02cd-428d-8861-70688142d866.jpg
         * head : 0f4d349d-f0ed-4a43-910e-73f0d627dc6f.jpg
         * createDate : null
         * alipay : null
         * place : null
         * outputsourcetype : null
         * upstreamAddress : null
         * rtmpDownstreamAddress : rtmp://9250.liveplay.myqcloud.com/live/9250_34cfb6aeee6d4ee3b52ab77a0ac255a8
         * flvDownstreamAddress : http://9250.liveplay.myqcloud.com/live/9250_34cfb6aeee6d4ee3b52ab77a0ac255a8.flv
         * hlsDownstreamAddress : null
         * channelId : 9250_34cfb6aeee6d4ee3b52ab77a0ac255a8
         * playerpassword : null
         * watermarkid : null
         * outputrateOne : null
         * outputrateTow : null
         * outputrateThree : null
         * maxnum : null
         * clicknum : null
         * typeId : 1
         * typeName : null
         * topicId : 7
         * topicName : null
         * img1 : 754c8d2e-bb72-4d14-b686-08ee73215b91.jpg
         * size : null
         * type : null
         * sort : null
         */

        private Object id;
        private Object uid;
        private Object username;
        private String channelName;
        private int isall;
        private Object status;
        private int price;
        private String img;
        private String head;
        private Object createDate;
        private Object alipay;
        private Object place;
        private Object outputsourcetype;
        private Object upstreamAddress;
        private String rtmpDownstreamAddress;
        private String flvDownstreamAddress;
        private Object hlsDownstreamAddress;
        private String channelId;
        private Object playerpassword;
        private Object watermarkid;
        private Object outputrateOne;
        private Object outputrateTow;
        private Object outputrateThree;
        private Object maxnum;
        private Object clicknum;
        private int typeId;
        private Object typeName;
        private int topicId;
        private Object topicName;
        private String img1;
        private Object size;
        private Object type;
        private Object sort;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getUid() {
            return uid;
        }

        public void setUid(Object uid) {
            this.uid = uid;
        }

        public Object getUsername() {
            return username;
        }

        public void setUsername(Object username) {
            this.username = username;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public int getIsall() {
            return isall;
        }

        public void setIsall(int isall) {
            this.isall = isall;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public Object getAlipay() {
            return alipay;
        }

        public void setAlipay(Object alipay) {
            this.alipay = alipay;
        }

        public Object getPlace() {
            return place;
        }

        public void setPlace(Object place) {
            this.place = place;
        }

        public Object getOutputsourcetype() {
            return outputsourcetype;
        }

        public void setOutputsourcetype(Object outputsourcetype) {
            this.outputsourcetype = outputsourcetype;
        }

        public Object getUpstreamAddress() {
            return upstreamAddress;
        }

        public void setUpstreamAddress(Object upstreamAddress) {
            this.upstreamAddress = upstreamAddress;
        }

        public String getRtmpDownstreamAddress() {
            return rtmpDownstreamAddress;
        }

        public void setRtmpDownstreamAddress(String rtmpDownstreamAddress) {
            this.rtmpDownstreamAddress = rtmpDownstreamAddress;
        }

        public String getFlvDownstreamAddress() {
            return flvDownstreamAddress;
        }

        public void setFlvDownstreamAddress(String flvDownstreamAddress) {
            this.flvDownstreamAddress = flvDownstreamAddress;
        }

        public Object getHlsDownstreamAddress() {
            return hlsDownstreamAddress;
        }

        public void setHlsDownstreamAddress(Object hlsDownstreamAddress) {
            this.hlsDownstreamAddress = hlsDownstreamAddress;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public Object getPlayerpassword() {
            return playerpassword;
        }

        public void setPlayerpassword(Object playerpassword) {
            this.playerpassword = playerpassword;
        }

        public Object getWatermarkid() {
            return watermarkid;
        }

        public void setWatermarkid(Object watermarkid) {
            this.watermarkid = watermarkid;
        }

        public Object getOutputrateOne() {
            return outputrateOne;
        }

        public void setOutputrateOne(Object outputrateOne) {
            this.outputrateOne = outputrateOne;
        }

        public Object getOutputrateTow() {
            return outputrateTow;
        }

        public void setOutputrateTow(Object outputrateTow) {
            this.outputrateTow = outputrateTow;
        }

        public Object getOutputrateThree() {
            return outputrateThree;
        }

        public void setOutputrateThree(Object outputrateThree) {
            this.outputrateThree = outputrateThree;
        }

        public Object getMaxnum() {
            return maxnum;
        }

        public void setMaxnum(Object maxnum) {
            this.maxnum = maxnum;
        }

        public Object getClicknum() {
            return clicknum;
        }

        public void setClicknum(Object clicknum) {
            this.clicknum = clicknum;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public Object getTypeName() {
            return typeName;
        }

        public void setTypeName(Object typeName) {
            this.typeName = typeName;
        }

        public int getTopicId() {
            return topicId;
        }

        public void setTopicId(int topicId) {
            this.topicId = topicId;
        }

        public Object getTopicName() {
            return topicName;
        }

        public void setTopicName(Object topicName) {
            this.topicName = topicName;
        }

        public String getImg1() {
            return img1;
        }

        public void setImg1(String img1) {
            this.img1 = img1;
        }

        public Object getSize() {
            return size;
        }

        public void setSize(Object size) {
            this.size = size;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }
    }
}
