package com.jt.base.http.responsebean;

import java.util.List;

/**
 * Created by Smith on 2017/6/19.
 */

public class GetRoomBean {

    /**
     * code : 0
     * msg : success
     * result : [{"id":null,"uid":null,"channelName":null,"isall":0,"status":null,"price":0,"img":"2014.gif","head":"2014.gif","createDate":null,"alipay":null,"place":null,"outputsourcetype":null,"upstreamAddress":null,"rtmpDownstreamAddress":null,"flvDownstreamAddress":null,"hlsDownstreamAddress":null,"channelId":null,"playerpassword":null,"watermarkid":null,"outputrateOne":null,"outputrateTow":null,"outputrateThree":null}]
     * page : {"total":0,"totalPage":0,"page":1,"count":10,"startDate":null,"endDate":null,"keywords":null,"order":null,"asc":null,"partition":null,"status":null,"getAllRecord":false,"countAllRecord":true,"channelName":null,"startIndex":0}
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
         * total : 0
         * totalPage : 0
         * page : 1
         * count : 10
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
         * channelName : null
         * isall : 0
         * status : null
         * price : 0
         * img : 2014.gif
         * head : 2014.gif
         * createDate : null
         * alipay : null
         * place : null
         * outputsourcetype : null
         * upstreamAddress : null
         * rtmpDownstreamAddress : null
         * flvDownstreamAddress : null
         * hlsDownstreamAddress : null
         * channelId : null
         * playerpassword : null
         * watermarkid : null
         * outputrateOne : null
         * outputrateTow : null
         * outputrateThree : null
         */

        private Object id;
        private Object uid;
        private Object channelName;
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
        private Object rtmpDownstreamAddress;
        private Object flvDownstreamAddress;
        private Object hlsDownstreamAddress;
        private Object channelId;
        private Object playerpassword;
        private Object watermarkid;
        private Object outputrateOne;
        private Object outputrateTow;
        private Object outputrateThree;

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

        public Object getChannelName() {
            return channelName;
        }

        public void setChannelName(Object channelName) {
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

        public Object getRtmpDownstreamAddress() {
            return rtmpDownstreamAddress;
        }

        public void setRtmpDownstreamAddress(Object rtmpDownstreamAddress) {
            this.rtmpDownstreamAddress = rtmpDownstreamAddress;
        }

        public Object getFlvDownstreamAddress() {
            return flvDownstreamAddress;
        }

        public void setFlvDownstreamAddress(Object flvDownstreamAddress) {
            this.flvDownstreamAddress = flvDownstreamAddress;
        }

        public Object getHlsDownstreamAddress() {
            return hlsDownstreamAddress;
        }

        public void setHlsDownstreamAddress(Object hlsDownstreamAddress) {
            this.hlsDownstreamAddress = hlsDownstreamAddress;
        }

        public Object getChannelId() {
            return channelId;
        }

        public void setChannelId(Object channelId) {
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
    }
}
