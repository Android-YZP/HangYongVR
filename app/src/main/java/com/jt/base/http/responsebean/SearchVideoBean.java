package com.jt.base.http.responsebean;

import java.util.List;

/**
 * Created by Smith on 2017/7/18.
 */

public class SearchVideoBean {

    /**
     * code : 0
     * msg : success
     * result : [{"id":295,"uid":1,"type":1,"channelId":"10263_148deed7bbb34fe5a5be8c894ab779d3","channelName":"测试视频","isall":0,"status":0,"price":0,"img":"9e7b2e26-54ca-461b-a57a-97ac958464e7.jpg","createDate":null,"alipay":"","place":0,"outputSourceType":3,"upstreamAddress":"rtmp://10263.livepush.myqcloud.com/live/10263_148deed7bbb34fe5a5be8c894ab779d3?bizid=10263&txSecret=32c62dad47d92039bcee95a6bb993ea7&txTime=596E307F","rtmpDownstreamAddress":"rtmp://10263.liveplay.myqcloud.com/live/10263_148deed7bbb34fe5a5be8c894ab779d3","flvDownstreamAddress":"http://10263.liveplay.myqcloud.com/live/10263_148deed7bbb34fe5a5be8c894ab779d3.flv","hlsDownstreamAddress":"http://10263.liveplay.myqcloud.com/10263_148deed7bbb34fe5a5be8c894ab779d3.m3u8","playerPassword":null,"watermarkId":null,"outputrateOne":null,"outputrateTow":null,"outputrateThree":null,"head":"0798de34-d890-44d3-936a-64628d8233d4.jpg","typeId":46,"topicId":62,"img1":"84b09f10-d7bf-45fa-8944-10e89af3c22f.jpg","time":null,"format":null,"size":null,"rate":null,"vodTaskId":null,"top":null,"isVip":null,"publish":"2017-07-17 10:02:18","vodStatus":null,"vodInfos":null,"username":null,"typeName":null,"topicName":null,"reason":null,"introduce":null,"num":null}]
     * page : {"total":1,"totalPage":1,"page":1,"count":5,"startDate":null,"endDate":null,"keywords":"测试","order":null,"asc":null,"partition":null,"status":null,"getAllRecord":false,"countAllRecord":true,"channelName":null,"vodStatus":null,"typeId":null,"topicId":null,"isall":null,"sourceNum":222,"topicImg":null,"intrduce":null,"startIndex":0}
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
         * keywords : 测试
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
         * id : 295
         * uid : 1
         * type : 1
         * channelId : 10263_148deed7bbb34fe5a5be8c894ab779d3
         * channelName : 测试视频
         * isall : 0
         * status : 0
         * price : 0
         * img : 9e7b2e26-54ca-461b-a57a-97ac958464e7.jpg
         * createDate : null
         * alipay :
         * place : 0
         * outputSourceType : 3
         * upstreamAddress : rtmp://10263.livepush.myqcloud.com/live/10263_148deed7bbb34fe5a5be8c894ab779d3?bizid=10263&txSecret=32c62dad47d92039bcee95a6bb993ea7&txTime=596E307F
         * rtmpDownstreamAddress : rtmp://10263.liveplay.myqcloud.com/live/10263_148deed7bbb34fe5a5be8c894ab779d3
         * flvDownstreamAddress : http://10263.liveplay.myqcloud.com/live/10263_148deed7bbb34fe5a5be8c894ab779d3.flv
         * hlsDownstreamAddress : http://10263.liveplay.myqcloud.com/10263_148deed7bbb34fe5a5be8c894ab779d3.m3u8
         * playerPassword : null
         * watermarkId : null
         * outputrateOne : null
         * outputrateTow : null
         * outputrateThree : null
         * head : 0798de34-d890-44d3-936a-64628d8233d4.jpg
         * typeId : 46
         * topicId : 62
         * img1 : 84b09f10-d7bf-45fa-8944-10e89af3c22f.jpg
         * time : null
         * format : null
         * size : null
         * rate : null
         * vodTaskId : null
         * top : null
         * isVip : null
         * publish : 2017-07-17 10:02:18
         * vodStatus : null
         * vodInfos : null
         * username : null
         * typeName : null
         * topicName : null
         * reason : null
         * introduce : null
         * num : null
         */

