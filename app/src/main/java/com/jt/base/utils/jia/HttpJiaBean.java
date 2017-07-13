package com.jt.base.utils.jia;

import java.util.List;

/**
 * Created by Smith on 2017/7/12.
 */

public class HttpJiaBean {

    /**
     * code : 0
     * msg : success
     * result : {"topic":{"code":26,"msg":"你打我啊","result":[{"title":"爱旅行","vodInfos":[{"isAll":"1","desc":"普吉岛风光","pic1":null,"pic2":null,"time":"00:20","playurl":"http://1253520711.vod2.myqcloud.com/e45ccc42vodtransgzp1253520711/8e19bcf89031868223016040914/f0.f40.mp4"},{"isAll":"1","desc":"梯田风光","pic1":null,"pic2":null,"time":"00:20","playurl":null},{"isAll":"1","desc":"吴哥窟美景","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"西藏人文","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"自然美景","pic1":null,"pic2":null,"time":null,"playurl":null}]},{"title":"大咖剧星","vodInfos":[{"isAll":"0","desc":"金刚骷髅岛","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"摔跤吧爸爸","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"长城","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"纵横四海","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"水上飞机","pic1":null,"pic2":null,"time":null,"playurl":null}]},{"title":"疯视角","vodInfos":[{"isAll":"0","desc":"机械姬","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"生化危机6：终章","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"星球大战外传：侠盗一号","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"花满楼","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"西藏旅游","pic1":null,"pic2":null,"time":null,"playurl":null}]},{"title":"鬼叔怪谈灵异系列","vodInfos":[{"isAll":"0","desc":"宝贝计划","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"沃伦","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"新世纪福尔摩斯","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"绑架","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"神秘探案","pic1":null,"pic2":null,"time":null,"playurl":null}]},{"title":"精彩赛事","vodInfos":[{"isAll":"1","desc":"卡丁车","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"摩托赛事","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"赛车比赛","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"赛车体验","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"山地摩托实拍","pic1":null,"pic2":null,"time":null,"playurl":null}]}]}}
     */

    private int code;
    private String msg;
    private ResultBeanX result;

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

    public ResultBeanX getResult() {
        return result;
    }

    public void setResult(ResultBeanX result) {
        this.result = result;
    }

    public static class ResultBeanX {
        /**
         * topic : {"code":26,"msg":"你打我啊","result":[{"title":"爱旅行","vodInfos":[{"isAll":"1","desc":"普吉岛风光","pic1":null,"pic2":null,"time":"00:20","playurl":"http://1253520711.vod2.myqcloud.com/e45ccc42vodtransgzp1253520711/8e19bcf89031868223016040914/f0.f40.mp4"},{"isAll":"1","desc":"梯田风光","pic1":null,"pic2":null,"time":"00:20","playurl":null},{"isAll":"1","desc":"吴哥窟美景","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"西藏人文","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"自然美景","pic1":null,"pic2":null,"time":null,"playurl":null}]},{"title":"大咖剧星","vodInfos":[{"isAll":"0","desc":"金刚骷髅岛","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"摔跤吧爸爸","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"长城","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"纵横四海","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"水上飞机","pic1":null,"pic2":null,"time":null,"playurl":null}]},{"title":"疯视角","vodInfos":[{"isAll":"0","desc":"机械姬","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"生化危机6：终章","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"星球大战外传：侠盗一号","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"花满楼","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"西藏旅游","pic1":null,"pic2":null,"time":null,"playurl":null}]},{"title":"鬼叔怪谈灵异系列","vodInfos":[{"isAll":"0","desc":"宝贝计划","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"沃伦","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"新世纪福尔摩斯","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"绑架","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"神秘探案","pic1":null,"pic2":null,"time":null,"playurl":null}]},{"title":"精彩赛事","vodInfos":[{"isAll":"1","desc":"卡丁车","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"摩托赛事","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"赛车比赛","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"赛车体验","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"山地摩托实拍","pic1":null,"pic2":null,"time":null,"playurl":null}]}]}
         */

        private TopicBean topic;

        public TopicBean getTopic() {
            return topic;
        }

        public void setTopic(TopicBean topic) {
            this.topic = topic;
        }

