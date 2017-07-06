package com.jt.base.utils;

import com.jt.base.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Smith on 2017/7/6.
 */

public class JiaTitleUtils {

    public static List<String> getOne() {
        List<String> One = new ArrayList<>();
        One.add("宝贝老板");
        One.add("好吃懒做超级自恋加菲猫");
        One.add("蓝精灵：寻找神秘村");
        One.add("美女与野兽 Beauty and the Beast");
        One.add("神偷奶爸3");
        return One;
    }

    public static List<String> getTwo() {
        List<String> One = new ArrayList<>();
        One.add("被操纵的城市");
        One.add("毒。诫");
        One.add("画皮2");
        One.add("生化危机：复仇");
        One.add("生化危机：终章");
        return One;
    }

    public static List<String> getThree() {
        List<String> One = new ArrayList<>();
        One.add("巴霍巴利王(下)：终结");
        One.add("非凡任务");
        One.add("金刚狼3：殊死一战");
        One.add("速度与激情8");
        One.add("亚瑟王：斗兽争霸");
        return One;
    }

    public static List<String> getFour() {
        List<String> One = new ArrayList<>();
        One.add("刺客信条");
        One.add("独闯龙潭");
        One.add("攻壳机动队");
        One.add("疾速特攻");
        One.add("降临");
        return One;
    }

    public static List<String> getFive() {
        List<String> One = new ArrayList<>();
        One.add("不期而遇");
        One.add("欢乐好声音");
        One.add("乐高蝙蝠侠大电影");
        One.add("明日的我与昨日的你约会");
        One.add("一条狗的使命");
        return One;
    }

    public static List<String> getTopic() {
        List<String> One = new ArrayList<>();
        One.add("好莱坞动画");
        One.add("画面惊悚血腥");
        One.add("开启奇幻冒险之旅");
        One.add("科幻新标杆");
        One.add("迎来全新剧情");
        return One;
    }

    public static HashMap<Integer, List<String>> getJSJ() {
        HashMap<Integer, List<String>> Jiashuju = new HashMap<>();
        Jiashuju.put(0,getOne());
        Jiashuju.put(1,getTwo());
        Jiashuju.put(2,getThree());
        Jiashuju.put(3,getFour());
        Jiashuju.put(4,getFive());
        return Jiashuju;
    }

}
