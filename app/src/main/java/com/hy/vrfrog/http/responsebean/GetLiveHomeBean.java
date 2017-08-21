package com.hy.vrfrog.http.responsebean;

import java.util.List;

/**
 * Created by qwe on 2017/8/3.
 */

public class GetLiveHomeBean {


    /**
     * code : 0
     * msg : success
     * result : [{"id":333,"uid":null,"username":"sasaa","channelName":"sun","isall":0,"status":2,"lvbStatus":0,"price":10,"img":"86fba2b9-31b1-4288-b32b-bebcd5323a91.jpg","head":null,"createDate":"2017-08-12 14:17:48","alipay":null,"place":null,"outputsourcetype":3,"upstreamAddress":"rtmp://10263.livepush.myqcloud.com/live/10263_56784498183c4d9d8211c243e60e1f71?bizid=10263&txSecret=951b9be9b06649882126345138166b7b&txTime=5991C8FF","rtmpDownstreamAddress":"rtmp://10263.liveplay.myqcloud.com/live/10263_90cc6e99e089450fb5e2c5acc57574b4","flvDownstreamAddress":"http://10263.liveplay.myqcloud.com/live/10263_90cc6e99e089450fb5e2c5acc57574b4.flv","hlsDownstreamAddress":"http://10263.liveplay.myqcloud.com/10263_90cc6e99e089450fb5e2c5acc57574b4.m3u8","channelId":"10263_90cc6e99e089450fb5e2c5acc57574b4","playerpassword":null,"watermarkid":null,"outputrateOne":null,"outputrateTow":null,"outputrateThree":null,"maxnum":null,"clicknum":null,"typeId":null,"typeName":null,"topicId":null,"topicName":null,"img1":"93417830-41d2-45e6-8dd2-eff672bf369b.jpg","size":null,"type":1,"sort":null,"vodStatus":null,"isVip":1,"isCharge":1,"chick":null,"isTranscribe":1,"worth":null,"sweepimg":"fff4b503-7065-4711-986f-35a427ad5c30.jpg","noticeimg":"ca5061f0-232d-499a-9c62-818ad2082412.jpg","sweepnoticeimg":null,"introduce":null,"updateDate":"2017-08-14 17:00:02","giftGroup":1,"lvbChannelRecords":[{"id":3,"lvbchannelid":"10263_90cc6e99e089450fb5e2c5acc57574b4","taskid":18148738,"fileid":"9031868223088821583","fileurl":"http://1253690353.vod2.myqcloud.com/3308ed44vodgzp1253690353/b06098ea9031868223088821583/playlist.m3u8","time":77,"size":1703,"recordname":"玉玉","createDate":null,"updateDate":null,"img":"","img1":"","status":1},{"id":4,"lvbchannelid":"10263_90cc6e99e089450fb5e2c5acc57574b4","taskid":18148738,"fileid":"9031868223088821583","fileurl":"http://1253690353.vod2.myqcloud.com/3308ed44vodgzp1253690353/b06098ea9031868223088821583/playlist.m3u8","time":77,"size":1703,"recordname":"玉玉","createDate":null,"updateDate":null,"img":"","img1":"","status":0},{"id":5,"lvbchannelid":"10263_90cc6e99e089450fb5e2c5acc57574b4","taskid":18148738,"fileid":"9031868223088821583","fileurl":"http://1253690353.vod2.myqcloud.com/3308ed44vodgzp1253690353/b06098ea9031868223088821583/playlist.m3u8","time":77,"size":1703,"recordname":"玉玉","createDate":null,"updateDate":null,"img":"","img1":"","status":0},{"id":6,"lvbchannelid":"10263_90cc6e99e089450fb5e2c5acc57574b4","taskid":18148738,"fileid":"9031868223088821583","fileurl":"http://1253690353.vod2.myqcloud.com/3308ed44vodgzp1253690353/b06098ea9031868223088821583/playlist.m3u8","time":77,"size":1703,"recordname":"玉玉","createDate":null,"updateDate":null,"img":"","img1":"","status":0},{"id":7,"lvbchannelid":"10263_90cc6e99e089450fb5e2c5acc57574b4","taskid":18148738,"fileid":"9031868223088821583","fileurl":"http://1253690353.vod2.myqcloud.com/3308ed44vodgzp1253690353/b06098ea9031868223088821583/playlist.m3u8","time":77,"size":1703,"recordname":"玉玉","createDate":null,"updateDate":null,"img":"","img1":"","status":0},{"id":8,"lvbchannelid":"10263_90cc6e99e089450fb5e2c5acc57574b4","taskid":18148738,"fileid":"9031868223088821583","fileurl":"http://1253690353.vod2.myqcloud.com/3308ed44vodgzp1253690353/b06098ea9031868223088821583/playlist.m3u8","time":77,"size":1703,"recordname":"玉玉","createDate":null,"updateDate":null,"img":"","img1":"","status":0}]}]
     * page : {"total":2,"totalPage":1,"page":1,"count":20,"startDate":null,"endDate":null,"keywords":null,"order":null,"asc":null,"partition":null,"status":null,"getAllRecord":false,"countAllRecord":true,"channelName":null,"type":1,"lvbStatus":null,"isVip":null,"isCharge":null,"sourceNum":111,"startIndex":0}
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
         * count : 20
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
         * type : 1
         * lvbStatus : null
         * isVip : null
         * isCharge : null
         * sourceNum : 111
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
        private int type;
        private Object lvbStatus;
        private Object isVip;
        private Object isCharge;
        private int sourceNum;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getLvbStatus() {
            return lvbStatus;
        }

        public void setLvbStatus(Object lvbStatus) {
            this.lvbStatus = lvbStatus;
        }

        public Object getIsVip() {
            return isVip;
        }

        public void setIsVip(Object isVip) {
            this.isVip = isVip;
        }

        public Object getIsCharge() {
            return isCharge;
        }

        public void setIsCharge(Object isCharge) {
            this.isCharge = isCharge;
        }

        public int getSourceNum() {
            return sourceNum;
        }

        public void setSourceNum(int sourceNum) {
            this.sourceNum = sourceNum;
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
         * id : 333
         * uid : null
         * username : sasaa
         * channelName : sun
         * isall : 0
         * status : 2
         * lvbStatus : 0
         * price : 10
         * img : 86fba2b9-31b1-4288-b32b-bebcd5323a91.jpg
         * head : null
         * createDate : 2017-08-12 14:17:48
         * alipay : null
         * place : null
         * outputsourcetype : 3
         * upstreamAddress : rtmp://10263.livepush.myqcloud.com/live/10263_56784498183c4d9d8211c243e60e1f71?bizid=10263&txSecret=951b9be9b06649882126345138166b7b&txTime=5991C8FF
         * rtmpDownstreamAddress : rtmp://10263.liveplay.myqcloud.com/live/10263_90cc6e99e089450fb5e2c5acc57574b4
         * flvDownstreamAddress : http://10263.liveplay.myqcloud.com/live/10263_90cc6e99e089450fb5e2c5acc57574b4.flv
         * hlsDownstreamAddress : http://10263.liveplay.myqcloud.com/10263_90cc6e99e089450fb5e2c5acc57574b4.m3u8
         * channelId : 10263_90cc6e99e089450fb5e2c5acc57574b4
         * playerpassword : null
         * watermarkid : null
         * outputrateOne : null
         * outputrateTow : null
         * outputrateThree : null
         * maxnum : null
         * clicknum : null
         * typeId : null
         * typeName : null
         * topicId : null
         * topicName : null
         * img1 : 93417830-41d2-45e6-8dd2-eff672bf369b.jpg
         * size : null
         * type : 1
         * sort : null
         * vodStatus : null
         * isVip : 1
         * isCharge : 1
         * chick : null
         * isTranscribe : 1
         * worth : null
         * sweepimg : fff4b503-7065-4711-986f-35a427ad5c30.jpg
         * noticeimg : ca5061f0-232d-499a-9c62-818ad2082412.jpg
         * sweepnoticeimg : null
         * introduce : null
         * updateDate : 2017-08-14 17:00:02
         * giftGroup : 1
         * lvbChannelRecords : [{"id":3,"lvbchannelid":"10263_90cc6e99e089450fb5e2c5acc57574b4","taskid":18148738,"fileid":"9031868223088821583","fileurl":"http://1253690353.vod2.myqcloud.com/3308ed44vodgzp1253690353/b06098ea9031868223088821583/playlist.m3u8","time":77,"size":1703,"recordname":"玉玉","createDate":null,"updateDate":null,"img":"","img1":"","status":1},{"id":4,"lvbchannelid":"10263_90cc6e99e089450fb5e2c5acc57574b4","taskid":18148738,"fileid":"9031868223088821583","fileurl":"http://1253690353.vod2.myqcloud.com/3308ed44vodgzp1253690353/b06098ea9031868223088821583/playlist.m3u8","time":77,"size":1703,"recordname":"玉玉","createDate":null,"updateDate":null,"img":"","img1":"","status":0},{"id":5,"lvbchannelid":"10263_90cc6e99e089450fb5e2c5acc57574b4","taskid":18148738,"fileid":"9031868223088821583","fileurl":"http://1253690353.vod2.myqcloud.com/3308ed44vodgzp1253690353/b06098ea9031868223088821583/playlist.m3u8","time":77,"size":1703,"recordname":"玉玉","createDate":null,"updateDate":null,"img":"","img1":"","status":0},{"id":6,"lvbchannelid":"10263_90cc6e99e089450fb5e2c5acc57574b4","taskid":18148738,"fileid":"9031868223088821583","fileurl":"http://1253690353.vod2.myqcloud.com/3308ed44vodgzp1253690353/b06098ea9031868223088821583/playlist.m3u8","time":77,"size":1703,"recordname":"玉玉","createDate":null,"updateDate":null,"img":"","img1":"","status":0},{"id":7,"lvbchannelid":"10263_90cc6e99e089450fb5e2c5acc57574b4","taskid":18148738,"fileid":"9031868223088821583","fileurl":"http://1253690353.vod2.myqcloud.com/3308ed44vodgzp1253690353/b06098ea9031868223088821583/playlist.m3u8","time":77,"size":1703,"recordname":"玉玉","createDate":null,"updateDate":null,"img":"","img1":"","status":0},{"id":8,"lvbchannelid":"10263_90cc6e99e089450fb5e2c5acc57574b4","taskid":18148738,"fileid":"9031868223088821583","fileurl":"http://1253690353.vod2.myqcloud.com/3308ed44vodgzp1253690353/b06098ea9031868223088821583/playlist.m3u8","time":77,"size":1703,"recordname":"玉玉","createDate":null,"updateDate":null,"img":"","img1":"","status":0}]
         */

