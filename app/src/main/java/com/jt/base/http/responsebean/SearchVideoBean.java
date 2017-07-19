package com.jt.base.http.responsebean;

import java.util.List;

/**
 * Created by Smith on 2017/7/18.
 */

public class SearchVideoBean {

    /**
     * code : 0
     * msg : success
     * result : [{"id":280,"uid":1,"type":0,"channelId":"9031868223034315984","channelName":"沉睡魔咒","isall":0,"status":0,"price":0,"img":"b934091f-da47-473a-ba50-46ce165147a9.jpg","createDate":null,"alipay":null,"place":null,"outputSourceType":3,"upstreamAddress":null,"rtmpDownstreamAddress":"http://1253690353.vod2.myqcloud.com/3308ed44vodgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.mp4","flvDownstreamAddress":null,"hlsDownstreamAddress":null,"playerPassword":null,"watermarkId":null,"outputrateOne":null,"outputrateTow":null,"outputrateThree":null,"head":"3.jpg","typeId":46,"topicId":62,"img1":"b84b7d38-965b-4f99-a758-7810a9a1f957.jpg","time":"230","format":null,"size":"121.931","rate":null,"vodTaskId":null,"top":null,"isVip":null,"publish":null,"vodStatus":5,"vodInfos":[{"id":693,"vodId":280,"vbitrate":4430011,"definition":0,"url":"http://1253690353.vod2.myqcloud.com/3308ed44vodgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.mp4","time":230,"size":121.94,"md5":""},{"id":694,"vodId":280,"vbitrate":580600,"definition":25,"url":"http://1253690353.vod2.myqcloud.com/e7308fecvodtransgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.f25.mp4","time":230,"size":121.94,"md5":"90946b66844ac19ec1d3fb1e60b7f07f"},{"id":695,"vodId":280,"vbitrate":2586394,"definition":60,"url":"http://1253690353.vod2.myqcloud.com/e7308fecvodtransgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.f60.mp4","time":230,"size":121.94,"md5":"f1f713440fecf9d051bde4bc3627b9f7"},{"id":696,"vodId":280,"vbitrate":656001,"definition":225,"url":"http://1253690353.vod2.myqcloud.com/e7308fecvodtransgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.f225.m3u8","time":230,"size":121.94,"md5":"d5551592f3d6049ef2cd20c0c3da4204"},{"id":697,"vodId":280,"vbitrate":2819208,"definition":260,"url":"http://1253690353.vod2.myqcloud.com/e7308fecvodtransgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.f260.m3u8","time":230,"size":121.94,"md5":"572585f700b35b37ba385ab428169fd3"},{"id":698,"vodId":280,"vbitrate":1067686,"definition":35,"url":"http://1253690353.vod2.myqcloud.com/e7308fecvodtransgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.f35.mp4","time":230,"size":121.94,"md5":"54e4e4242580f0aa42c8fea52a51db43"},{"id":699,"vodId":280,"vbitrate":1180894,"definition":235,"url":"http://1253690353.vod2.myqcloud.com/e7308fecvodtransgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.f235.m3u8","time":230,"size":121.94,"md5":"a2756258c68dd0cee9856fc719310a79"},{"id":700,"vodId":280,"vbitrate":1993157,"definition":45,"url":"http://1253690353.vod2.myqcloud.com/e7308fecvodtransgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.f45.mp4","time":230,"size":121.94,"md5":"a806ab52acd0ec08ef2b4d0f495bad2e"},{"id":701,"vodId":280,"vbitrate":2226275,"definition":245,"url":"http://1253690353.vod2.myqcloud.com/e7308fecvodtransgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.f245.m3u8","time":230,"size":121.94,"md5":"abbe9e16660bffc21b362b4882ecf411"}],"username":null,"typeName":null,"topicName":null,"reason":null,"introduce":null,"num":null}]
     * page : {"total":1,"totalPage":1,"page":1,"count":5,"startDate":null,"endDate":null,"keywords":"睡","order":null,"asc":null,"partition":null,"status":null,"getAllRecord":false,"countAllRecord":true,"channelName":null,"vodStatus":null,"typeId":null,"topicId":null,"isall":null,"sourceNum":222,"topicImg":null,"intrduce":null,"startIndex":0}
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
         * keywords : 睡
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
         * id : 280
         * uid : 1
         * type : 0
         * channelId : 9031868223034315984
         * channelName : 沉睡魔咒
         * isall : 0
         * status : 0
         * price : 0
         * img : b934091f-da47-473a-ba50-46ce165147a9.jpg
         * createDate : null
         * alipay : null
         * place : null
         * outputSourceType : 3
         * upstreamAddress : null
         * rtmpDownstreamAddress : http://1253690353.vod2.myqcloud.com/3308ed44vodgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.mp4
         * flvDownstreamAddress : null
         * hlsDownstreamAddress : null
         * playerPassword : null
         * watermarkId : null
         * outputrateOne : null
         * outputrateTow : null
         * outputrateThree : null
         * head : 3.jpg
         * typeId : 46
         * topicId : 62
         * img1 : b84b7d38-965b-4f99-a758-7810a9a1f957.jpg
         * time : 230
         * format : null
         * size : 121.931
         * rate : null
         * vodTaskId : null
         * top : null
         * isVip : null
         * publish : null
         * vodStatus : 5
         * vodInfos : [{"id":693,"vodId":280,"vbitrate":4430011,"definition":0,"url":"http://1253690353.vod2.myqcloud.com/3308ed44vodgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.mp4","time":230,"size":121.94,"md5":""},{"id":694,"vodId":280,"vbitrate":580600,"definition":25,"url":"http://1253690353.vod2.myqcloud.com/e7308fecvodtransgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.f25.mp4","time":230,"size":121.94,"md5":"90946b66844ac19ec1d3fb1e60b7f07f"},{"id":695,"vodId":280,"vbitrate":2586394,"definition":60,"url":"http://1253690353.vod2.myqcloud.com/e7308fecvodtransgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.f60.mp4","time":230,"size":121.94,"md5":"f1f713440fecf9d051bde4bc3627b9f7"},{"id":696,"vodId":280,"vbitrate":656001,"definition":225,"url":"http://1253690353.vod2.myqcloud.com/e7308fecvodtransgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.f225.m3u8","time":230,"size":121.94,"md5":"d5551592f3d6049ef2cd20c0c3da4204"},{"id":697,"vodId":280,"vbitrate":2819208,"definition":260,"url":"http://1253690353.vod2.myqcloud.com/e7308fecvodtransgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.f260.m3u8","time":230,"size":121.94,"md5":"572585f700b35b37ba385ab428169fd3"},{"id":698,"vodId":280,"vbitrate":1067686,"definition":35,"url":"http://1253690353.vod2.myqcloud.com/e7308fecvodtransgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.f35.mp4","time":230,"size":121.94,"md5":"54e4e4242580f0aa42c8fea52a51db43"},{"id":699,"vodId":280,"vbitrate":1180894,"definition":235,"url":"http://1253690353.vod2.myqcloud.com/e7308fecvodtransgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.f235.m3u8","time":230,"size":121.94,"md5":"a2756258c68dd0cee9856fc719310a79"},{"id":700,"vodId":280,"vbitrate":1993157,"definition":45,"url":"http://1253690353.vod2.myqcloud.com/e7308fecvodtransgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.f45.mp4","time":230,"size":121.94,"md5":"a806ab52acd0ec08ef2b4d0f495bad2e"},{"id":701,"vodId":280,"vbitrate":2226275,"definition":245,"url":"http://1253690353.vod2.myqcloud.com/e7308fecvodtransgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.f245.m3u8","time":230,"size":121.94,"md5":"abbe9e16660bffc21b362b4882ecf411"}]
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
        private Object alipay;
        private Object place;
        private int outputSourceType;
        private Object upstreamAddress;
        private String rtmpDownstreamAddress;
        private Object flvDownstreamAddress;
        private Object hlsDownstreamAddress;
        private Object playerPassword;
        private Object watermarkId;
        private Object outputrateOne;
        private Object outputrateTow;
        private Object outputrateThree;
        private String head;
        private int typeId;
        private int topicId;
        private String img1;
        private String time;
        private Object format;
        private String size;
        private Object rate;
        private Object vodTaskId;
        private Object top;
        private Object isVip;
        private Object publish;
        private int vodStatus;
        private Object username;
        private Object typeName;
        private Object topicName;
        private Object reason;
        private Object introduce;
        private Object num;
        private List<VodInfosBean> vodInfos;

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

        public int getOutputSourceType() {
            return outputSourceType;
        }

        public void setOutputSourceType(int outputSourceType) {
            this.outputSourceType = outputSourceType;
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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Object getFormat() {
            return format;
        }

        public void setFormat(Object format) {
            this.format = format;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
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

        public Object getPublish() {
            return publish;
        }

        public void setPublish(Object publish) {
            this.publish = publish;
        }

        public int getVodStatus() {
            return vodStatus;
        }

        public void setVodStatus(int vodStatus) {
            this.vodStatus = vodStatus;
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

        public List<VodInfosBean> getVodInfos() {
            return vodInfos;
        }

        public void setVodInfos(List<VodInfosBean> vodInfos) {
            this.vodInfos = vodInfos;
        }

        public static class VodInfosBean {
            /**
             * id : 693
             * vodId : 280
             * vbitrate : 4430011
             * definition : 0
             * url : http://1253690353.vod2.myqcloud.com/3308ed44vodgzp1253690353/d374dc649031868223034315984/V1O4oOfBxZ8A.mp4
             * time : 230
             * size : 121.94
             * md5 :
             */

            private int id;
            private int vodId;
            private int vbitrate;
            private int definition;
            private String url;
            private int time;
            private double size;
            private String md5;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getVodId() {
                return vodId;
            }

            public void setVodId(int vodId) {
                this.vodId = vodId;
            }

            public int getVbitrate() {
                return vbitrate;
            }

            public void setVbitrate(int vbitrate) {
                this.vbitrate = vbitrate;
            }

            public int getDefinition() {
                return definition;
            }

            public void setDefinition(int definition) {
                this.definition = definition;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public double getSize() {
                return size;
            }

            public void setSize(double size) {
                this.size = size;
            }

            public String getMd5() {
                return md5;
            }

            public void setMd5(String md5) {
                this.md5 = md5;
            }
        }
    }
}