        public static class TopicBean {
            /**
             * code : 26
             * msg : 你打我啊
             * result : [{"title":"爱旅行","vodInfos":[{"isAll":"1","desc":"普吉岛风光","pic1":null,"pic2":null,"time":"00:20","playurl":"http://1253520711.vod2.myqcloud.com/e45ccc42vodtransgzp1253520711/8e19bcf89031868223016040914/f0.f40.mp4"},{"isAll":"1","desc":"梯田风光","pic1":null,"pic2":null,"time":"00:20","playurl":null},{"isAll":"1","desc":"吴哥窟美景","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"西藏人文","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"自然美景","pic1":null,"pic2":null,"time":null,"playurl":null}]},{"title":"大咖剧星","vodInfos":[{"isAll":"0","desc":"金刚骷髅岛","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"摔跤吧爸爸","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"长城","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"纵横四海","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"水上飞机","pic1":null,"pic2":null,"time":null,"playurl":null}]},{"title":"疯视角","vodInfos":[{"isAll":"0","desc":"机械姬","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"生化危机6：终章","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"星球大战外传：侠盗一号","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"花满楼","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"西藏旅游","pic1":null,"pic2":null,"time":null,"playurl":null}]},{"title":"鬼叔怪谈灵异系列","vodInfos":[{"isAll":"0","desc":"宝贝计划","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"沃伦","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"0","desc":"新世纪福尔摩斯","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"绑架","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"神秘探案","pic1":null,"pic2":null,"time":null,"playurl":null}]},{"title":"精彩赛事","vodInfos":[{"isAll":"1","desc":"卡丁车","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"摩托赛事","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"赛车比赛","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"赛车体验","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"山地摩托实拍","pic1":null,"pic2":null,"time":null,"playurl":null}]}]
             */

            private int code;
            private String msg;
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

            public List<ResultBean> getResult() {
                return result;
            }

            public void setResult(List<ResultBean> result) {
                this.result = result;
            }

            public static class ResultBean {
                /**
                 * title : 爱旅行
                 * vodInfos : [{"isAll":"1","desc":"普吉岛风光","pic1":null,"pic2":null,"time":"00:20","playurl":"http://1253520711.vod2.myqcloud.com/e45ccc42vodtransgzp1253520711/8e19bcf89031868223016040914/f0.f40.mp4"},{"isAll":"1","desc":"梯田风光","pic1":null,"pic2":null,"time":"00:20","playurl":null},{"isAll":"1","desc":"吴哥窟美景","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"西藏人文","pic1":null,"pic2":null,"time":null,"playurl":null},{"isAll":"1","desc":"自然美景","pic1":null,"pic2":null,"time":null,"playurl":null}]
                 */

                private String title;
                private List<VodInfosBean> vodInfos;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public List<VodInfosBean> getVodInfos() {
                    return vodInfos;
                }

                public void setVodInfos(List<VodInfosBean> vodInfos) {
                    this.vodInfos = vodInfos;
                }

                public static class VodInfosBean {
                    /**
                     * isAll : 1
                     * desc : 普吉岛风光
                     * pic1 : null
                     * pic2 : null
                     * time : 00:20
                     * playurl : http://1253520711.vod2.myqcloud.com/e45ccc42vodtransgzp1253520711/8e19bcf89031868223016040914/f0.f40.mp4
                     */

                    private String isAll;
                    private String desc;
                    private Object pic1;
                    private Object pic2;
                    private String time;
                    private String playurl;

                    public String getIsAll() {
                        return isAll;
                    }

                    public void setIsAll(String isAll) {
                        this.isAll = isAll;
                    }

                    public String getDesc() {
                        return desc;
                    }

                    public void setDesc(String desc) {
                        this.desc = desc;
                    }

                    public Object getPic1() {
                        return pic1;
                    }

                    public void setPic1(Object pic1) {
                        this.pic1 = pic1;
                    }

                    public Object getPic2() {
                        return pic2;
                    }

                    public void setPic2(Object pic2) {
                        this.pic2 = pic2;
                    }

                    public String getTime() {
                        return time;
                    }

                    public void setTime(String time) {
                        this.time = time;
                    }

                    public String getPlayurl() {
                        return playurl;
                    }

                    public void setPlayurl(String playurl) {
                        this.playurl = playurl;
                    }
                }
            }
        }
    }
}