        private int id;
        private int uid;
        private String username;
        private String channelName;
        private int isall;
        private int status;
        private int lvbStatus;
        private int price;
        private String img;
        private Object head;
        private String createDate;
        private Object alipay;
        private Object place;
        private int outputsourcetype;
        private String upstreamAddress;
        private String rtmpDownstreamAddress;
        private String flvDownstreamAddress;
        private String hlsDownstreamAddress;
        private String channelId;
        private Object playerpassword;
        private Object watermarkid;
        private Object outputrateOne;
        private Object outputrateTow;
        private Object outputrateThree;
        private Object maxnum;
        private Object clicknum;
        private Object typeId;
        private Object typeName;
        private Object topicId;
        private Object topicName;
        private String img1;
        private Object size;
        private int type;
        private Object sort;
        private Object vodStatus;
        private int isVip;
        private int isCharge;
        private Object chick;
        private int isTranscribe;
        private Object worth;
        private String sweepimg;
        private String noticeimg;
        private Object sweepnoticeimg;
        private Object introduce;
        private String updateDate;
        private int giftGroup;
        private List<LvbChannelRecordsBean> lvbChannelRecords;

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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getLvbStatus() {
            return lvbStatus;
        }

        public void setLvbStatus(int lvbStatus) {
            this.lvbStatus = lvbStatus;
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

        public Object getHead() {
            return head;
        }

        public void setHead(Object head) {
            this.head = head;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
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

        public int getOutputsourcetype() {
            return outputsourcetype;
        }

        public void setOutputsourcetype(int outputsourcetype) {
            this.outputsourcetype = outputsourcetype;
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

        public Object getTypeId() {
            return typeId;
        }

        public void setTypeId(Object typeId) {
            this.typeId = typeId;
        }

        public Object getTypeName() {
            return typeName;
        }

        public void setTypeName(Object typeName) {
            this.typeName = typeName;
        }

        public Object getTopicId() {
            return topicId;
        }

        public void setTopicId(Object topicId) {
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public Object getVodStatus() {
            return vodStatus;
        }

        public void setVodStatus(Object vodStatus) {
            this.vodStatus = vodStatus;
        }

        public int getIsVip() {
            return isVip;
        }

        public void setIsVip(int isVip) {
            this.isVip = isVip;
        }

        public int getIsCharge() {
            return isCharge;
        }

        public void setIsCharge(int isCharge) {
            this.isCharge = isCharge;
        }

        public Object getChick() {
            return chick;
        }

        public void setChick(Object chick) {
            this.chick = chick;
        }

        public int getIsTranscribe() {
            return isTranscribe;
        }

        public void setIsTranscribe(int isTranscribe) {
            this.isTranscribe = isTranscribe;
        }

        public Object getWorth() {
            return worth;
        }

        public void setWorth(Object worth) {
            this.worth = worth;
        }

        public String getSweepimg() {
            return sweepimg;
        }

        public void setSweepimg(String sweepimg) {
            this.sweepimg = sweepimg;
        }

        public String getNoticeimg() {
            return noticeimg;
        }

        public void setNoticeimg(String noticeimg) {
            this.noticeimg = noticeimg;
        }

        public Object getSweepnoticeimg() {
            return sweepnoticeimg;
        }

        public void setSweepnoticeimg(Object sweepnoticeimg) {
            this.sweepnoticeimg = sweepnoticeimg;
        }

        public Object getIntroduce() {
            return introduce;
        }

        public void setIntroduce(Object introduce) {
            this.introduce = introduce;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public int getGiftGroup() {
            return giftGroup;
        }

        public void setGiftGroup(int giftGroup) {
            this.giftGroup = giftGroup;
        }

        public List<LvbChannelRecordsBean> getLvbChannelRecords() {
            return lvbChannelRecords;
        }

        public void setLvbChannelRecords(List<LvbChannelRecordsBean> lvbChannelRecords) {
            this.lvbChannelRecords = lvbChannelRecords;
        }

        public static class LvbChannelRecordsBean {
            /**
             * id : 3
             * lvbchannelid : 10263_90cc6e99e089450fb5e2c5acc57574b4
             * taskid : 18148738
             * fileid : 9031868223088821583
             * fileurl : http://1253690353.vod2.myqcloud.com/3308ed44vodgzp1253690353/b06098ea9031868223088821583/playlist.m3u8
             * time : 77
             * size : 1703
             * recordname : 玉玉
             * createDate : null
             * updateDate : null
             * img :
             * img1 :
             * status : 1
             */

            private int id;
            private String lvbchannelid;
            private int taskid;
            private String fileid;
            private String fileurl;
            private int time;
            private int size;
            private String recordname;
            private Object createDate;
            private Object updateDate;
            private String img;
            private String img1;
            private int status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLvbchannelid() {
                return lvbchannelid;
            }

            public void setLvbchannelid(String lvbchannelid) {
                this.lvbchannelid = lvbchannelid;
            }

            public int getTaskid() {
                return taskid;
            }

            public void setTaskid(int taskid) {
                this.taskid = taskid;
            }

            public String getFileid() {
                return fileid;
            }

            public void setFileid(String fileid) {
                this.fileid = fileid;
            }

            public String getFileurl() {
                return fileurl;
            }

            public void setFileurl(String fileurl) {
                this.fileurl = fileurl;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public String getRecordname() {
                return recordname;
            }

            public void setRecordname(String recordname) {
                this.recordname = recordname;
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

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getImg1() {
                return img1;
            }

            public void setImg1(String img1) {
                this.img1 = img1;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