        private int id;
        private int uid;
        private int type;
        private String channelId;
        private String channelName;
        private int isall;
        private int status;
        private int price;
        private String img;
        private Object createDate;
        private String alipay;
        private int place;
        private int outputSourceType;
        private String upstreamAddress;
        private String rtmpDownstreamAddress;
        private String flvDownstreamAddress;
        private String hlsDownstreamAddress;
        private Object playerPassword;
        private Object watermarkId;
        private Object outputrateOne;
        private Object outputrateTow;
        private Object outputrateThree;
        private String head;
        private int typeId;
        private int topicId;
        private String img1;
        private Object time;
        private Object format;
        private Object size;
        private Object rate;
        private Object vodTaskId;
        private Object top;
        private Object isVip;
        private String publish;
        private Object vodStatus;
        private Object vodInfos;
        private Object username;
        private Object typeName;
        private Object topicName;
        private Object reason;
        private Object introduce;
        private Object num;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
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

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public String getAlipay() {
            return alipay;
        }

        public void setAlipay(String alipay) {
            this.alipay = alipay;
        }

        public int getPlace() {
            return place;
        }

        public void setPlace(int place) {
            this.place = place;
        }

        public int getOutputSourceType() {
            return outputSourceType;
        }

        public void setOutputSourceType(int outputSourceType) {
            this.outputSourceType = outputSourceType;
        }

        public String getUpstreamAddress() {
            return upstreamAddress;
        }

        public void setUpstreamAddress(String upstreamAddress) {
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

        public String getHlsDownstreamAddress() {
            return hlsDownstreamAddress;
        }

        public void setHlsDownstreamAddress(String hlsDownstreamAddress) {
            this.hlsDownstreamAddress = hlsDownstreamAddress;
        }

        public Object getPlayerPassword() {
            return playerPassword;
        }

        public void setPlayerPassword(Object playerPassword) {
            this.playerPassword = playerPassword;
        }

        public Object getWatermarkId() {
            return watermarkId;
        }

        public void setWatermarkId(Object watermarkId) {
            this.watermarkId = watermarkId;
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

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public int getTopicId() {
            return topicId;
        }

        public void setTopicId(int topicId) {
            this.topicId = topicId;
        }

        public String getImg1() {
            return img1;
        }

        public void setImg1(String img1) {
            this.img1 = img1;
        }

        public Object getTime() {
            return time;
        }

        public void setTime(Object time) {
            this.time = time;
        }

        public Object getFormat() {
            return format;
        }

        public void setFormat(Object format) {
            this.format = format;
        }

        public Object getSize() {
            return size;
        }

        public void setSize(Object size) {
            this.size = size;
        }

        public Object getRate() {
            return rate;
        }

        public void setRate(Object rate) {
            this.rate = rate;
        }

        public Object getVodTaskId() {
            return vodTaskId;
        }

        public void setVodTaskId(Object vodTaskId) {
            this.vodTaskId = vodTaskId;
        }

        public Object getTop() {
            return top;
        }

        public void setTop(Object top) {
            this.top = top;
        }

        public Object getIsVip() {
            return isVip;
        }

        public void setIsVip(Object isVip) {
            this.isVip = isVip;
        }

        public String getPublish() {
            return publish;
        }

        public void setPublish(String publish) {
            this.publish = publish;
        }

        public Object getVodStatus() {
            return vodStatus;
        }

        public void setVodStatus(Object vodStatus) {
            this.vodStatus = vodStatus;
        }

        public Object getVodInfos() {
            return vodInfos;
        }

        public void setVodInfos(Object vodInfos) {
            this.vodInfos = vodInfos;
        }

        public Object getUsername() {
            return username;
        }

        public void setUsername(Object username) {
            this.username = username;
        }

        public Object getTypeName() {
            return typeName;
        }

        public void setTypeName(Object typeName) {
            this.typeName = typeName;
        }

        public Object getTopicName() {
            return topicName;
        }

        public void setTopicName(Object topicName) {
            this.topicName = topicName;
        }

        public Object getReason() {
            return reason;
        }

        public void setReason(Object reason) {
            this.reason = reason;
        }

        public Object getIntroduce() {
            return introduce;
        }

        public void setIntroduce(Object introduce) {
            this.introduce = introduce;
        }

        public Object getNum() {
            return num;
        }

        public void setNum(Object num) {
            this.num = num;
        }
    }
}
